/**
 *  ZXT-120 HVAC Control
 *
 *  Author: b.dahlem@gmail.com
 *  Date: 2014-04-10
 *  Code: https://github.com/smartthings-users/device-type.zxt-120
 *
 * Copyright (C) 2013 Justin J. Novack <jnovack@gmail.com>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions: The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
 
preferences {
    input("remoteCode", "number", title: "Remote Code (000 for learned, don't forget to hit configure after changing)", description: "The number of the remote to emulate")
}

metadata {
	definition (name: "ZXT-120 Thermostat", namespace: "", author: "SmartThings") {
		capability "Actuator"
		capability "Temperature Measurement"
		capability "Thermostat"
		capability "Configuration"
		capability "Polling"
		capability "Sensor"

		command "switchMode"
		command "switchFanMode"
        command "switchFanOscillate"
		command "setRemoteCode"
        command "raiseTemperature"
        command "lowerTemperature"
        
		fingerprint deviceId: "0x08"
		fingerprint inClusters: "0x43,0x40,0x44,0x31"
	}

	// simulator metadata
	simulator {
		status "off"			: "command: 4003, payload: 00"
		status "heat"			: "command: 4003, payload: 01"
		status "cool"			: "command: 4003, payload: 02"
		status "auto"			: "command: 4003, payload: 03"
		status "emergencyHeat"	: "command: 4003, payload: 04"

		status "fanAuto"		: "command: 4403, payload: 00"
		status "fanOn"			: "command: 4403, payload: 01"
		status "fanCirculate"	: "command: 4403, payload: 06"

		status "heat 60"        : "command: 4303, payload: 01 01 3C"
		status "heat 68"        : "command: 4303, payload: 01 01 44"
		status "heat 72"        : "command: 4303, payload: 01 01 48"

		status "cool 72"        : "command: 4303, payload: 02 01 48"
		status "cool 76"        : "command: 4303, payload: 02 01 4C"
		status "cool 80"        : "command: 4303, payload: 02 01 50"

		status "temp 58"        : "command: 3105, payload: 01 22 02 44"
		status "temp 62"        : "command: 3105, payload: 01 22 02 6C"
		status "temp 70"        : "command: 3105, payload: 01 22 02 BC"
		status "temp 74"        : "command: 3105, payload: 01 22 02 E4"
		status "temp 78"        : "command: 3105, payload: 01 22 03 0C"
		status "temp 82"        : "command: 3105, payload: 01 22 03 34"

		// reply messages
		reply "2502": "command: 2503, payload: FF"
	}

	tiles {
		valueTile("temperature", "device.temperature", width: 2, height: 2) {
			state("temperature", label:'${currentValue}°',
				backgroundColors:[
					[value: 31, color: "#153591"],
					[value: 44, color: "#1e9cbb"],
					[value: 59, color: "#90d2a7"],
					[value: 74, color: "#44b621"],
					[value: 84, color: "#f1d801"],
					[value: 95, color: "#d04e00"],
					[value: 96, color: "#bc2323"]
				]
			)
		}
		standardTile("mode", "device.thermostatMode", inactiveLabel: false, decoration: "flat", canChangeIcon: true, canChangeBackground: true) {
			state "off", action:"switchMode", icon:"st.thermostat.heating-cooling-off", label: ' '
			state "heat", action:"switchMode", icon:"st.thermostat.heat", label: ' '
			state "emergencyHeat", action:"switchMode", icon:"st.thermostat.emergency-heat", label: ' '
			state "cool", action:"switchMode", icon:"st.thermostat.cool", label: ' '
			state "auto", action:"switchMode", icon:"st.thermostat.auto", label: ' '
            state "dry", action:"switchMode", icon:"st.Bath.bath1", label: 'Dry'
			state "autoChangeover", action:"switchMode", icon:"st.thermostat.auto", label: ' '
        }
		standardTile("fanMode", "device.thermostatFanMode", inactiveLabel: false, decoration: "flat", canChangeIcon: true, canChangeBackground: true) {
			state "fanAuto", action:"switchFanMode", icon:"st.Appliances.appliances11", label: 'AUTO'
			state "fanLow", action:"switchFanMode", icon:"st.Appliances.appliances11", label: 'LOW'
			state "fanMed", action:"switchFanMode", icon:"st.Appliances.appliances11", label: 'MED'
			state "fanHigh", action:"switchFanMode", icon:"st.Appliances.appliances11", label: 'HIGH'
		}
        standardTile("swingMode", "device.swingMode", inactiveLabel: false, decoration: "flat", canChangeIcon: true, canChangeBackground: true) {
			state "oscillate", action:"switchFanOscillate", icon:"st.secondary.refresh-icon", label: 'Swing ${currentValue}'
		}
		standardTile("temperatureLower", "device.thermostatSetpoint", inactiveLabel: false, decoration: "flat", canChangeIcon: true, canChangeBackground: true) {
			state "lowerTemp", action:"lowerTemperature", backgroundColor:"#ffffff", icon: "st.thermostat.thermostat-down"
		}
		valueTile("temperatureSetpoint", "device.thermostatSetpoint", inactiveLabel: false, decoration: "flat", canChangeIcon: true, canChangeBackground: true) {
			state("thermostatSetpoint", label:'${currentValue}°',
            	/*backgroundColors:[
					[value: 31, color: "#153591"],
					[value: 44, color: "#1e9cbb"],
					[value: 59, color: "#90d2a7"],
					[value: 74, color: "#44b621"],
					[value: 84, color: "#f1d801"],
					[value: 95, color: "#d04e00"],
					[value: 96, color: "#bc2323"]
                ]*/
            )
		}
		standardTile("temperatureRaise", "device.thermostatSetpoint", inactiveLabel: false, decoration: "flat", canChangeIcon: true, canChangeBackground: true) {
			state "raiseTemp", action:"raiseTemperature", backgroundColor: "#ffffff", icon: "st.thermostat.thermostat-up"
		}
		standardTile("refresh", "device.thermostatMode", inactiveLabel: false, decoration: "flat") {
			state "default", action:"polling.poll", icon:"st.secondary.refresh"
		}
		standardTile("configure", "device.configure", inactiveLabel: false, decoration: "flat") {
			state "configure", label:'', action:"configuration.configure", icon:"st.secondary.configure"
		}
		main "temperature"
		details(["temperature", "temperatureRaise", "temperatureSetpoint", "mode", "fanMode", "temperatureLower", "swingMode", "refresh", "configure", "setRemoteCode"])
	}
}

