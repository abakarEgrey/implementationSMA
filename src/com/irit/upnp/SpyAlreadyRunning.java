package com.irit.upnp;

public class SpyAlreadyRunning extends Exception {
	private static final long serialVersionUID = 1L;

	public SpyAlreadyRunning(){
		super("Spy already running");
	}
}
