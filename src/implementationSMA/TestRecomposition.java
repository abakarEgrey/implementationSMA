package implementationSMA;

import java.util.HashSet;

import com.irit.upnp.ContainerWComp;
import com.irit.upnp.ErrorContainer;
import com.irit.upnp.NoDevice;
import com.irit.upnp.NoService;
import com.irit.upnp.NotLaunched;
import com.irit.upnp.SpyNotRunning;

import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.InstanceAgents.ButtonInstance;
import implementationSMA.agents.InstanceAgents.ImpressInstance;
import implementationSMA.agents.ServiceAgents.ServiceAgent;

public class TestRecomposition {

	public static void main(String[] args) {
		
		ContainerWComp c = new ContainerWComp("Container1_Structural_0");

		AgentsConnectionToUPnP agentsConnectionToUPnP = new AgentsConnectionToUPnP();

		HashSet<ServiceAgent> buttonAgents = new HashSet<ServiceAgent>();

		ImpressInstance impress = new ImpressInstance("ImpressJ", null, buttonAgents, agentsConnectionToUPnP, c);

		HashSet<ServiceAgent> impressAgents = new HashSet<ServiceAgent>();
		impressAgents.addAll(impress.getServiceAgentList());

		ButtonInstance boutonPred = new ButtonInstance("@Button", null, "prevButton", impressAgents,
				agentsConnectionToUPnP, c);
		buttonAgents.addAll(boutonPred.getServiceAgentList());
		
		ButtonInstance boutonPred1 = new ButtonInstance("@ButtonP", null, "prevButton", impressAgents,
				agentsConnectionToUPnP, c);
		buttonAgents.addAll(boutonPred1.getServiceAgentList());

		ButtonInstance boutonSuiv = new ButtonInstance("@ButtonS", null, "nextButton", impressAgents,
				agentsConnectionToUPnP, c);
		buttonAgents.addAll(boutonSuiv.getServiceAgentList());

		impress.setReceiverSAList(buttonAgents);

		try {
			//pause(2000);
			// creation des 2 boutons de manipuler l'ImpressJ
			String bouton1 = c.createBeanAtPos("Bouton 1", "System.Windows.Forms.Button", 600, 400);
			//pause(5000);
			String bouton2 = c.createBeanAtPos("Bouton 2", "System.Windows.Forms.Button", 200, 400);
			
			String bouton3 = c.createBeanAtPos("Bouton 3", "System.Windows.Forms.Button", 104, 216);
			//pause(5000);
			// creation de l'ImpressJ
			String impressJS = c.createBeanAtPos("ImpressJS", "WComp.UPnPDevice.ImpressJS", 400, 100);
			
			agentsConnectionToUPnP.addServiceAgent(impress.getServiceAgentList().get(0), "ImpressJS");
			agentsConnectionToUPnP.addServiceAgent(impress.getServiceAgentList().get(1), "ImpressJS");

			agentsConnectionToUPnP.addServiceAgent(boutonPred.getServiceAgentList().get(0), "Bouton 1");
			agentsConnectionToUPnP.addServiceAgent(boutonSuiv.getServiceAgentList().get(0), "Bouton 2");
			agentsConnectionToUPnP.addServiceAgent(boutonPred1.getServiceAgentList().get(0), "Bouton 3");
			
			
			boutonPred.getServiceAgentList().get(0).nextStep();
			
			for (int i = 0; i < 7; i++){
				//perception
				impress.getServiceAgentList().get(0).perceive();
				impress.getServiceAgentList().get(1).perceive();
				boutonPred1.getServiceAgentList().get(0).perceive();
				boutonSuiv.getServiceAgentList().get(0).perceive();
				boutonPred.getServiceAgentList().get(0).perceive();
				//decision action
				impress.getServiceAgentList().get(0).decideAndAct();
				impress.getServiceAgentList().get(1).decideAndAct();
				boutonPred1.getServiceAgentList().get(0).decideAndAct();
				boutonSuiv.getServiceAgentList().get(0).decideAndAct();
				boutonPred.getServiceAgentList().get(0).decideAndAct();
			}
			
			//deconnection de bouton 3 cad boutonPred1
			boutonPred1.disappear();
			c.removeBean("Bouton 3");
			
			buttonAgents.removeAll(boutonPred1.getServiceAgentList());
			impress.setReceiverSAList(buttonAgents);
			//regarder le comportement de bouton 2
			boutonSuiv.getServiceAgentList().get(0).perceive();
			boutonSuiv.getServiceAgentList().get(0).decideAndAct();
			
			for (int i = 0; i < 10; i++){
				//perception
				impress.getServiceAgentList().get(0).perceive();
				impress.getServiceAgentList().get(1).perceive();
				boutonPred1.getServiceAgentList().get(0).perceive();
				boutonSuiv.getServiceAgentList().get(0).perceive();
				boutonPred.getServiceAgentList().get(0).perceive();
				//decision action
				impress.getServiceAgentList().get(0).decideAndAct();
				impress.getServiceAgentList().get(1).decideAndAct();
				boutonPred1.getServiceAgentList().get(0).decideAndAct();
				boutonSuiv.getServiceAgentList().get(0).decideAndAct();
				boutonPred.getServiceAgentList().get(0).decideAndAct();
			}
			
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
		} 

	}
}
