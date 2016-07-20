package exceptions;

public class RemovedLink extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param linkNotExist
	 */
	public RemovedLink(String linkNotExist){
		super("The Link" + linkNotExist +  " is removed before or not exist.");
	}
	
	

}