def modes() {
	["off", "auto", "heat", "emergencyHeat", "cool", "dry", "autoChangeover"]
}

def getSetpointMap() { [
	"heatingSetpoint": physicalgraph.zwave.commands.thermostatsetpointv1.ThermostatSetpointSet.SETPOINT_TYPE_HEATING_1,
    "coolingSetpoint": physicalgraph.zwave.commands.thermostatsetpointv1.ThermostatSetpointSet.SETPOINT_TYPE_COOLING_1,
    "dryingSetpoint": physicalgraph.zwave.commands.thermostatsetpointv1.ThermostatSetpointSet.SETPOINT_TYPE_DRY_AIR,
    "autoChangeoverSetpoint": physicalgraph.zwave.commands.thermostatsetpointv1.ThermostatSetpointSet.SETPOINT_TYPE_AUTO_CHANGEOVER
]}

def getSetpointModeMap() { [
	"heat": "heatingSetpoint",
    "cool": "coolingSetpoint",
    "dry": "dryingSetpoint",
    "autoChangeover": "autoChangeoverSetpoint"
]}

def getModeMap() { [
	"off": physicalgraph.zwave.commands.thermostatmodev1.ThermostatModeSet.MODE_OFF,
	"heat": physicalgraph.zwave.commands.thermostatmodev1.ThermostatModeSet.MODE_HEAT,
	"cool": physicalgraph.zwave.commands.thermostatmodev1.ThermostatModeSet.MODE_COOL,
    "auto": physicalgraph.zwave.commands.thermostatmodev1.ThermostatModeSet.MODE_AUTO,
	"emergencyHeat": physicalgraph.zwave.commands.thermostatmodev1.ThermostatModeSet.MODE_AUXILIARY_HEAT,
    "dry": physicalgraph.zwave.commands.thermostatmodev1.ThermostatModeSet.MODE_DRY_AIR,
    "autoChangeover": physicalgraph.zwave.commands.thermostatmodev1.ThermostatModeSet.MODE_AUTO_CHANGEOVER
]}

