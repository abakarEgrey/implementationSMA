package implementationSMA.agents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.irit.upnp.ContainerWComp;

import fr.irit.smac.libs.tooling.messaging.AgentMessaging;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.messaging.impl.Ref;
import implementationSMA.Pair;
import implementationSMA.messages.AbstractMessage;

public class InstanceAgent extends Agent {
	// Properties
	// id
	protected ArrayList<ServiceAgent> serviceAgents;
	private int countIdContextAgents;
	// T is the of the component
	protected String type;
	// reference of Instance agent
	private Ref<AbstractMessage> refInstanceAgent;
	private IMsgBox<AbstractMessage> messageBox;
	// this parameter contains the list messages to forward to Routage class
	private ArrayList<AbstractMessage> messagesListToForward;
	protected Routage routage;

	
	protected AgentsConnectionToUPnP agentsConnectionToUPnP;
	protected ContainerWComp container;

	// Constructor1
	public InstanceAgent(String id, Routage routage) {
		this.id = id;
		this.messageBox = (IMsgBox<AbstractMessage>) AgentMessaging.getMsgBox(id, AbstractMessage.class);
		this.refInstanceAgent = this.getMessageBox().getRef();
		this.messagesListToForward = new ArrayList<AbstractMessage>();
		this.routage = routage;
		this.serviceAgents = new ArrayList<ServiceAgent>();
		// A l'instanciation d'un agent instance, il s'ajoute automatiquement à
		// la liste des agents de la classe routage
		// a commenter pour le moment
		/* this.routage.addInstanceAgent(this); */
	}
	
	// Constructor2
	public InstanceAgent(String id, Routage routage, AgentsConnectionToUPnP agentsConnectionToUPnP, ContainerWComp container) {
		this.id = id;
		this.messageBox = (IMsgBox<AbstractMessage>) AgentMessaging.getMsgBox(id, AbstractMessage.class);
		this.refInstanceAgent = this.getMessageBox().getRef();
		this.messagesListToForward = new ArrayList<AbstractMessage>();
		this.routage = routage;
		this.serviceAgents = new ArrayList<ServiceAgent>();
		this.agentsConnectionToUPnP = agentsConnectionToUPnP;
		this.container = container;
		// A l'instanciation d'un agent instance, il s'ajoute automatiquement à
		// la liste des agents de la classe routage
		// a commenter pour le moment
		/* this.routage.addInstanceAgent(this); */
	}

	// Accessor

	
	public int getCountIdContextAgents() {
		return countIdContextAgents;
	}

	public AgentsConnectionToUPnP getAgentsConnectionToUPnP() {
		return agentsConnectionToUPnP;
	}

	public ContainerWComp getContainer() {
		return container;
	}

	public IMsgBox<AbstractMessage> getMessageBox() {
		return messageBox;
	}

	public Ref<AbstractMessage> getRefInstanceAgent() {
		return refInstanceAgent;
	}

	public String getType() {
		return type;
	}

	public void setCountIdContextAgents(int countIdContextAgents) {
		this.countIdContextAgents = countIdContextAgents;
	}

	// Life Cycle
	@Override
	protected void perceive() {
		// TODO Auto-generated method stub
		System.out.println("perceiveInstance: je suis executé");
		this.messagesListToForward = (ArrayList<AbstractMessage>) this.messageBox.getMsgs();

	}

	@Override
	protected void decide() {
		System.out.println("decideInstance: je suis executé");
		// TODO Auto-generated method stub
		// no decision

	}

	@Override
	protected void act() {
		// TODO Auto-generated method stub
		System.out.println("actInstance: je suis executé");
		for (AbstractMessage am : this.messagesListToForward) {
			this.messageBox.send(am, this.routage.getRefRoutage());
		}

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		this.removeAllServiceAgent();
		this.routage.remove(this);

	}

	public ArrayList<Pair<Boolean, ArrayList<ServiceAgent>>> getChildrenState() {
		ArrayList<Pair<Boolean, ArrayList<ServiceAgent>>> childrenConnexion = new ArrayList<Pair<Boolean, ArrayList<ServiceAgent>>>(
				serviceAgents.size());

		for (int i = 0; i < serviceAgents.size(); i++) {
			childrenConnexion.add(serviceAgents.get(i).getCurrentServiceState());
		}

		return childrenConnexion;
	}

	// TODO: to change...Or had a more efficient: we DON'T care about the bool,
	// mb

