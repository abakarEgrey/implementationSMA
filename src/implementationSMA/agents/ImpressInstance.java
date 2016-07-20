package implementationSMA.agents;

import java.util.ArrayList;
import java.util.HashSet;

import com.irit.upnp.ContainerWComp;

import implementationSMA.enumeration.InterfaceType;

public class ImpressInstance extends InstanceAgent {
	/**
	 * Création d'une instance du composant Impress qui requiert deux interfaces
	 * (agents services) qui sont des boutons et fournit une interface
	 * (affichage du numéro de la page actuelle)
	 */
	// pour le test
	private HashSet<ServiceAgent> hashSet = new HashSet<ServiceAgent>();

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
		// lisisbilité du code
		ServiceAgent prevSlideRequired = new ServiceAgent("@prevSlideRequired", this, 1, this.hashSet, "Click",
				"Previous", InterfaceType.REQUIRED);
		ServiceAgent nextSlideRequired = new ServiceAgent("@nextSlideRequired", this, 1, this.hashSet, "Click", "Next",
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
		// mettre à jour des agents devant recevoir l'annonce
		for (int i = 0; i < this.serviceAgents.size(); i++) {
			this.serviceAgents.get(i).setsAListReceivingMessages(hashSet);
		}

	}

}
