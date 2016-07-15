package com.irit.upnp;

public class SpyNotRunning extends Exception {
	private static final long serialVersionUID = 1L;

	public SpyNotRunning(){
		super("Spy already running");
	}
}