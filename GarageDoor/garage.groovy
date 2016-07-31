/** SmartThings Device Type. Based on ... */
/**
 *  Garage Door
 *
 *  Author: Steve Sell
 *  Date: 2014-02-02
 */

metadata {
	// Preferences
	definition (name: "Garage Door", namespace: "dsg", author: "Scott Gregory") {
		capability "Polling"
       	capability "Refresh"
        capability "Switch"
        capability "Contact Sensor"
		
        attribute "contact",   "string"

		attribute "leftDoor", "string"
       	attribute "rightDoor", "string"
        
		command "pushLeft"
		command "pushRight"
	}

	// tile definitions
	tiles {
		standardTile("leftDoor", "device.leftDoor", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
			state "closed", label: 'Closed', action: "pushLeft", icon: "st.doors.garage.garage-closed", backgroundColor: "#79b821", nextState: "opening"
            state "open", label: 'Open', action: "pushLeft", icon: "st.doors.garage.garage-open", backgroundColor: "#ffa81e", nextState: "closing"
            state "opening", label: "Opening", icon: "st.doors.garage.garage-opening", backgroundColor: "89C2E8"
            state "closing", label: "Closing", icon: "st.doors.garage.garage-closing", backgroundColor: "89C2E8"
 		}
        standardTile("rightDoor", "device.rightDoor", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
			state "closed", label: 'Closed', action: "pushRight", icon: "st.doors.garage.garage-closed", backgroundColor: "#79b821", nextState: "opening"
            state "open", label: 'Open', action: "pushRight", icon: "st.doors.garage.garage-open", backgroundColor: "#ffa81e", nextState: "closing"
            state "opening", label: "Opening", icon: "st.doors.garage.garage-opening", backgroundColor: "89C2E8"
            state "closing", label: "Closing", icon: "st.doors.garage.garage-closing", backgroundColor: "89C2E8"
 		}

		main "leftDoor"
		details(["leftDoor","rightDoor"])
	}

    simulator {
        status "on":  "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6E"
        status "off": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6666"

        // reply messages
        reply "raw 0x0 { 00 00 0a 0a 6f 6e }": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6E"
        reply "raw 0x0 { 00 00 0a 0a 6f 66 66 }": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6666"
    }
}

List<Map> parse(String description) {
    def msg = zigbee.parse(description)?.text
    log.debug "Parse got '${msg}'"

    def parts = msg.split(" ")
    def name  = parts.length>0?parts[0].trim():null
    def value = parts.length>1?parts[1].trim():null

    name = value != "ping" ? name : null

	def result = []
    def mainResult = createEvent(name: name, value: value)
	result << mainResult
	
	if(name!=null && name!='') {
		/* Here we include the 'contact' (reed switch) state so smart apps like
		   'Left It Open' will be able to work from the Contact Switch capability.
			Get the current state for any of the doors and return 'open' for the
			contact if either door is open. Only return 'closed' when both are closed.
		*/
		def anyOpen=false
		def leftOpen = device.latestValue("leftDoor")=='open'
		def rightOpen = device.latestValue("rightDoor")=='open'
		def n_open = (leftOpen?1:0) + (rightOpen?1:0)
		if(value=='open' || n_open>0)
			anyOpen = true

		/* State change on the contact if:
		      o both are closed and one opens
			  o only one is open and it closes
		*/
		def stateChange=false
		if(n_open==0 && value=='open')
			stateChange = true
		else if(n_open==1 && value=='closed')
			stateChange = true
		// the overall contact state
		result << createEvent(name: "contact", value: (anyOpen ? 'open' : 'closed'), isStateChange: stateChange)
	}

    log.debug result

    return result
}

def pushLeft() {
    zigbee.smartShield(text: "pushLeft").format()
}

def pushRight() {
    zigbee.smartShield(text: "pushRight").format()
}

