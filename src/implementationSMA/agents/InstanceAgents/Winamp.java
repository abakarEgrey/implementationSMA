package implementationSMA.agents.InstanceAgents;

import java.util.ArrayList;
import java.util.HashSet;

import com.irit.upnp.ContainerWComp;

import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.Routage;
import implementationSMA.agents.ServiceAgents.ServiceAgent;
import implementationSMA.enumeration.InterfaceType;

/**
 * Ce composant a 4 interfaces: nous avons l'interface:
 * 
 * @winPredButton: le bouton precedent
 * @winSuivButton: le bouton suivant
 * @winIncreaseVolumeButton : le bouton permettant d'augmenter le volume
 * @winDecreseVolumeButton: le bouton permettant de diminuer le volume
 * @author amahamat
 *
 */
public class Winamp extends InstanceAgent {

	private String idServiceAgent;
	private HashSet<ServiceAgent> hashSet = new HashSet<ServiceAgent>();

	private AgentsConnectionToUPnP agentsConnectionToUPnP;

	public Winamp(String id, Routage routage, HashSet<ServiceAgent> hashSet,
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

		ServiceAgent winPredButton = new ServiceAgent("@winPredButton", this, 1, this.hashSet, "Click", "PreviousSong",
				InterfaceType.REQUIRED);
		ServiceAgent winSuivButton = new ServiceAgent("@winSuivButton", this, 1, this.hashSet, "Click", "NextSong",
				InterfaceType.REQUIRED);

		ServiceAgent winIncreaseVolumeButton = new ServiceAgent("@winIncVolButton", this, 1, this.hashSet, "Click",
				"VolumeUp", InterfaceType.REQUIRED);
		ServiceAgent winDecreseVolumeButton = new ServiceAgent("@winDecVolButton", this, 1, this.hashSet, "Click",
				"VolumeDown", InterfaceType.REQUIRED);
		// a creer prochainement les boutons volumes

		this.serviceAgents.add(winPredButton);
		this.serviceAgents.add(winSuivButton);
		/*this.serviceAgents.add(winIncreaseVolumeButton);
		this.serviceAgents.add(winDecreseVolumeButton);*/

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
