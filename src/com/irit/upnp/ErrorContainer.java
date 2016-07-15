package com.irit.upnp;

public class ErrorContainer extends Exception{
	private static final long serialVersionUID = 1L;

	public ErrorContainer(String error){
		super("Error : "+ error);
	}
}