def getFanModeMap() { [
	"auto": physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeReport.FAN_MODE_AUTO_LOW,
	"low": physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeReport.FAN_MODE_LOW,
    "med": physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeReport.FAN_MODE_MEDIUM,
    "high": physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeReport.FAN_MODE_HIGH
]}

def parse(String description)
{
	def map = createEvent(zwaveEvent(zwave.parse(description, [0x42:1, 0x43:2, 0x31: 3])))
	if (!map) {
		return null
	}

	def result = [map]
    def stateChanges = setpointMap.keySet() + "thermostatMode"
	if (map.isStateChange && map.name in stateChanges) {
		def map2 = [
			name: "thermostatSetpoint",
			unit: getTemperatureScale()
		]
		if (map.name == "thermostatMode") {
			updateState("lastTriedMode", map.value)
            
            def setpointModeName = setpointModeMap[map.value]
            
            
            if (setpointModeName == null) {
            	setpointModeName = "coolingSetpoint"
            }
            map2.value = device.latestValue(setpointModeName)
            log.info "THERMOSTAT, latest " + setpointModeName + " = ${map2.value}"
		}
		else {
			def mode = device.latestValue("thermostatMode")
			log.info "THERMOSTAT, latest mode = ${mode}"
			if (map.name == setpointModeMap[mode]) {
				map2.value = map.value
				map2.unit = map.unit
			}
		}
		if (map2.value != null) {
			log.debug "THERMOSTAT, adding setpoint event: $map"
			result << createEvent(map2)
		}
	} else if (map.name == "thermostatFanMode" && map.isStateChange) {
		updateState("lastTriedFanMode", map.value)
	}
	log.debug "Parse returned $result"
	result
}

// Event Generation
def zwaveEvent(physicalgraph.zwave.commands.thermostatsetpointv2.ThermostatSetpointReport cmd)
{
	def cmdScale = cmd.scale == 1 ? "F" : "C"
	def map = [:]
	map.value = convertTemperatureIfNeeded(cmd.scaledValue, cmdScale, cmd.precision)
	map.unit = getTemperatureScale()
	map.displayed = false
    
    def name = setpointMap.find {it.value == cmd.setpointType}?.key
    
    if (name == null) {
    	return [:]
    }
    
    map.name = name

	// So we can respond with same format
	state.size = cmd.size
	state.scale = cmd.scale
	state.precision = cmd.precision
	map
}

def zwaveEvent(physicalgraph.zwave.commands.sensormultilevelv3.SensorMultilevelReport cmd)
{
	def cmdScale = cmd.scale == 1 ? "F" : "C"

	def map = [:]
	map.value = convertTemperatureIfNeeded(cmd.scaledSensorValue, cmdScale, cmd.precision)
	map.unit = getTemperatureScale()
	map.name = "temperature"
	map
}

def zwaveEvent(physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeReport cmd) {
	def map = [:]
    
    map.value = modeMap.find {it.value == cmd.mode}?.key
	map.name = "thermostatMode"
	map
}

def zwaveEvent(physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeReport cmd) {
	def map = [:]
    
    map.value = fanModeMap.find {it.value == cmd.fanMode}?.key
	map.name = "thermostatFanMode"
	map.displayed = false
	map
}

