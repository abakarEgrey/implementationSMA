package com.irit.upnp;

public class AlreadyRunning extends Exception{

	public AlreadyRunning(){
		super("The spy is already running");
	}
}
