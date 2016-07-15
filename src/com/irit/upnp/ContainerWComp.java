package com.irit.upnp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.fourthline.cling.model.types.InvalidValueException;

public class ContainerWComp extends Spy{

	private String serviceId;
	
	/**
	 * Cette variable retournera le nom de la bean crée contenu dans le map
	 * grace à la clé de type string retourné lors de la creation du beans
	 */
	private Map<String, Map<String, Object>> beanCreationIdAndMap;
	
	/**
	 * Finds the controller from his friendly name.
	 * @param _friendlyName The friendly name of the structural service.
	 */
	public ContainerWComp(String _friendlyName){
		super(_friendlyName);
		serviceId="StructuralService";
		this.beanCreationIdAndMap = new HashMap<>();
	}
	
	/**
	 * 
	 * @return
	 */
	
	public Map<String, Map<String, Object>> getBeanCreationIdAndMap() {
		return beanCreationIdAndMap;
	}



	/**
	 * Add a bean to the container. <br>
	 * Example : createBean("light", "WComp.UPnPDevice.Light");
	 * @param beanName Bean name
	 * @param beanType Bean type
	 * @return SUCCESS if the bean is created, ERROR if not (or already exists)
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
	public String createBean(String beanName ,String beanType) 
			throws NoDevice, NoService, NotLaunched, ErrorContainer {
		
		HashMap<String, Object> arg = new HashMap<>();
		
		String actionName = "CreateBean";
		arg.put("beanType", beanType);
		arg.put("beanName", beanName);
		
		ArrayList<Object> res;
		try {
			res = super.launchAction(serviceId,actionName, arg);
			if(res == null)
				throw new ErrorContainer("No return");
			if(res.isEmpty())	
				throw new ErrorContainer("Error in return, no result.");
			if(res.size()>1)
				throw new ErrorContainer("To many results");
			if(!res.get(0).getClass().getSimpleName().equals("String"))
				throw new ErrorContainer("The return object is wrong");
			//Ajout de la map arg et res dans le map this.beanCreationIdAndMap
			this.beanCreationIdAndMap.put(beanName, arg);
			
			return (String) res.get(0);
		} catch (InvalidValueException e) {
			throw new ErrorContainer("Wrong value");
		} catch (NoDevice e) {
			throw e;
		} catch (NoService e) {
			throw e;
		} catch (NotLaunched e) {
			throw e;
		}
		
	}
	
	/**
	 * Add a bean to the container to a position. <br>
	 * Example : createBean("light", "WComp.UPnPDevice.Light", 200, 100);
	 * @param beanName Bean name
	 * @param beanType Bean type
	 * @param x X position
	 * @param y Y position
	 * @return SUCCESS if the bean is created, ERROR if not (or already exists)
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
	public String createBeanAtPos(String beanName ,String beanType, int x, int y) 
			throws NoDevice, ErrorContainer, NoService, NotLaunched {
		HashMap<String, Object> arg = new HashMap<>();
		
		String actionName = "CreateBeanAtPos";
		arg.put("beanType", beanType);
		arg.put("beanName", beanName);
		arg.put("x", x);
		arg.put("y", y);
		
		try {
			ArrayList<Object> res;
			res = super.launchAction(serviceId,actionName, arg);
			
			if(res == null)
				throw new ErrorContainer("No return");
			if(res.isEmpty())
				throw new ErrorContainer("Error in return, no result.");
			if(res.size()>1)
				throw new ErrorContainer("To many results");
			if(!res.get(0).getClass().getSimpleName().equals("String"))
				throw new ErrorContainer("The return object is wrong");
			//Ajout de la map arg et res dans le map this.beanCreationIdAndMap
			this.beanCreationIdAndMap.put((String) res.get(0), arg);
			return (String) res.get(0);
		} catch (InvalidValueException e) {
			throw new ErrorContainer("Wrong value");
		} catch (NoDevice e) {
			throw e;
		} catch (NoService e) {
			throw e;
		} catch (NotLaunched e) {
			throw e;
		}
	}
	
	/**
	 * Removes the bean from the instanced name.
	 * @param instName Instanced name
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
	public void removeBean(String instName) 
			throws NoDevice, ErrorContainer, NoService, NotLaunched {
		String actionName = "RemoveBean";
		
		HashMap<String, Object> arg = new HashMap<>();
		arg.put("instName", instName);
		
		try {
			super.launchAction(serviceId,actionName, arg);
		} catch (InvalidValueException e) {
			throw new ErrorContainer("Wrong value");
		} catch (NoDevice e) {
			throw e;
		} catch (NoService e) {
			throw e;
		} catch (NotLaunched e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * Creates a link between 2 beans. <br>
	 * Difference from the original WComp : returns the link name
	 * @param source Source bean
	 * @param srcEvent The event sent by the source 
	 * @param destination Destination bean
	 * @param dstAction The action to call when the srcEvent event is received
	 * @param dstParams Destination parameters to the action
	 * @return Link name
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
	public String createLink(String source ,String srcEvent, String destination, String dstAction, String dstParams)
			throws NoDevice, ErrorContainer, NoService, NotLaunched {
		String actionName = "CreateLink";
		
		HashMap<String, Object> arg = new HashMap<>();
		arg.put("source", source);
		arg.put("srcEvent", srcEvent);
		arg.put("destination", destination);
		arg.put("dstAction", dstAction);
		arg.put("dstParams", dstParams);
		
		try {
			super.launchAction(serviceId,actionName, arg);
			return "link-"+source+"-"+destination+"-"+srcEvent+"-"+dstAction+"-"+dstParams;
		} catch (InvalidValueException e) {
			throw new ErrorContainer("Wrong value");
		} catch (NoDevice e) {
			throw e;
		} catch (NoService e) {
			throw e;
		} catch (NotLaunched e) {
			throw e;
		}
	}
	
	/**
	 * Remove a link from a link name.
	 * @param linkName Link name
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
	public void removeLink(String linkName) 
			throws NoDevice, ErrorContainer, NoService, NotLaunched {
		String actionName = "RemoveLink";
		
		HashMap<String, Object> arg = new HashMap<>();
		arg.put("linkName", linkName);
		
		try {
			super.launchAction(serviceId,actionName, arg);
		} catch (InvalidValueException e) {
			throw new ErrorContainer("Wrong value");
		} catch (NoDevice e) {
			throw e;
		} catch (NoService e) {
			throw e;
		} catch (NotLaunched e) {
			throw e;
		}
	}
	
	/**
	 * Get all the bean names.
	 * @return String array of bean names
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
	public String[] getBeanNames()
			throws NoDevice, ErrorContainer, NoService, NotLaunched {
		String actionName = "GetBeanNames";
		
		ArrayList<Object> res;
		try {
			res = super.launchAction(serviceId,actionName, null);
			if(res == null)
				throw new ErrorContainer("No return");
			if(res.isEmpty())
				throw new ErrorContainer("Error in return, no result.");
			if(res.size()>1)
				throw new ErrorContainer("To many results");
			if(!res.get(0).getClass().getSimpleName().equals("String"))
				throw new ErrorContainer("The return object is wrong");

			return ((String) res.get(0)).split("\n");
		} catch (InvalidValueException e) {
			throw new ErrorContainer("Wrong value");
		} catch (NoDevice e) {
			throw e;
		} catch (NoService e) {
			throw e;
		} catch (NotLaunched e) {
			throw e;
		}
	}
	
	/**
	 * Get the beans.
	 * @return String array of beans
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
public String[] getBeans()
		throws NoDevice, ErrorContainer, NoService, NotLaunched {
	String actionName = "GetBeans";

	ArrayList<Object> res;
	try {
		res = super.launchAction(serviceId,actionName, null);
		
		if(res == null)
			throw new ErrorContainer("No return");
		if(res.isEmpty())
			throw new ErrorContainer("Error in return, no result.");
		if(res.size()>1)
			throw new ErrorContainer("To many results");
		if(!res.get(0).getClass().getSimpleName().equals("String"))
			throw new ErrorContainer("The return object is wrong");

		return ((String) res.get(0)).split("\n");
	} catch (InvalidValueException e) {
		throw new ErrorContainer("Wrong value");
	} catch (NoDevice e) {
		throw e;
	} catch (NoService e) {
		throw e;
	} catch (NotLaunched e) {
		throw e;
	}
}
	
	/**
	 * Move the position of a bean in the container.
	 * @param beanName Bean name
	 * @param x New x position
	 * @param y New y position
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
	public void moveBean(String beanName, int x, int y) 
			throws NoDevice, ErrorContainer, NoService, NotLaunched {
		
		String actionName = "MoveBean";
		
		HashMap<String, Object> arg = new HashMap<>();
		arg.put("beanName", beanName);
		arg.put("x", x);
		arg.put("y", y);
		
		try {
			super.launchAction(serviceId,actionName, arg);
		} catch (InvalidValueException e) {
			throw new ErrorContainer("Wrong value");
		} catch (NoDevice e) {
			throw e;
		} catch (NoService e) {
			throw e;
		} catch (NotLaunched e) {
			throw e;
		}
	}
	
	/**
	 * Sets a property value (must be XML)
	 * Example : 
	 *  setPropertyValue("Scroll 1", "Maximum", "ï»¿<?xml version=\"1.0\" encoding=\"utf-8\"?><int>3</int>");
	 * @param instName  Instanced name
	 * @param propName Property name
	 * @param propValue Property value (in xml)
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
	public void setPropertyValue(String instName, String propName, String propValue) 
			throws NoDevice, ErrorContainer, NoService, NotLaunched {
		
		String actionName = "SetPropertyValue";
		
		HashMap<String, Object> arg = new HashMap<>();
		arg.put("instName", instName);
		arg.put("propName", propName);
		arg.put("propValue", propValue);
		
		try {
			super.launchAction(serviceId,actionName, arg);
		} catch (InvalidValueException e) {
			throw new ErrorContainer("Wrong value");
		} catch (NoDevice e) {
			throw e;
		} catch (NoService e) {
			throw e;
		} catch (NotLaunched e) {
			throw e;
		}
	}
	

	/**
	 * Get the property value of a bean.
	 * @param instName  Instanced name
	 * @param propName Property name
	 * @return de value in xml format
	 * @throws NoDevice If the device is not connected
	 * @throws NoService If the service is not found
	 * @throws NotLaunched If the spy isn't launched
	 * @throws ErrorContainer If the method has a problem during the call
	 */
	public String getPropertyValue(String instName, String propName)
			throws NoDevice, ErrorContainer, NoService, NotLaunched {
		String actionName = "GetPropertyValue";
	
		HashMap<String, Object> arg = new HashMap<>();
		arg.put("instName", instName);
		arg.put("propName", propName);
		
		ArrayList<Object> res;
		try {
			res = super.launchAction(serviceId,actionName, arg);
			
			if(res == null)
				throw new ErrorContainer("No return");
			if(res.isEmpty())
				throw new ErrorContainer("Error in return, no result.");
			if(res.size()>1)
				throw new ErrorContainer("To many results");
			if(!res.get(0).getClass().getSimpleName().equals("String"))
				throw new ErrorContainer("The return object is wrong");
	
			String resF;
			if(!((String)res.get(0)).startsWith("<"))
				resF = ((String)res.get(0)).substring(1, ((String)res.get(0)).length());
			else
				resF =  (String)res.get(0);
			
			return resF;
		} catch (InvalidValueException e) {
			throw new ErrorContainer("Wrong value");
		} catch (NoDevice e) {
			throw e;
		} catch (NoService e) {
			throw e;
		} catch (NotLaunched e) {
			throw e;
		}
	}

}