def zwaveEvent(physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeSupportedReport cmd) {
	def supportedModes = ""
	if(cmd.off) { supportedModes += "off " }
	if(cmd.heat) { supportedModes += "heat " }
	if(cmd.auxiliaryemergencyHeat) { supportedModes += "emergencyHeat " }
	if(cmd.cool) { supportedModes += "cool " }
	if(cmd.auto) { supportedModes += "auto " }
	if(cmd.dryAir) { supportedModes += "dry " }
    if(cmd.autoChangeover) { supportedModes += "autoChangeover " }
    
    log.debug "Supported Modes: ${supportedModes}"
	updateState("supportedModes", supportedModes)
}

def zwaveEvent(physicalgraph.zwave.commands.thermostatfanmodev3.ThermostatFanModeSupportedReport cmd) {
	def supportedFanModes = ""
	if(cmd.auto) { supportedFanModes += "fanAuto " }
	if(cmd.low) { supportedFanModes += "fanLow " }
	if(cmd.medium) { supportedFanModes += "fanMedium " }
	if(cmd.high) { supportedFanModes += "fanHigh " }

    log.debug "Supported Fan Modes: ${supportedFanModes}"
	updateState("supportedFanModes", supportedFanModes)
}

def updateState(String name, String value) {
	state[name] = value
	device.updateDataValue(name, value)
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd) {
	log.debug "Zwave event received: $cmd"
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	switch (cmd.parameterNumber) {
    	case 27:
        	log.debug "remote code" + cmd.configurationValue.toString()
        	break
            
    	case 33:
        	def oscillateMode = (cmd.configurationValue[0] == 0) ? "Off" : "On"
        	updateState("swingMode", oscillateMode)
            log.debug "Updated: Oscillate " + oscillateMode
            sendEvent(name: "swingMode", value: oscillateMode, displayed: true)
             
        	break
    
    	default:
			log.warn "Unexpected zwave command $cmd"
    }
}

// Command Implementations
def poll() {

	def commands = [
		zwave.sensorMultilevelV3.sensorMultilevelGet().format(), // current temperature
		zwave.thermostatModeV2.thermostatModeGet().format(),
		zwave.thermostatFanModeV3.thermostatFanModeGet().format(),
        zwave.configurationV1.configurationGet(parameterNumber: 27).format(),
        zwave.configurationV1.configurationGet(parameterNumber: 33).format()
	]
    
    for (setpoint in setpointModeMap) {
        def supportedModes = getDataByName("supportedModes")
        if (supportedModes.tokenize()?.contains(setpoint.key)) {
            commands << [zwave.thermostatSetpointV1.thermostatSetpointGet(setpointType: setpointMap[setpoint.value]).format()]
        }
    }
    
    delayBetween(commands, 2300)
}

def setThermostatSetpoint(degrees) {
	setThermostatSetpoint(degrees.toDouble())
}

def setThermostatSetpoint(Double degrees, setpointMode = null) {
	def deviceScale = state.scale ?: 1
	def deviceScaleString = deviceScale == 2 ? "C" : "F"
    def locationScale = getTemperatureScale()
	def p = (state.precision == null) ? 1 : state.precision
    
    def convertedDegrees
    if (locationScale == "C" && deviceScaleString == "F") {
    	convertedDegrees = celsiusToFahrenheit(degrees)
    } else if (locationScale == "F" && deviceScaleString == "C") {
    	convertedDegrees = fahrenheitToCelsius(degrees)
    } else {
    	convertedDegrees = degrees
    }
    
    if (setpointMode == null) {
    	def mode = device.currentState("thermostatMode")?.value ?: null
        setpointMode = setpointMap[setpointModeMap[mode]] ?: 0
	}
    
    log.debug "new temp ${degrees}"
    
    delayBetween([
        zwave.thermostatSetpointV1.thermostatSetpointSet(setpointType: setpointMode, scale: deviceScale, precision: p, scaledValue: convertedDegrees).format(),
        zwave.thermostatSetpointV1.thermostatSetpointGet(setpointType: setpointMode).format()
    ])
}


