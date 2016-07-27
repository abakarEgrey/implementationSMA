package implementationSMA.agents.InstanceAgents;

import java.util.ArrayList;
import java.util.HashSet;

import com.irit.upnp.ContainerWComp;

import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.Routage;
import implementationSMA.agents.ServiceAgents.ServiceAgent;
import implementationSMA.enumeration.InterfaceType;

/**
 * Ce composant a 2 interfaces: nous avons l'interface:
 * 
 * @ArdinoPredButton: le bouton precedent
 * @ArdinoSuivButton: le bouton suivant
 * @author amahamat
 *
 */
public class ArduinoInstance extends InstanceAgent {
	
	private String idServiceAgent;
	private HashSet<ServiceAgent> hashSet = new HashSet<ServiceAgent>();

	private AgentsConnectionToUPnP agentsConnectionToUPnP;
	
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
		this.type = "ButtonInstance";
		this.idServiceAgent = idServiceAgent;
		this.hashSet = hashSet;
		this.createWinampButtonsAgents();
		this.agentsConnectionToUPnP = agentsConnectionToUPnP;

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

		ServiceAgent ArdinoPredButton = new ServiceAgent("@ArdinoPredButton", this, 1, this.hashSet, "Button1_Event", "PreviousSong",
				InterfaceType.PROVIDED);
		ServiceAgent ArdinoSuivButton = new ServiceAgent("@ArdinoSuivButton", this, 1, this.hashSet, "Button2_Event", "NextSong",
				InterfaceType.PROVIDED);
		//a creer prochainement les boutons volumes
		
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
	

}
