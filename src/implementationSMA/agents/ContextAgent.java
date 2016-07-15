package implementationSMA.agents;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.irit.smac.libs.tooling.messaging.AgentMessaging;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.messaging.impl.Ref;
import implementationSMA.Pair;
import implementationSMA.enumeration.Action;
import implementationSMA.messages.AbstractMessage;
import implementationSMA.messages.ContextAgentProposition;
import implementationSMA.messages.ServiceAgentMessage;

public class ContextAgent extends Agent {
	// TODO General TODO: make criterion variables?

	// Properties
	private ServiceAgent serviceAgent;
	// TODO id??
	private final Double LAMBDA = 0.2;

	private double confidence;

	// private ArrayList<String> correspondingMessages; // TODO to change for
	// actual
	// type

	// Ranges of Validity
	// private ArrayList<ArrayList<ServicesStateVR>> neightboursState;
	// private int nbOfConnection
	// private double averageTOConnexion;
	// private Cardinality

	private ArrayList<Pair<Boolean, ServiceAgent>> neightboursState; // (same
	// instance,
	// agent
	// type)
	private Action actionPerformed;
	private String senderType;
	private Action messageType;
	private Pair<Boolean, ArrayList<ServiceAgent>> serviceAgentState;

	// Necessary for the perceive-decide-act cycle distinction
	private boolean isValid;

	// Necessary for the perceive-decide-act cycle distinction and because an
	// agent may respond to multiple services messages
	private ArrayList<ServiceAgentMessage> listValidatingSAMessage;
	private IMsgBox<AbstractMessage> messageBox;
	private Ref<AbstractMessage> refAgentService;
	
	// private int feedBack;

	// Constructor
	// public ContextAgent(String senderType, Action messageType,
	// ArrayList<ArrayList<ServicesStateVR>> neightboursState,
	// boolean serviceAgentState, String typeConnectedAgent) {
	// this.senderType = senderType;
	// this.messageType = messageType;
	// this.neightboursState = neightboursState;
	// this.serviceAgentState = serviceAgentState;
	// this.typeConnectedAgent = typeConnectedAgent;
	// }
	/**
	 * 
	 * @param senderType
	 * @param messageType
	 * @param neightboursState
	 * @param serviceAgentState
	 * @param actionPermed
	 * @param serviceAgent
	 * @param confidence
	 */
	public ContextAgent(String senderType, Action messageType, ArrayList<Pair<Boolean, ServiceAgent>> neightboursState,
			Pair<Boolean, ArrayList<ServiceAgent>> serviceAgentState, Action actionPerformed, ServiceAgent serviceAgent,
			double confidence, String id) {
		this.id = id;
		this.senderType = senderType;

		this.messageType = messageType;
		this.actionPerformed = actionPerformed;
		this.serviceAgentState = serviceAgentState;
		this.neightboursState = neightboursState;
		this.serviceAgent = serviceAgent;
		this.confidence = confidence;
		this.messageBox = (IMsgBox<AbstractMessage>) AgentMessaging.getMsgBox(id, AbstractMessage.class);
		this.refAgentService = serviceAgent.getMessageBox().getRef();
		// this.feedBack = 0;
		this.listValidatingSAMessage = new ArrayList<ServiceAgentMessage>();
		// this.ArrayListServiceAgentMessage = new
		// ArrayList<ServiceAgentMessage>();
		// ??
	}

	// /**
	// *
	// * @param senderType
	// * @param messageType
	// * @param neightboursState
	// * @param serviceAgentState
	// * @param actionPermed
	// * @param cardinality
	// * @param nbOfConnection
	// * @param averageTOConnexion
	// * @param serviceAgent
	// */
	// public ContextAgent(String senderType, Action messageType,
	// ArrayList<Pair<Boolean, ArrayList<ServiceAgent>>> neightboursState,
	// Pair<Boolean, ArrayList<Agent>> serviceAgentState, Action actionPermed,
	// int
	// cardinality, int nbOfConnection,
	// Double averageTOConnexion, ServiceAgent serviceAgent, double confidence)
	// {
	// this.senderType = senderType;
	//
	// this.messageType = messageType;
	// this.neightboursState = neightboursState;
	// this.serviceAgentState = serviceAgentState;
	// this.typeConnectedAgent = typeConnectedAgent;
	// this.actionPerformed = actionPermed;
	// this.cardinality = cardinality;
	// this.nbOfConnection = nbOfConnection;
	// this.averageTOConnexion = averageTOConnexion;
	// this.serviceAgent = serviceAgent;
	// this.confidence = confidence;
	// }

