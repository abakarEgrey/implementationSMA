package com.irit.upnp;

public class NotLaunched extends Exception{
	private static final long serialVersionUID = 1L;

	public NotLaunched(){
		super("The spy isn't running.");
	}
}
