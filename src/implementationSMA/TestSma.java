package implementationSMA;

import java.util.HashSet;

import com.irit.upnp.ContainerWComp;
import com.irit.upnp.ErrorContainer;
import com.irit.upnp.NoDevice;
import com.irit.upnp.NoService;
import com.irit.upnp.NotLaunched;
import com.irit.upnp.SpyNotRunning;

import fr.irit.smac.libs.tooling.scheduling.IAgentStrategy;
import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.ButtonInstance;
import implementationSMA.agents.ImpressInstance;
import implementationSMA.agents.ServiceAgent;

public class TestSma {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContainerWComp c = new ContainerWComp("Container1_Structural_0");

		AgentsConnectionToUPnP agentsConnectionToUPnP = new AgentsConnectionToUPnP();

		HashSet<ServiceAgent> buttonAgents = new HashSet<ServiceAgent>();

		ImpressInstance impress = new ImpressInstance("ImpressJ", null, buttonAgents, agentsConnectionToUPnP, c);

		HashSet<ServiceAgent> impressAgents = new HashSet<ServiceAgent>();
		impressAgents.addAll(impress.getServiceAgentList());

		ButtonInstance boutonPred = new ButtonInstance("@Button", null, "prevButton", impressAgents,
				agentsConnectionToUPnP, c);
		buttonAgents.addAll(boutonPred.getServiceAgentList());

		ButtonInstance boutonSuiv = new ButtonInstance("@Button1", null, "nextButton", impressAgents,
				agentsConnectionToUPnP, c);
		buttonAgents.addAll(boutonSuiv.getServiceAgentList());

		impress.setReceiverSAList(buttonAgents);

		/*
		 * ButtonInstance boutonSuiv = new ButtonInstance("@Button1", null,
		 * "nextButton", listServiceAgents);
		 */

		// Ajout dans hashset

		HashSet<IAgentStrategy> hashSet = new HashSet<IAgentStrategy>();
		hashSet.addAll(boutonPred.getServiceAgentList());

		try {
			pause(2000);
			// creation des 2 boutons de manipuler l'ImpressJ
			String bouton1 = c.createBeanAtPos("Bouton 1", "System.Windows.Forms.Button", 600, 400);
			pause(5000);
			String bouton2 = c.createBeanAtPos("Bouton 2", "System.Windows.Forms.Button", 200, 400);
			pause(5000);
			// creation de l'ImpressJ
			String impressJS = c.createBeanAtPos("ImpressJS", "WComp.UPnPDevice.ImpressJS", 400, 100);

			// ajout dans le map

			agentsConnectionToUPnP.addServiceAgent(impress.getServiceAgentList().get(0), "ImpressJS");
			agentsConnectionToUPnP.addServiceAgent(impress.getServiceAgentList().get(1), "ImpressJS");

			agentsConnectionToUPnP.addServiceAgent(boutonPred.getServiceAgentList().get(0), "Bouton 1");
			agentsConnectionToUPnP.addServiceAgent(boutonSuiv.getServiceAgentList().get(0), "Bouton 2");

			// creation des liens entre ImpressJ et les 2 boutons
			pause(5000);

			// String linkBut1 = c.createLink("Bouton 1", "Click", "ImpressJS",
			// "Next", "");
			System.out.println("la taille de agentsConnectionToUPnP pour boutonPred est :"
					+ boutonPred.getAgentsConnectionToUPnP().getServiceAgentAndWCompBean().size());
			agentsConnectionToUPnP.doPhysicConnection(boutonPred.getServiceAgentList().get(0),
					impress.getServiceAgentList().get(0), c);
			pause(5000);
			// String linkBut2 = c.createLink("Bouton 2", "Click", "ImpressJS",
			// "Previous", "");
			agentsConnectionToUPnP.doPhysicConnection(boutonSuiv.getServiceAgentList().get(0),
					impress.getServiceAgentList().get(1), c);

			c.stopSpy();

		} catch (NoDevice e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoService e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLaunched e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorContainer e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SpyNotRunning e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}