def setHeatingSetpoint(degrees) {
	setThermostatSetpoint(degrees.toDouble(), physicalgraph.zwave.commands.thermostatsetpointv1.ThermostatSetpointSet.SETPOINT_TYPE_HEATING_1)
}

def setCoolingSetpoint(degrees) {
	setThermostatSetpoint(degrees.toDouble(), physicalgraph.zwave.commands.thermostatsetpointv1.ThermostatSetpointSet.SETPOINT_TYPE_COOLING_1)
}

def configure() {
	delayBetween([
 		setRemoteCode(),
		zwave.thermostatModeV2.thermostatModeSupportedGet().format(),
		zwave.thermostatFanModeV3.thermostatFanModeSupportedGet().format(),
		zwave.associationV1.associationSet(groupingIdentifier:1, nodeId:[zwaveHubNodeId]).format()
	], 2300)
}



def switchMode() {
	def currentMode = device.currentState("thermostatMode")?.value
	def lastTriedMode = getDataByName("lastTriedMode") ?: currentMode ?: "off"
	def supportedModes = getDataByName("supportedModes")
	def modeOrder = modes()
	def next = { modeOrder[modeOrder.indexOf(it) + 1] ?: modeOrder[0] }
	def nextMode = next(lastTriedMode)
	if (supportedModes?.tokenize()?.contains(currentMode)) {
		while (!supportedModes.tokenize()?.contains(nextMode) && nextMode != "off") {
			nextMode = next(nextMode)
		}
	}
	switchToMode(nextMode)
}

def switchToMode(nextMode) {
	def supportedModes = getDataByName("supportedModes")
	if(supportedModes && !supportedModes.tokenize()?.contains(nextMode)) log.warn "thermostat mode '$nextMode' is not supported"
	if (nextMode in modes()) {
		updateState("lastTriedMode", nextMode)
		return "$nextMode"()
	} else {
		log.debug("no mode method '$nextMode'")
	}
}

def switchFanMode() {
	def currentMode = device.currentState("thermostatFanMode")?.value
	def lastTriedMode = getDataByName("lastTriedFanMode") ?: currentMode ?: "off"
	def supportedModes = getDataByName("supportedFanModes") ?: "fanAuto fanLow"
	def modeOrder = ["fanAuto", "fanLow", "fanMed", "fanHigh"]
	def next = { modeOrder[modeOrder.indexOf(it) + 1] ?: modeOrder[0] }
	def nextMode = next(lastTriedMode)
	while (!supportedModes?.contains(nextMode) && nextMode != "fanAuto") {
		nextMode = next(nextMode)
	}
	switchToFanMode(nextMode)
}

def switchToFanMode(nextMode) {
	def supportedFanModes = getDataByName("supportedFanModes")
	if(supportedFanModes && !supportedFanModes.tokenize()?.contains(nextMode)) log.warn "thermostat mode '$nextMode' is not supported"

	def returnCommand
	if (nextMode == "fanAuto") {
		returnCommand = fanAuto()
	} else if (nextMode == "fanLow") {
		returnCommand = fanLow()
	} else if (nextMode == "fanMed") {
		returnCommand = fanMed()
   	} else if (nextMode == "fanHigh") {
		returnCommand = fanHigh()
	} else {
		log.debug("no fan mode '$nextMode'")
	}
	if(returnCommand) updateState("lastTriedFanMode", nextMode)
	returnCommand
}

def getDataByName(String name) {
	state[name] ?: device.getDataValue(name)
}

def setThermostatMode(String value) {
	delayBetween([
		zwave.thermostatModeV2.thermostatModeSet(mode: modeMap[value]).format(),
		zwave.thermostatModeV2.thermostatModeGet().format()
	])
}