	// Accessors and mutators

	public Double getConfidenceD() {
		// Implemented for comparable need
		return new Double(confidence);
	}

	/*
	 * public int getFeedBack() { return feedBack; }
	 */

	/*
	 * public void setFeedBack(int feedBack) { this.feedBack = feedBack; }
	 */

	public double getConfidence() {
		return confidence;
	}

	private void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public ServiceAgent getServiceAgent() {
		return serviceAgent;
	}

	private void setServiceAgent(ServiceAgent serviceAgent) {
		this.serviceAgent = serviceAgent;
	}

	public String getSenderType() {
		return senderType;
	}

	private void setSenderType(String senderType) {
		this.senderType = senderType;
	}

	public Action getMessageType() {
		return messageType;
	}

	private void setMessageType(Action messageType) {
		this.messageType = messageType;
	}

	public ArrayList<Pair<Boolean, ServiceAgent>> getNeightboursState() {
		return neightboursState;
	}

	private void setNeightboursState(ArrayList<Pair<Boolean, ServiceAgent>> neightboursState) {
		this.neightboursState = neightboursState;
	}

	public Action getActionPerformed() {
		return actionPerformed;
	}

	private void setActionPerformed(Action actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

	public Pair<Boolean, ArrayList<ServiceAgent>> getServiceAgentState() {
		return serviceAgentState;
	}

	private void setServiceAgentState(Pair<Boolean, ArrayList<ServiceAgent>> serviceAgentState) {
		this.serviceAgentState = serviceAgentState;
	}

	public boolean isValid() {
		return isValid;
	}

	private void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	// Life cycle

	public Ref<AbstractMessage> getRefAgentService() {
		return refAgentService;
	}

	@Override
	protected void perceive() {
		// New cycle, e
		System.out.println("perceive: " + this.getId() + " Agent Contexte: je suis en cours d'exécution");
		isValid = false;
		listValidatingSAMessage.clear();
		// validité de l'agent contexte lorsque l'agent service effectue une
		// annonce pour la 2e fois
		if (this.serviceAgent.getServiceMessages().isEmpty()) {
			// regarder les caractéristiques de l'agent service, s'il y a un
			// changement ou pas

		} else {

		} // fin new if

		if (isBasicCriterionValid(serviceAgent.getCurrentServiceState())) {
			// Neightbours
			// TODO to change change.........................................
			ArrayList<Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>>> _actualNeighboursState = getActualNeighboursState();
			if (isNeighboursStateValid(_actualNeighboursState)) {

				ArrayList<ServiceAgentMessage> mReceivedByAS = this.serviceAgent.getServiceMessages();

				// validité de l'agent contexte lorsque l'agent service effectue
				// une
				// annonce pour la 2e fois
				if (mReceivedByAS.isEmpty()) {
					// regarder les caractéristiques de l'agent service, s'il y
					// a un
					// changement ou pas. L'etat de l'agent service
					// ()serviceAgentState) est verifié par la méthode
					// isBasicCriterionValid et les etats des voisins par la
					// methode isNeighboursStateValid
					if (isSenderTypeAndMessageTypeValid()) {
						isValid = true;

					}

				} else {
					for (ServiceAgentMessage saMessage : mReceivedByAS) {
						// Read every messages of its service agent
						// If valid stock the message is a list to answer to it
						// later
						if (isMessageCriterionValid(saMessage.getSenderType(), saMessage.getActionType(),
								saMessage.getServiceAgent(), _actualNeighboursState)) {
							// The context agent is valid for at least this
							// message
							isValid = true;
							listValidatingSAMessage.add(saMessage);
						}
					}

				}

			}

		}
		System.out.println("perceive : " + this.getId() + " Agent Contexte: mon execution est terminée");
	}

	private boolean isSenderTypeAndMessageTypeValid() {
		// TODO Auto-generated method stub
		if ((this.senderType.equals(this.serviceAgent.getInstanceAgent().getType())) && (this.messageType == null)) {
			// this.message = null ici dans le cas ou un agent contexte est créé
			// sans avoir à repondre un message
			return true;
		}
		return false;
	}

	@Override
	protected void decide() {
		System.out.println("decide: " + this.getId() + " Agent Contexte: je suis en cours d'exécution");
		// A contextAgent may be valid for multiple messages, for all those
		// messages he will send a proposition to its service agent
		// No real decision

		System.out.println("decide : " + this.getId() + " Agent Contexte: mon execution est terminée");
	}

	@Override
	protected void act() {
		System.out.println("act : " + this.getId() + " Agent Contexte: je suis en cours d'exécution");
		if (isValid) {
			System.out.println(this.getId() + "je suis valide ");
			if (listValidatingSAMessage.isEmpty()) {
				// TODO serviceAgentMessage est null car l'agent contexte ne
				// repond a aucun message. Il faut tester le service agent
				// message s'il est null ou pas dans les classes qui l'utilisent
				ContextAgentProposition contextAgentProposition = new ContextAgentProposition(this,
						this.actionPerformed, null);
				messageBox.send(contextAgentProposition, refAgentService);
			} else {
				// Sends its proposition to its serviceAgent
				for (ServiceAgentMessage saM : listValidatingSAMessage) {
					ContextAgentProposition msg = new ContextAgentProposition(this, this.actionPerformed, saM);
					messageBox.send(msg, refAgentService);
				}
				listValidatingSAMessage.clear();
			}
		} else {
			System.out.println(this.getId() + "je suis invalide ");
		}
		System.out.println("act : " + this.getId() + " Agent Contexte: mon execution est terminée");
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		if (this.confidence < 0.1) {
			this.serviceAgent.remove(this);
		}
	}

	// Methodes used during Life Cycle

	private ArrayList<ArrayList<Pair<Boolean, ServiceAgent>>> getActualNeighboursStateOld() {
		ArrayList<ArrayList<Pair<Boolean, ServiceAgent>>> actualNeighboursState = this.serviceAgent
				.getActualNeighboursStateOld();
		if (!actualNeighboursState.isEmpty()) {
			if (actualNeighboursState.size() == this.neightboursState.size()) {
				// The size is good: normal behavior
			} else {
				// TODO send an error
			}
		}
		return actualNeighboursState;
	}
	//pour l'ancienne méthode qui regargde l'etat des agents services voisins
	private ArrayList<Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>>> getActualNeighboursState() {
		ArrayList<Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>>> actualNeighboursState = this.serviceAgent
				.getActualNeighboursState();
		if (!actualNeighboursState.isEmpty()) {
			if (actualNeighboursState.size() == this.neightboursState.size()) {
				// The size is good: normal behavior
			} else {
				// TODO send an error
			}
		}
		return actualNeighboursState;
	}
	
	private ArrayList<ServiceAgent> getConnectedAgents() {
		return serviceAgent.getConnectedAgents();
	}

	/**
	 * @param _serviceAgentState
	 *            : the state (connected) of the serviceAgent
	 * @param _typeConnectedAgent
	 *            : the type of the ServiceAgent with which the serviceAgent is
	 *            connected
	 * @return : if the context agent is valid for criterion about state of
	 *         ServiceAgents i.e. if the context is reproduced for these
	 *         criterion
	 */
	private boolean isBasicCriterionValid(Pair<Boolean, ArrayList<ServiceAgent>> _actualServiceAgentState) {
		// Validity of the connection range of the context agent
		//je commente le test pour voir si l'agent est connecté ou pas
		/*if (!(((serviceAgentState.getFirst() && _actualServiceAgentState.getFirst())
				|| ((!serviceAgentState.getFirst() && (!_actualServiceAgentState.getFirst())))))) {
			// TODO may need to be changed if bool can become either
			return false;
		}*/

		// Check if one of the connection is correlated with the type range
		// if
		// (hasSimpleTypeCorrelation(serviceAgentState.getSecond(),_actualServiceAgentState.getSecond()))
		// {
		if (!isServiceAgentConnectedTypeValid(_actualServiceAgentState)) {
			return false;
		}

		return true;
	}

	private boolean isServiceAgentConnectedTypeValid(Pair<Boolean, ArrayList<ServiceAgent>> _actualServiceAgentState) {
		boolean isV = false;
		ArrayList<ServiceAgent> servicesAgent = _actualServiceAgentState.getSecond();
		// Discuter et se mettre d'accords sur la notion de type
		if (!serviceAgentState.getSecond().isEmpty()) {
			for (int i = 0; i < servicesAgent.size(); i++) {
				/*
				 * if (servicesAgent.get(i).getClass().getName().equals(
				 * serviceAgentState.getSecond())) { isV = true; break; }
				 */
				if (servicesAgent.get(i).getInstanceAgent().getType()
						.equals(serviceAgentState.getSecond().get(i).getInstanceAgent().getType())) {
					isV = true;
					break;
				}
			}
			return isV;
		}
		return true;
	}

	/**
	 * 
	 * @param _senderType
	 *            : the type of the InstanceAgent of the sender (which is a
	 *            ServiceAgent)
	 * @param _messageType
	 *            : the type of the message (see Action)
	 * @return : if the context agent is valid for criterion based on messages
	 *         i.e. if the context is reproduced for these criterion
	 */
	private boolean isMessageCriterionValid(String _senderType, Action _messageType, ServiceAgent sender,
			ArrayList<Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>>> _actualNeighboursState) {
		boolean isV = true;
		if (_senderType == null || senderType == null || !_senderType.equals(senderType)) {
			// TODO change if become a list
			return false;
		}
		if (_messageType == null || messageType == null || !_messageType.equals(messageType)) {
			return false;
		}

		// for (ArrayList<Pair<Boolean, ServiceAgent>> actualNState :
		// _actualNeighboursState)
		/*for (int i = 0; i < _actualNeighboursState.size(); i++) {
			Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>> actualNState = _actualNeighboursState.get(i);
			// boolean sameInstance;

			if (neightboursState.get(i).getFirst()) {
				isV = false;
				for (Pair<Boolean, ServiceAgent> connectedSA : actualNState) {
					if (connectedSA.getSecond().equals(sender)) {
						isV = true;
						break;
					}
				}
				if (!isV) {
					break;
				}
			} else {
				isV = true;
				for (Pair<Boolean, ServiceAgent> connectedSA : actualNState) {
					if (connectedSA.getSecond().equals(sender)) {
						isV = false;
						break;
					}
				}
				if (!isV) {
					break;
				}
			}
		}*/

		return isV;
	}

	// TODO: to change
	private boolean isNeighboursStateValidOld(ArrayList<ArrayList<Pair<Boolean, ServiceAgent>>> actualNeighboursState) {

		boolean isV = true;
		if (!actualNeighboursState.isEmpty()) {
			if (actualNeighboursState.size() == neightboursState.size()) {
				ArrayList<Pair<Boolean, ServiceAgent>> actualNState;
				boolean isNeiV = false;

				// The size is good: normal behavior
				// Look for every neighbour (isNeiV) if the expected neighbour
				// context
				// really is
				for (int i = 0; i < actualNeighboursState.size(); i++) {
					actualNState = actualNeighboursState.get(i);
					isNeiV = isNeighbourV(actualNState);

					if (!isNeiV) {
						isV = false;
						break;
					}

				}
			} else {
				// TODO send an error
			}
		}
		return isV;
	}
	//...
	private boolean isNeighboursStateValid(ArrayList<Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>>> _actualNeighboursState) {

		boolean isV = true;
		if (!_actualNeighboursState.isEmpty()) {
			if (_actualNeighboursState.size() == neightboursState.size()) {
				//ArrayList<Pair<Boolean, ServiceAgent>> actualNState;
				Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>> actualNState;
				boolean isNeiV = false;

				// The size is good: normal behavior
				// Look for every neighbour (isNeiV) if the expected neighbour
				// context
				// really is
				for (int i = 0; i < _actualNeighboursState.size(); i++) {
					actualNState = _actualNeighboursState.get(i);
					//isNeiV = isNeighbourV(actualNState);
					isNeiV = (actualNState.getSecond().getFirst() == this.neightboursState.get(i).getFirst());
					if (!isNeiV) {
						isV = false;
						break;
					}

				}
			} else {
				// TODO send an error
			}
		}
		return isV;
	}

	// TODO: to change, we want to know if the message sender is the same
	// instance than the one connected to not what it is now
	// private boolean isNeighbourV(ArrayList<Pair<Boolean, ServiceAgent>>
	// actualNState) {
	// boolean isNeiV = false;
	// // Search a simple correlation
	// for (int j = 0; j < actualNState.size(); j++) {
	// if ((actualNState.get(j).getFirst() &&
	// neightboursState.get(j).getFirst())
	// || ((!actualNState.get(j).getFirst()) &&
	// (!neightboursState.get(j).getFirst()))) {
	// if
	// (actualNState.get(j).getSecond().getClass().getName().equals(neightboursState.get(j).getSecond()))
	// {
	// isNeiV = true;
	// break;
	// }
	// }
	// }
	// return isNeiV;
	// }

	private boolean isNeighbourV(ArrayList<Pair<Boolean, ServiceAgent>> actualNState) {
		boolean isNeiV = false;
		// Search a simple correlation
		for (int j = 0; j < actualNState.size(); j++) {
			// if ((actualNState.get(j).getFirst() &&
			// neightboursState.get(j).getFirst())
			// || ((!actualNState.get(j).getFirst()) &&
			// (!neightboursState.get(j).getFirst()))) {
			if (actualNState.get(j).getSecond().getInstanceAgent().getType()
					.equals(neightboursState.get(j).getSecond().getInstanceAgent().getType())) {
				isNeiV = true;
				break;
			}
			// }
		}
		return isNeiV;
	}

	static <T> boolean hasSimpleCorrelation(ArrayList<T> list1, ArrayList<T> list2) {
		boolean isSimplyCorralated = false;
		T item;
		for (int i = 0; i < list1.size(); i++) {
			item = list1.get(i);
			if (list2.contains(item)) {
				// TODO need to be modify if we
				// change same instance or not
				// same instance to be possibly
				// either

				isSimplyCorralated = true;
				break;
			}
		}

		return isSimplyCorralated;
	}

	static boolean hasSimpleTypeCorrelation(ArrayList<String> list1, ArrayList<ServiceAgent> list2) {
		ArrayList<String> temp2 = new ArrayList<String>(list2.size());

		// GetName of type
		// list1 is already a ArrayList<String>
		for (int i = 0; i < list2.size(); i++) {
			temp2.add(list2.get(i).getClass().getName());
		}
		return hasSimpleCorrelation(list1, temp2);
	}

	// static boolean hasDoubleCorrelation(ArrayList<Pair<Boolean,
	// ArrayList<ServiceAgent>>> list1, ArrayList<Pair<Boolean,
	// ArrayList<ServiceAgent>>> list2){
	// boolean isSimplyCorralated = false;
	//// T item;
	//// for (int i = 0; i < list1.size(); i++){
	//// item = list1.get(i);
	//// if (list2.contains(item)) {
	//// // TODO need to be modify if we
	//// // change same instance or not
	//// //same instance to be possibly
	//// //either
	////
	//// isSimplyCorralated = true;
	//// break;
	//// }
	//// }
	////
	//// return isSimplyCorralated;
	// }

	public void useFeedback(Boolean serviceAgentReponse) {
		calculateConfidence(serviceAgentReponse);
	}

	// Cette methode permet de calculer la recompense de l'agent contexte
	private void calculateConfidence(Boolean serviceAgentReponse) {
		int res = (serviceAgentReponse) ? 1 : 0;
		this.confidence = (1 - LAMBDA) * this.confidence + LAMBDA * res;
	}

	/**
	 * 
	 */
	public void display() {
		System.out.println(
				"(************************debut affichage agent contexte*********************************************)");
		System.out.println("serviceAgentId = " + this.serviceAgent.getId());
		System.out.println("confidence = " + this.confidence);
		System.out.println("actionPerformed = " + this.actionPerformed);
		System.out.println("senderType = " + this.senderType);
		System.out.println("messageType = " + messageType);
		System.out.println("serviceAgentState = " + this.serviceAgentState.getFirst());
 		System.out.println(
				"(*/*/*/*/*/*/*/*/*/*/*/*/*fin affichage agent contexte*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*)");
	}

}