	public ArrayList<Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>>> getActualNeighboursState(
			ServiceAgent askingServiceAgent) {
		ArrayList<Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>>> actualNeighboursState = new ArrayList<Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>>>(
				(serviceAgents.size() - 1));

		for (int i = 0; i < this.serviceAgents.size(); i++) {
			ServiceAgent tmpServiceAgent = this.serviceAgents.get(i);
			if (!tmpServiceAgent.equals(askingServiceAgent)) {
				// ajout de l'agent service voisin et de son etat. S'il est
				// connecté, l'ensemble des agents avec qui il est connecté
				actualNeighboursState.add(new Pair<ServiceAgent, Pair<Boolean, ArrayList<ServiceAgent>>>(
						tmpServiceAgent, new Pair<Boolean, ArrayList<ServiceAgent>>(tmpServiceAgent.isConnected(),
								new ArrayList<ServiceAgent>(tmpServiceAgent.getCurrentServiceState().getSecond()))));
			}
		}

		return actualNeighboursState;
	}

	public ArrayList<ArrayList<Pair<Boolean, ServiceAgent>>> getActualNeighboursStateOld(
			ServiceAgent askingServiceAgent) {

		// List containing the state of the neighbours of serviceAgentAsking
		ArrayList<ArrayList<Pair<Boolean, ServiceAgent>>> actualNeighboursState = new ArrayList<ArrayList<Pair<Boolean, ServiceAgent>>>(
				(serviceAgents.size() - 1));

		// To avoid multiple allocation, contain the connexions between
		// neighbourServiceAgent and other ServiceAgents
		Pair<Boolean, ArrayList<ServiceAgent>> servACState;

		// Asking Service Agent State to avoid multiple queries

		Pair<Boolean, ArrayList<ServiceAgent>> askingServiceAgentState = askingServiceAgent.getCurrentServiceState();

		for (int i = 0; i < this.serviceAgents.size(); i++) {
			if (!this.serviceAgents.get(i).equals(askingServiceAgent)) {
				servACState = this.serviceAgents.get(i).getCurrentServiceState();
				ArrayList<Pair<Boolean, ServiceAgent>> thisNeighbourState = new ArrayList<Pair<Boolean, ServiceAgent>>();

				// Some changes before sending
				// childrenConnexion.add(serviceAgents.get(i).getCurrentServiceState());
				if (servACState.getFirst()) {
					if (servACState.getSecond().isEmpty()) {
						// TODO error
					} else {
						// Add every type with the bool "same instance"
						thisNeighbourState = new ArrayList<Pair<Boolean, ServiceAgent>>(servACState.getSecond().size());

						for (int j = 0; j < servACState.getSecond().size(); j++) {
							// Check if the askingServiceAgent is connected to
							// this serviceAgent
							if (askingServiceAgentState.getSecond().contains(servACState.getSecond().get(j))) {
								thisNeighbourState
										.add(new Pair<Boolean, ServiceAgent>(true, servACState.getSecond().get(j)));
							} else {
								thisNeighbourState
										.add(new Pair<Boolean, ServiceAgent>(false, servACState.getSecond().get(j)));
							}
						}

						// Once every connected serviceAgents connected to the
						// serviceAgent has been add to the sub list,
						// add the sub list to the list Array
						actualNeighboursState.add(thisNeighbourState);
					}
				} else {
					if (servACState.getSecond().isEmpty()) {
						// Nothing connected, add empty list
						thisNeighbourState.add(new Pair<Boolean, ServiceAgent>(false, this.serviceAgents.get(i)));
						actualNeighboursState.add(thisNeighbourState);
					} else {
						// TODO error
					}

				}
			}
		}

		return actualNeighboursState;
	}

	/**
	 * This method allows to get a list contains the states of service agents
	 * 
	 * @param serviceAgent
	 * @return
	 */
	public ArrayList<Pair<Boolean, ServiceAgent>> getNeightboursState(ServiceAgent serviceAgent) {
		ArrayList<Pair<Boolean, ServiceAgent>> neightboursState = null;
		if (!this.serviceAgents.isEmpty()) {
			neightboursState = new ArrayList<Pair<Boolean, ServiceAgent>>();
			for (ServiceAgent sa : this.serviceAgents) {
				if (!sa.equals(serviceAgent)) {
					// add the state of of service agent
					neightboursState.add(new Pair<Boolean, ServiceAgent>(sa.isConnected(), sa));
				}
			}
			return neightboursState;
		}
		return neightboursState;
	}

	/**
	 * this method allows to delete the service agent
	 * 
	 * @param serviceAgent
	 */
	public boolean remove(ServiceAgent serviceAgent) {
		// TODO Auto-generated method stub
		for (ServiceAgent sa : this.serviceAgents) {
			if (sa.getId().equals(serviceAgent.getId())) {
				this.serviceAgents.remove(sa);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 */
	private void removeAllServiceAgent() {
		this.serviceAgents.clear();
	}

}
