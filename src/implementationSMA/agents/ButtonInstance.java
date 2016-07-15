package implementationSMA.agents;

import java.util.ArrayList;
import java.util.HashSet;

import com.irit.upnp.ContainerWComp;

import fr.irit.smac.libs.tooling.scheduling.IAgentStrategy;
import implementationSMA.enumeration.InterfaceType;

public class ButtonInstance extends InstanceAgent {

	/**
	 * l'id est le nom de l'instance
	 */
	private String idServiceAgent;
	// pour le test
	private HashSet<ServiceAgent> hashSet = new HashSet<ServiceAgent>();

	/**
	 * 
	 * @param id
	 * @param routage
	 */
	public ButtonInstance(String id, Routage routage, String idServiceAgent, HashSet<ServiceAgent> hashSet,
			AgentsConnectionToUPnP agentsConnectionToUPnP, ContainerWComp container) {
		super(id, routage, agentsConnectionToUPnP, container);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.routage = routage;
		this.type = "ButtonInstance";
		this.idServiceAgent = idServiceAgent;
		this.hashSet = hashSet;
		this.createButtonsAgents();
		// this.agentsConnectionToUPnP = agentsConnectionToUPnP;
		// this.container = container;
		// this.type = "Bouton";
	}

	public ContainerWComp getContainer() {
		return container;
	}

	public AgentsConnectionToUPnP getAgentsConnectionToUPnP() {
		return agentsConnectionToUPnP;
	}

	/**
	 * creation of 2 buttons: next button and previous button
	 */
	private void createButtonsAgents() {
		// TODO Auto-generated method stub
		// un peu en dur. A changer pour rendre plus generique
		ServiceAgent button;
		if (this.idServiceAgent.equals("prevButton")) {
			button = new ServiceAgent("@" + this.idServiceAgent, this, 1, this.hashSet, "Click", "Previous",
					InterfaceType.PROVIDED);
		} else {
			button = new ServiceAgent("@" + this.idServiceAgent, this, 1, this.hashSet, "Click", "Next",
					InterfaceType.PROVIDED);
		}

		this.serviceAgents.add(button);
		// besoin pour le test: chaque bouton envoie une annonce aux interfaces
		// de ImpressJ
		/*
		 * if (this.serviceAgents.get(0).getMessageBox().getMsgs().isEmpty()) {
		 * ServiceAgent localSA = this.serviceAgents.get(0);
		 * 
		 * for (ServiceAgent aSImpressJ : this.hashSet) {
		 * 
		 * ServiceAgentMessage sAM = new ServiceAgentMessage(1, this.type,
		 * MessageType.SAMESSAGE, localSA.getCurrentServiceState(), 0, 0.0,
		 * localSA, Action.ANNONCER); localSA.getMessageBox().send(sAM,
		 * aSImpressJ.getId());
		 * 
		 * } // creation d'un agent contexte pour le test. Integrer tout ca //
		 * dans // la classe service agent (decide) ContextAgent contextAgent =
		 * new ContextAgent(this.type, null, null, new Pair<Boolean,
		 * ArrayList<ServiceAgent>>(false, new ArrayList<ServiceAgent>()),
		 * Action.ANNONCER, localSA, 0.5, this.getId() + "-" + 1);
		 * ArrayList<ContextAgent> contextAgents = localSA.getContextAgents();
		 * contextAgents.add(contextAgent);
		 * localSA.setContextAgents(contextAgents);
		 * 
		 * }
		 */

	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<ServiceAgent> getServiceAgentList() {
		return this.serviceAgents;
	}

}
