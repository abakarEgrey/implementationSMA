package com.irit.upnp;



public class App {

	public static void main( String[] args )
    {
        ContainerWComp c = new ContainerWComp("Container1_Structural_0");
        
        
        try {
        	
        	pause(20000);
			String winamp = c.createBeanAtPos("Winamp", "WComp.UPnPDevice.WinampRemote", 400, 200);

			pause(10000);
			String bouton1 = c.createBeanAtPos("Bouton 1", "System.Windows.Forms.Button", 600, 400);
			String bouton2 = c.createBeanAtPos("Bouton 2", "System.Windows.Forms.Button", 200, 400);
        	
			pause(2000);
			String linkBut1 = c.createLink("Bouton 1", "Click", "Winamp", "NextSong", "");
			String linkBut2 = c.createLink("Bouton 2", "Click", "Winamp", "PreviousSong", "");
			
        	pause(20000);
        	String buttonArd = c.createBeanAtPos("Arduino Bouton", "WComp.UPnPDevice.Arduino_Button", 200, 100);
			
        	pause(10000);
        	c.removeLink(linkBut1);
        	c.removeLink(linkBut2);
        	
        	pause(2000);
			String linkNext = c.createLink("Arduino Bouton", "Button1_Event", "Winamp", "NextSong", "");
			String linkPrevious = c.createLink("Arduino Bouton", "Button2_Event", "Winamp", "PreviousSong", "");

			pause(10000);
			String impressJS = c.createBeanAtPos("ImpressJS", "WComp.UPnPDevice.ImpressJS", 400, 100);
			
			pause(2000);
			linkBut1 = c.createLink("Bouton 1", "Click", "ImpressJS", "Next", "");
			linkBut2 = c.createLink("Bouton 2", "Click", "ImpressJS", "Previous", "");
			
			
			pause(20000);
			String scrollBar = c.createBeanAtPos("Scroll 1", "System.Windows.Forms.HScrollBar", 400, 400);
			c.setPropertyValue("Scroll 1", "Maximum", "﻿<?xml version=\"1.0\" encoding=\"utf-8\"?><int>3</int>");
			c.setPropertyValue("Scroll 1", "LargeChange", "﻿<?xml version=\"1.0\" encoding=\"utf-8\"?><int>1</int>");
			
			pause(10000);
        	c.removeLink(linkNext);
        	c.removeLink(linkPrevious);
        	
        	pause(2000);
			String linkScroll = c.createLink("Scroll 1", "ValueChanged", "Winamp", "jumpTo", "get_Value");

        	pause(10000);
        	c.removeLink(linkBut1);
        	c.removeLink(linkBut2);
        	
			pause(10000);
			String linkImpressJSNext = c.createLink("Arduino Bouton", "Button1_Event", "ImpressJS", "Next", "");
			String linkImpressJSPrevious = c.createLink("Arduino Bouton", "Button2_Event", "ImpressJS", "Previous", "");
			
			pause(10000);
			c.removeBean("Bouton 1");
			c.removeBean("Bouton 2");
			
			pause(10000);
			String joystickArd = c.createBeanAtPos("Arduino Joystick", "WComp.UPnPDevice.Arduino_Joystick", 200, 200);
			
			pause(2000);
			c.removeLink(linkNext);
			c.removeLink(linkPrevious);
			
			pause(2000);
			String linkUp = c.createLink("Arduino Joystick", "X_Up_Event", "Winamp", "VolumeUp", "");
			String linkDown = c.createLink("Arduino Joystick", "X_Down_Event", "Winamp", "VolumeDown", "");
			String linkLeft = c.createLink("Arduino Joystick", "Y_Left_Event", "Winamp", "PreviousSong", "");
			String linkRight = c.createLink("Arduino Joystick", "Y_Right_Event", "Winamp", "NextSong", "");

			
			pause(3000);
			c.stopSpy();
		} catch (NoDevice e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoService e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLaunched e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorContainer e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SpyNotRunning e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public static void pause(long ms){
        try {
			Thread.sleep(ms);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    }

}
