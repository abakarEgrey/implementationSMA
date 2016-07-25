package implementationSMA.messages;
import java.util.ArrayList;

import implementationSMA.Pair;
import implementationSMA.agents.InstanceAgents.InstanceAgent;
import implementationSMA.enumeration.MessageType;

public abstract class AbstractMessage {

	// This type is not really obligatory, it is more for readability than real
	// cast
	protected String abstractMessageType = "abstractMessage";
	private MessageType type;
	// this attribute is null if Abstract message is instanceof
	// contextAgentProposition
	
	private InstanceAgent instanceAgent;

	public AbstractMessage(MessageType type) {
		this.type = type;
		this.instanceAgent = null;
	}

	public AbstractMessage(MessageType type, InstanceAgent instanceAgent) {
		super();
		this.type = type;
		this.instanceAgent = instanceAgent;
	}

	public InstanceAgent getInstanceAgent() {
		return instanceAgent;
	}

	public MessageType getType() {
		return type;
	}

	private void setType(MessageType type) {
		this.type = type;
	}

	public static final Pair<ArrayList<ContextAgentProposition>, ArrayList<ServiceAgentMessage>> sortAbstractMIntoCAPandSAM(
			ArrayList<AbstractMessage> abstractMessages) {

		ArrayList<ContextAgentProposition> propositions = new ArrayList<ContextAgentProposition>();
		ArrayList<ServiceAgentMessage> saM = new ArrayList<ServiceAgentMessage>();

		for (AbstractMessage abstMessage : abstractMessages) {
			if (abstMessage.getType() == MessageType.PROPOSITIONAC) {
				if (abstMessage.getAbstractMessageType().equals("contextAgentProposition")) {
					propositions.add((ContextAgentProposition) abstMessage);
				} else {
					// TODO : error
					// Note, the enum was at first here for readability, may be
					// erased
				}
			} else if (abstMessage.getType() == MessageType.SAMESSAGE) {
				if (abstMessage.getAbstractMessageType().equals("serviceAgentMessage")) {
					saM.add((ServiceAgentMessage) abstMessage);
				} else {
					// TODO : error
					// Note, the enum was at first here for readability, may be
					// erased
				}
			} else {
				// TODO error
			}

		}
		return new Pair<ArrayList<ContextAgentProposition>, ArrayList<ServiceAgentMessage>>(
				propositions, saM);
	}

	/**
	 * 
	 * @return
	 */
	public String getAbstractMessageType() {
		return this.abstractMessageType;
	}
	/**
	 * 
	 * @param abstractMessageType
	 */
	public void setAbstractMessageType(String abstractMessageType){
		this.abstractMessageType = abstractMessageType;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}

		AbstractMessage otherAbstractMessage = (AbstractMessage) obj;
		return this.getAbstractMessageType().equals(
				otherAbstractMessage.getAbstractMessageType());

	}

}
