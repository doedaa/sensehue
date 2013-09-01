package nl.sensibleHue

import groovyjarjarantlr.collections.List
import de.jaetzold.philips.hue.HueBridge
import de.jaetzold.philips.hue.HueLight;
import de.jaetzold.philips.hue.HueLightBulb
import de.jaetzold.philips.hue.HueLightGroup

class sensehue {

	static main(args) {
/*		String credentials = "credentials.txt";
		Api.login(credentials);

		// verbosity
		Params.General.verbosity = true;*/
		
		def address = InetAddress.getByName("192.168.1.72");
		def bridge = new HueBridge(address,'8aefa072a354a7f113f6bf72b173e6f')
		if(!bridge.authenticate(false)) {
						println("Press the button on your Hue bridge in the next 30 seconds to grant access.")
						if(bridge.authenticate(true)) {
							println("Access granted. username: " + bridge.username)
						} else {
							println("Authentication failed.")
						}
					} else {
						println("Already granted access. username: " + bridge.username)
					}
		
		def bridges = [bridge]
		
		if(bridge.lights.isEmpty()) {
			println("No lights found.")
			System.exit(2)
		}
		
		def light = bridge.getLight(1)
		
		def lights = bridge.getLights()
		
		//lights.each {it.on = true; sweepBrightness(it, 1000)}
		
		/* Change various light states */
		light.on = true;
		
			//blinkOnce(light, 1000)
		
		HueLightGroup group = bridge.getGroup(0)
		//blinkOnce(group, 1000)      // Groups and LightBulbs share the same interface (containing only the state setters though)
		//sweepBrightness(HueLightBulb light, int waitMillis)
		light.transitionTime = 1    // this is used in all subsequent (non-transactional) state changes. 'null' uses the bridges default

		while(true){		
		group.hue = Math.random() * 65535;
		//group.on = false;
		group.saturation = Math.random() * 255;
		
		Thread.sleep(1000)
		group.on = true;
		group.brightness = 255
		Thread.sleep(1000)
		group.saturation = Math.random() * 255;
		group.hue = Math.random() * 65535
		Thread.sleep(2000);
		
		}
		
	}		

	def sweepColorTemperature(HueLightBulb light, int waitMillis) {
		print("sweepColorTemperature '$light.name': ")
		light.colorTemperature = 500
		print("500 ")
		Thread.sleep(waitMillis)
		light.colorTemperature = 450
		print("450 ")
		Thread.sleep(waitMillis)
		light.colorTemperature = 400
		print("400 ")
		Thread.sleep(waitMillis)
		light.colorTemperature = 350
		print("350 ")
		Thread.sleep(waitMillis)
		light.colorTemperature = 300
		print("300 ")
		Thread.sleep(waitMillis)
		light.colorTemperature = 250
		print("250 ")
		Thread.sleep(waitMillis)
		light.colorTemperature = 200
		print("200 ")
		Thread.sleep(waitMillis)
		light.colorTemperature = 153
		print("153 ")
		Thread.sleep(waitMillis)
		println()
	}
	
	def sweepCieXY(HueLightBulb light, int waitMillis) {
		print("sweepCieXY '$light.name': ")
		light.setCieXY(0, 0)
		print("0,0 ")
		Thread.sleep(waitMillis)
		light.setCieXY(0.5, 0)
		print("0.5,0 ")
		Thread.sleep(waitMillis)
		light.setCieXY(1, 0.5)
		print("1,0.5 ")
		Thread.sleep(waitMillis)
		light.setCieXY(1, 1)
		print("1,1 ")
		Thread.sleep(waitMillis)
		light.setCieXY(0.5, 1)
		print("0.5,1 ")
		Thread.sleep(waitMillis)
		light.setCieXY(0, 1)
		print("0,1 ")
		Thread.sleep(waitMillis)
		light.setCieXY(0, 0.5)
		print("0,0.5 ")
		Thread.sleep(waitMillis)
		light.setCieXY(0.5, 0.5)
		print("0.5,0.5 ")
		Thread.sleep(waitMillis)
		println()
	}
	
	def sweepHueSaturation(HueLightBulb light, int waitMillis) {
		print("sweepHueSaturation '$light.name': ")
		light.hue = HueLightBulb.HUE_RED
		light.saturation = 255
		print("RED saturated")
		Thread.sleep(waitMillis)
		light.saturation = 127
		Thread.sleep(waitMillis)
		light.saturation = 0
		print("-not saturated ")
		Thread.sleep(waitMillis)
		light.hue = HueLightBulb.HUE_GREEN
		print("GREEN not saturated")
		Thread.sleep(waitMillis)
		light.saturation = 127
		Thread.sleep(waitMillis)
		light.saturation = 255
		print("-saturated ")
		Thread.sleep(waitMillis)
		light.hue = HueLightBulb.HUE_BLUE
		print("BLUE saturated")
		Thread.sleep(waitMillis)
		light.saturation = 127
		Thread.sleep(waitMillis)
		light.saturation = 0
		print("-not saturated ")
		Thread.sleep(waitMillis)
		light.hue = HueLightBulb.HUE_RED_2
		print("RED_2 not saturated")
		Thread.sleep(waitMillis)
		light.saturation = 127
		Thread.sleep(waitMillis)
		light.saturation = 255
		print("-saturated ")
		Thread.sleep(waitMillis)
		println()
	}
	
	static def sweepBrightness(HueLightBulb light, int waitMillis) {
		println("sweepBrightness '$light.name'")
		light.brightness = 255
		Thread.sleep(waitMillis)
		light.brightness = 200
		Thread.sleep(waitMillis)
		light.brightness = 150
		Thread.sleep(waitMillis)
		light.brightness = 100
		Thread.sleep(waitMillis)
		light.brightness = 50
		Thread.sleep(waitMillis)
		light.brightness = 0
		Thread.sleep(waitMillis)
		light.brightness = 127
		Thread.sleep(waitMillis)
		light.brightness = 255
		Thread.sleep(waitMillis)
	}
	
	
	static def discoverAndAuthenticate() {
		List<HueBridge> bridges = HueBridge.discover()
		for(HueBridge bridge : bridges) {
			println("Found " + bridge)
			// You may need a better scheme to store your username that to just hardcode it.
			// suggestion: Save a mapping from HueBridge.getUDN() to HueBridge.getUsername() somewhere.
			bridge.username = '8aefa072a354a7f113f6bf72b173e6f';
			if(!bridge.authenticate(false)) {
				println("Press the button on your Hue bridge in the next 30 seconds to grant access.")
				if(bridge.authenticate(true)) {
					println("Access granted. username: " + bridge.username)
				} else {
					println("Authentication failed.")
				}
			} else {
				println("Already granted access. username: " + bridge.username)
			}
		}
		bridges
	}
	
	
	static def blinkOnce(HueLight light, int waitMillis) {
		println("blinkOnce '$light.name'")
		light.on = false;
		Thread.sleep(waitMillis);
		light.on = true;
		Thread.sleep(waitMillis);
	}
	

}
