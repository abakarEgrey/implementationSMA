package implementationSMA.agents.InstanceAgents;

import java.util.ArrayList;
import java.util.HashSet;

import com.irit.upnp.ContainerWComp;

import exceptions.RemovedLink;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import implementationSMA.OnRemovedListener;
import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.Routage;
import implementationSMA.agents.ServiceAgents.ServiceAgent;
import implementationSMA.enumeration.Action;
import implementationSMA.enumeration.InterfaceType;
import implementationSMA.enumeration.MessageType;
import implementationSMA.messages.AbstractMessage;
import implementationSMA.messages.ServiceAgentMessage;

/**
 * Ce composant a 2 interfaces: nous avons l'interface:
 * 
 * @ArdinoPredButton: le bouton precedent
 * @ArdinoSuivButton: le bouton suivant
 * @author amahamat
 *
 */
public class ArduinoInstance extends InstanceAgent {

	private HashSet<ServiceAgent> hashSet = new HashSet<ServiceAgent>();

	private AgentsConnectionToUPnP agentsConnectionToUPnP;
	private ArrayList<OnRemovedListener> onRemovedListeners;

	/**
	 * 
	 * @param id
	 * @param routage
	 * @param hashSet
	 * @param agentsConnectionToUPnP
	 * @param container
	 */
	public ArduinoInstance(String id, Routage routage, HashSet<ServiceAgent> hashSet,
			AgentsConnectionToUPnP agentsConnectionToUPnP, ContainerWComp container) {
		super(id, routage, agentsConnectionToUPnP, container);
		this.id = id;
		this.routage = routage;
		this.type = "ArduinoInstance";
		this.hashSet = hashSet;
		this.createWinampButtonsAgents();
		this.agentsConnectionToUPnP = agentsConnectionToUPnP;
		this.onRemovedListeners = new ArrayList<>();

	}

	public ContainerWComp getContainer() {
		return container;
	}

	public AgentsConnectionToUPnP getAgentsConnectionToUPnP() {
		return agentsConnectionToUPnP;
	}

	/** 
	 * 
	 */
	private void createWinampButtonsAgents() {
		// TODO Auto-generated method stub

		ServiceAgent ArdinoPredButton = new ServiceAgent("@ArdinoPred" + this.id, this, 1, this.hashSet,
				"Button1_Event", "PreviousSong", InterfaceType.PROVIDED);

		ServiceAgent ArdinoSuivButton = new ServiceAgent("@ArdinoSuiv" + this.id, this, 1, this.hashSet,
				"Button2_Event", "NextSong", InterfaceType.PROVIDED);

		// a creer prochainement les boutons volumes

		this.serviceAgents.add(ArdinoPredButton);
		this.serviceAgents.add(ArdinoSuivButton);

	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<ServiceAgent> getServiceAgentList() {
		return this.serviceAgents;
	}

	public boolean disappear() {

		/**
		 * Le composant envoie le message sedeconnecter à tous les agents avec
		 * qui il est connecté
		 */
		for (ServiceAgent sA : this.serviceAgents) {
			String list = "[";
			for (ServiceAgent serviceAgent : sA.getConnectedAgents()) {
				list += serviceAgent.getId() + " ";
			}
			list += "]";
			System.out.println("sA.getConnectedAgents() = " + list + " " + sA.getId());
			if (!sA.getConnectedAgents().isEmpty()) {
				seDeconnecter(sA);
				// vider la liste des agents connectés
				sA.getConnectedAgents().clear();
				// changer son etat
				sA.setState(false);
			}

		}

		return true;

	}

	/**
	 * 
	 * @param sATriggingDeconnection
	 */
	private void seDeconnecter(ServiceAgent sATriggingDeconnection) {
		// TODO Auto-generated method stub

		IMsgBox<AbstractMessage> messageBox = sATriggingDeconnection.getMessageBox();
		ArrayList<ServiceAgent> sAConnected = sATriggingDeconnection.getConnectedAgents();
		for (ServiceAgent serviceAgent : sAConnected) {

			ServiceAgentMessage sAM = new ServiceAgentMessage(sATriggingDeconnection.getCardinality(),
					sATriggingDeconnection.getInstanceAgent().getType().toString(), MessageType.SAMESSAGE,
					sATriggingDeconnection.getCurrentServiceState(),
					sATriggingDeconnection.getNbOfConnectionAndAverageTime().get(serviceAgent.getId()).getFirst(),
					sATriggingDeconnection.getNbOfConnectionAndAverageTime().get(serviceAgent.getId()).getSecond(),
					sATriggingDeconnection, Action.SEDECONNECTER);

			messageBox.send(sAM, serviceAgent.getRefBox());
			try {
				this.agentsConnectionToUPnP.removePhysicConnection(sATriggingDeconnection, serviceAgent, container);
			} catch (RemovedLink e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sATriggingDeconnection.decrementNbLink();
		}
		// sAConnected.clear();

	}
	
	/**
	 * 
	 * @param serviceAgentsHashSet
	 */
	public void setReceiverSAList(HashSet<ServiceAgent> serviceAgentsHashSet) {
		this.hashSet = serviceAgentsHashSet;
		// mettre à jour des agents devant recevoir l'annonce
		for (int i = 0; i < this.serviceAgents.size(); i++) {
			this.serviceAgents.get(i).setsAListReceivingMessages(hashSet);
		}

	}

	public void addOnRemovedListener(OnRemovedListener o) {
		this.onRemovedListeners.add(o);
	}

	public void removeOnRemovedListener(OnRemovedListener o) {
		this.onRemovedListeners.remove(o);
	}

	public void destroy() {
		this.disappear();
		for (OnRemovedListener listener : onRemovedListeners)
			listener.onRemoved(this);
	}
	
	public String getInstanceName() {
		return "ArduinoInstance";
	}

}
