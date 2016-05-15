# Control Garage Doors with SmartThings and Arduino

(**Date**: May 2016)

Based on a project described at https://community.smartthings.com/t/arduino-smartshield-garage-controller/1333

This project leverages an Arduino (Ard) plus SmartThings Shield (STsh) (STsh+Ard) to build a controller that can open and close garage doors from a ST app.

## Project Equipment
* <A href="http://www.amazon.com/Arduino-UNO-SMD-R3-board/dp/B00F6JCV20/ref=sr_1_2?ie=UTF8&qid=1391971284&sr=8-2&keywords=arduino+r3">Arduino Uno</A>
* <A href="http://www.amazon.com/gp/product/B003XZSZWO/ref=oh_details_o02_s01_i00?ie=UTF8&psc=1">Power Adapter (for the Uno)</A>
* <A href="http://www.amazon.com/SainSmart-2-CH-2-Channel-Relay-Module/dp/B0057OC6D8/ref=pd_sim_sbs_hi_2">2-Channel Relay</A>
* 2x <A href="http://www.amazon.com/Directed-Electronics-8601-Magnetic-Switch/dp/B0009SUF08/ref=sr_1_1?s=electronics&ie=UTF8&qid=1391971163&sr=1-1&keywords=magnetic+reed+switch">Magnetic Reed Switches</A>
* <A href="https://shop.smartthings.com/#/products/smartthings-shield-arduino">SmartThings Arduino Shield</A>

## Join STsh+Ard to Your ST Hub
Assemble the STsh atop the Arduino, power it up and use your ST app on your mobile phone to include the device in your ST network. Name the device "GarageController" in the ST app.

## Files
* garage.groovy - SmartThings device handler code
* garage_sketch.c - Arduino + SmartThings Shield code
* ST Arduino Library - From http://docs.smartthings.com/en/latest/arduino/

### SmartThings Arduino Library
This library is for the ST Shield. Follow the installation steps from http://docs.smartthings.com/en/latest/arduino/.

### SmartThings Device Handler
The **garage.groovy** code is to be used to define the device handler of the STsh+Ard device. To create the device type, login to your ST account at https://graph.api.smartthings.com and click the "My Device Handlers" link at the top and then click "Create New Device Handler". Perform the following to create the device type:

* Click the "From Code" Tab
* Copy and paste garage.groovy file into the text area
** Edit the metadata.definition and set namespace and author attributes to your liking.
** Press "Save"
** Press "Publish", then  "For Me" - this delivers the code to your ST hub

## Debugging
### SmartThings Live Logging
### Simulator
