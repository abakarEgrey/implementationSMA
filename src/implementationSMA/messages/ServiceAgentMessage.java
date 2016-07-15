package implementationSMA;
import java.util.ArrayList;
import java.util.List;

import fr.irit.smac.libs.tooling.messaging.impl.Ref;

public class ServiceAgentMessage extends AbstractMessage {

	private int cardinality;
	private String senderType;
	private MessageType messageType;
	private Pair<Boolean, ArrayList<ServiceAgent>> serviceAgentState;
	private int nbOfConnection;
	private Double averageTOConnexion;
	// l'agent service qui envoie le message
	private ServiceAgent serviceAgent;
	// Variable permettant de compter le nombre de liaison de l'agent service
	private int nbLink;
	private Ref<AbstractMessage> refServiceAgent;
	private String serviceAgentMessageType = "serviceAgentMessage";
	private Action actionType;

	/**
	 * 
	 * @param cardinality
	 * @param senderType
	 * @param messageType
	 * @param serviceAgentState
	 * @param nbOfConnection
	 * @param averageTOConnexion
	 */
	public ServiceAgentMessage(int cardinality, String senderType, MessageType messageType,
			Pair<Boolean, ArrayList<ServiceAgent>> serviceAgentState, int nbOfConnection, Double averageTOConnexion,
			ServiceAgent serviceAgent, Action actionType) {
		super(MessageType.SAMESSAGE, serviceAgent.getInstanceAgent());
		this.cardinality = cardinality;
		this.senderType = senderType;
		this.messageType = messageType;
		// this.serviceAgentState = serviceAgentState;
		// effectuer une copie de l'état de l'agent service
		this.serviceAgentState = new Pair<Boolean, ArrayList<ServiceAgent>>(serviceAgentState.getFirst(),
				new ArrayList<>(serviceAgentState.getSecond()));
		
		this.nbOfConnection = nbOfConnection;
		this.averageTOConnexion = averageTOConnexion;
		this.serviceAgent = serviceAgent;
		this.nbLink = 0;
		this.refServiceAgent = serviceAgent.getMessageBox().getRef();
		this.setAbstractMessageType(serviceAgentMessageType);
		this.actionType = actionType;
	}

	public ServiceAgent getServiceAgent() {
		return this.serviceAgent;
	}

	/**
	 * 
	 * @param senderType
	 * @param messageType
	 * @param serviceAgentState
	 */
	public ServiceAgentMessage(String senderType, MessageType messageType,
			Pair<Boolean, ArrayList<ServiceAgent>> serviceAgentState) {
		super(MessageType.SAMESSAGE);
		this.senderType = senderType;
		this.messageType = messageType;
		this.serviceAgentState = serviceAgentState;
	}

	/**
	 * 
	 * @return
	 */
	public int getCardinality() {
		return cardinality;
	}

	/**
	 * 
	 * @return
	 */
	public String getSenderType() {
		return senderType;
	}

	/**
	 * 
	 * @return
	 */
	public MessageType getMessageType() {
		return messageType;
	}

	/**
	 * 
	 * @return
	 */
	public Pair<Boolean, ArrayList<ServiceAgent>> getServiceAgentState() {
		return serviceAgentState;
	}

	/**
	 * 
	 * @return
	 */
	public int getNbOfConnection() {
		return nbOfConnection;
	}

	/**
	 * 
	 * @return
	 */
	public Double getAverageTOConnexion() {
		return averageTOConnexion;
	}

	public Ref<AbstractMessage> getRefServiceAgent() {
		return refServiceAgent;
	}

	public Action getActionType() {
		return actionType;
	}

	public void setActionType(Action actionType) {
		this.actionType = actionType;
	}

	@Override
	public String getAbstractMessageType() {
		// TODO Auto-generated method stub
		return super.getAbstractMessageType();
	}

	public void display() {
		System.out.println("/*=================================================================================*/");
		System.out.println("id : " + this.getServiceAgent().getId());
		System.out.println("cardinalite : " + this.cardinality);
		System.out.println("senderType : " + this.senderType);
		System.out.println("messageType : " + this.messageType);
		System.out.print("serviceAgentState : (" + this.serviceAgentState.getFirst() + ", [");
		for (ServiceAgent sA : this.serviceAgentState.getSecond()) {
			System.out.print(sA.id + " ");
		}
		System.out.println("] )");
		;
		System.out.println("nbOfConnection : " + this.nbOfConnection);
		System.out.println("actionType : " + this.actionType);
		System.out.println("refServiceAgent" + this.refServiceAgent);
		System.out.println("/*=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=*/");
	}

}
