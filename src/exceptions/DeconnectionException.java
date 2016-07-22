package exceptions;

public class DeconnectionException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public DeconnectionException(String agentNotConnected){
		super(agentNotConnected);
	}
}