def setThermostatFanMode(String value) {
	delayBetween([
		zwave.thermostatFanModeV3.thermostatFanModeSet(fanMode: fanModeMap[value]).format(),
		zwave.thermostatFanModeV3.thermostatFanModeGet().format()
	])
}

def off() {
	setThermostatMode("off")
}

def heat() {
	setThermostatMode("heat")
}

def emergencyHeat() {
	setThermostatMode("emergencyHeat")

}

def dry() {
	setThermostatMode("dry")

}

def cool() {
	setThermostatMode("cool")

}

def auto() {
	setThermostatMode("auto")

}


def autoChangeover() {
	setThermostatMode("autoChangeover")

}

def fanLow() {
	setThermostatFanMode("low")

}
def fanMed() {
	setThermostatMode("med")

}
def fanHigh() {
	setThermostatMode("high")
}

def fanAuto() {
	setThermostatMode("auto")

}

def raiseTemperature() {
	def deviceScale = state.scale ?: 1
	def deviceScaleString = deviceScale == 2 ? "C" : "F"
    def locationScale = getTemperatureScale()
	def p = (state.precision == null) ? 1 : state.precision
    
    def degrees = device.latestValue("thermostatSetpoint")
    def convertedDegrees
    def convertedMax
    if (locationScale == "C" && deviceScaleString == "F") {
    	convertedDegrees = celsiusToFahrenheit(degrees)
    } else if (locationScale == "F" && deviceScaleString == "C") {
    	convertedDegrees = fahrenheitToCelsius(degrees)
    } else {
    	convertedDegrees = degrees
    }
    
    if (deviceScaleString == "C") {
    	convertedMax = farenheitToCelsius(84)
    } else {
    	convertedMax = 84
    }
            
    if (convertedDegrees < convertedMax) {
		setThermostatSetpoint(degrees + 1)
    }

}

def lowerTemperature() {
	def deviceScale = state.scale ?: 1
	def deviceScaleString = deviceScale == 2 ? "C" : "F"
    def locationScale = getTemperatureScale()
	def p = (state.precision == null) ? 1 : state.precision
    
    def degrees = device.latestValue("thermostatSetpoint")
    def convertedDegrees
    def convertedMin
    if (locationScale == "C" && deviceScaleString == "F") {
    	convertedDegrees = celsiusToFahrenheit(degrees)
    } else if (locationScale == "F" && deviceScaleString == "C") {
    	convertedDegrees = fahrenheitToCelsius(degrees)
    } else {
    	convertedDegrees = degrees
    }
    
    if (deviceScaleString == "C") {
    	convertedMin = farenheitToCelsius(62)
    } else {
    	convertedMin = 62
    }

	if (convertedDegrees > convertedMax) {
		setThermostatSetpoint(degrees - 1)
    }
}


def setRemoteCode() {
	def remoteCodeVal = remoteCode.toInteger()
    def short remoteCodeLow = remoteCodeVal & 0xFF
    def short remoteCodeHigh = (remoteCodeVal >> 8) & 0xFF
    def remoteBytes = [remoteCodeHigh, remoteCodeLow]
    
    log.debug "New Remote Code: ${remoteBytes}"
    
    delayBetween ([
		zwave.configurationV1.configurationSet(configurationValue: remoteBytes, parameterNumber: 27, size: 2).format(),
		zwave.configurationV1.configurationGet(parameterNumber: 27).format()
    ])	
}

def switchFanOscillate() {
	def swingMode = (getDataByName("swingMode") == "Off")  // Load swingmode and invert it (0 => 1, 1 => 0)
    
    setFanOscillate(swingMode)
}

def setFanOscillate(swingMode) {
	def swingValue = swingMode ? 1 : 0
    
    delayBetween ([
	    zwave.configurationV1.configurationSet(configurationValue: [swingValue], parameterNumber: 33, size: 1).format(),
        zwave.configurationV1.configurationGet(parameterNumber:33).format()    
    ])
}