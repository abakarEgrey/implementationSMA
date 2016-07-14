package implementationSMA;
public class ContextAgentProposition extends AbstractMessage {
	/**
	 * This class allows to get informations context agents proposals
	 */
	private ContextAgent contextAgent;
	private Action action;
	private ServiceAgentMessage serviceAgentMessage;
	private double confidence;
	private String contextAgentPropositionType = "contextAgentProposition";

	/**
	 * 
	 * @param contextAgent
	 * @param action
	 * @param serviceAgentMessage
	 */
	public ContextAgentProposition(ContextAgent contextAgent, Action action,
			ServiceAgentMessage serviceAgentMessage) {
		super(MessageType.PROPOSITIONAC);
		this.contextAgent = contextAgent;
		this.action = action;
		this.serviceAgentMessage = serviceAgentMessage;
		if (contextAgent != null) {
			this.confidence = contextAgent.getConfidence();
		} else {
			this.confidence = 0;
		}

		this.setAbstractMessageType(contextAgentPropositionType);

	}

	public ContextAgent getContextAgent() {
		return contextAgent;
	}

	public Action getAction() {
		return action;
	}

	public ServiceAgentMessage getServiceAgentMessage() {
		return serviceAgentMessage;
	}

	public double getConfidence() {
		return confidence;
	}

	public Double getConfidenceD() {
		// TODO Auto-generated method stub
		return new Double(confidence);
	}

	/**
	 * redefinition of this for the genericity
	 */
	@Override
	public String getAbstractMessageType() {
		// TODO Auto-generated method stub
		return super.getAbstractMessageType();
	}

	/**
	 * 
	 */
	public void display() {
		System.out
				.println("/*=================debut d'affichage d'une proposition de l'agent contexte====================*/");

		System.out.println("context agent = ");
		if (this.contextAgent != null) {
			this.contextAgent.display();
		}
		System.out.println("action = " + this.action);
		if (this.serviceAgentMessage != null){
			System.out.println("serviceAgentMessage = ");
			this.serviceAgentMessage.display();
		} else {
			System.out.println("serviceAgentMessage = null");
		}
		
		System.out.println("confidence = " + this.confidence);
		System.out.println("contextAgentPropositionType = "
				+ this.contextAgentPropositionType);
		System.out
				.println("/*=/=/=/=/=/=/=/=/=/=/fin affichage proposition agent contexte/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=*/");

	}

}
