device-type.ZXT-120
==========================

The ZXT-120 is an HVAC controller for your SmartThings Hub.  This device will accept commands like a normal thermostat and relay them by IR to the air conditioner/heat-pump.

## Installation

1. Create a new device type (https://graph.api.smartthings.com/ide/devices)
    * Name: ZXT-120
    * Author: b.dahlem@gmail.com
    * Capabilities:
        * Thermostat

(Note, the code contains all capabilities and the ide now updates based on the capabilities in the code).

    * Copy the contents of ZXT-120.device-type.groovy to the ide

2. Associate your ZXT-120 with the SmartThings hub.  In the things list, click the + tile, then press the button on the ZXT-120.

3. In the "My Devices" tab on graph.api.smartthings.com, edit the new "Z-Wave Thermostat" that you just created.  Change the device type from "Z-Wave Thermostat" to "ZXT-120 Thermostat".


## Use
In the SmartThings app on your smartphone, open the settings for the ZXT-120 then tap on the preferences tile.  Change the remote code to the code for your air conditioner.  Then click configure tile to set the remote code.  You may need to try several codes to enable all the functions for your air conditioner.

Remote codes for firmware v. 1.5 of the ZXT-120 are available at: 
http://www.remotec.com.hk/photos/software/Z-wave/ZXT-120%20Code%20List%20V1.5_20131203.pdf

The support page for the ZXT-120:
http://www.remotec.com.hk/zaspx/support.aspx
