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

public class ImpressInstance extends InstanceAgent {
	/**
	 * Cr�ation d'une instance du composant Impress qui requiert deux interfaces
	 * (agents services) qui sont des boutons et fournit une interface
	 * (affichage du num�ro de la page actuelle)
	 */
	// pour le test
	private HashSet<ServiceAgent> hashSet = new HashSet<ServiceAgent>();

	private ArrayList<OnRemovedListener> onRemovedListeners;
	private int nbAgents = 0;
	// private ArrayList<ServiceAgent> serviceAgentList = new
	// ArrayList<ServiceAgent>();
	/**
	 * 
	 * @param id
	 * @param routage
	 */
	public ImpressInstance(String id, Routage routage, HashSet<ServiceAgent> hashSet,
			AgentsConnectionToUPnP agentsConnectionToUPnP, ContainerWComp container) {
		super(id, routage, agentsConnectionToUPnP, container);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.routage = routage;
		this.type = "ImpressJ";
		this.createImpressAgents();
		this.hashSet = hashSet;
		// this.agentsConnectionToUPnP = agentsConnectionToUPnP;
		// this.container = container;
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
	private void createImpressAgents() {
		// TODO Auto-generated method stub
		// on peut supprimer les variables locaux. Ils sont la juste pour la
		// lisisbilit� du code
		nbAgents++;
		ServiceAgent prevSlideRequired = new ServiceAgent("@"+this.id + nbAgents, this, 1, this.hashSet, "Click",
				"Previous", InterfaceType.REQUIRED);
		nbAgents++;
		ServiceAgent nextSlideRequired = new ServiceAgent("@"+this.id + nbAgents, this, 1, this.hashSet, "Click", "Next",
				InterfaceType.REQUIRED);

		this.serviceAgents.add(prevSlideRequired);
		this.serviceAgents.add(nextSlideRequired);

	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<ServiceAgent> getServiceAgentList() {
		return this.serviceAgents;
	}

	public void setReceiverSAList(HashSet<ServiceAgent> serviceAgentsHashSet) {
		this.hashSet = serviceAgentsHashSet;
		// mettre � jour des agents devant recevoir l'annonce
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
		return "ImpressInstance";
	}

	public boolean disappear() {

		/**
		 * Le composant envoie le message sedeconnecter � tous les agents avec
		 * qui il est connect�
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
				// vider la liste des agents connect�s
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
}
