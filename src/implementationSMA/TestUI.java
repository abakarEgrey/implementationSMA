package implementationSMA;

import java.util.HashSet;

import com.irit.upnp.ContainerWComp;
import com.irit.upnp.ErrorContainer;
import com.irit.upnp.NoDevice;
import com.irit.upnp.NoService;
import com.irit.upnp.NotLaunched;
import com.irit.upnp.SpyNotRunning;

import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;
import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.InstanceAgents.ArdinoInstance;
import implementationSMA.agents.InstanceAgents.ButtonInstance;
import implementationSMA.agents.InstanceAgents.ImpressInstance;
import implementationSMA.agents.ServiceAgents.ServiceAgent;

public class TestUI {

	static ContainerWComp c = new ContainerWComp("Container1_Structural_0");

	AgentsConnectionToUPnP agentsConnectionToUPnP = new AgentsConnectionToUPnP();
	HashSet<ServiceAgent> impressAgents = new HashSet<ServiceAgent>();
	HashSet<ServiceAgent> buttonAgents = new HashSet<ServiceAgent>();
	HashSet<ITwoStepsAgent> hashSet = new HashSet<ITwoStepsAgent>();
	HashSet<ServiceAgent> ardinoDestinationAgents = new HashSet<>();
	TwoStepsForOppoCompo twoStepsForOppoCompo = new TwoStepsForOppoCompo(hashSet);

	Pile pile = new Pile();
	// debut singleton
	private static TestUI testUIInstance;

	public static TestUI getInstance() {
		if (testUIInstance != null) {
			return testUIInstance;
		}

		testUIInstance = new TestUI();
		return testUIInstance;
	}
	// fin singleton

	static int buttonCounter = 0, impressCounter = 0, arduinoCounter = 0;

	public synchronized void addButton() {
		buttonCounter++;
		ButtonInstance boutonInstance = new ButtonInstance("@Button" + buttonCounter, null, "Button " + buttonCounter,
				impressAgents, agentsConnectionToUPnP, c);
		// ajout de la pile
		// boutonPred.getServiceAgentList().get(0).setPile(pile);
		buttonAgents.addAll(boutonInstance.getServiceAgentList());
		boutonInstance.setReceiverSAList(impressAgents);
		

		try {
			//pause(2000);
			// creation des 2 boutons de manipuler l'ImpressJ
			String bouton1 = c.createBeanAtPos("Bouton " + buttonCounter, "System.Windows.Forms.Button", 600 + (buttonCounter - 1)*200, 400);
			agentsConnectionToUPnP.addServiceAgent(boutonInstance.getServiceAgentList().get(0), "Bouton " + buttonCounter);
			hashSet.addAll(boutonInstance.getServiceAgentList());
			twoStepsForOppoCompo.addAgents(hashSet);

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

	public TwoStepsForOppoCompo getTwoStepsForOppoCompo() {
		return twoStepsForOppoCompo;
	}

	public static void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SmaInterface smaInterface = new SmaInterface("bouton");
		smaInterface.run();

		boolean arret = true;
		while (arret) {
			TestUI.getInstance().getTwoStepsForOppoCompo().doStep();
		}

	}

	public synchronized void addImpressJ() {
		impressCounter++;
		ImpressInstance impress = new ImpressInstance("ImpressJ" + impressCounter, null, buttonAgents,
				agentsConnectionToUPnP, c);
		impressAgents.addAll(impress.getServiceAgentList());
		impress.setReceiverSAList(buttonAgents);

		try {
			String impressJS = c.createBeanAtPos("ImpressJS" + impressCounter, "WComp.UPnPDevice.ImpressJS", 400, 100);

			// ajout dans le map

			agentsConnectionToUPnP.addServiceAgent(impress.getServiceAgentList().get(0), "ImpressJS" + impressCounter);
			agentsConnectionToUPnP.addServiceAgent(impress.getServiceAgentList().get(1), "ImpressJS" + impressCounter);
			hashSet.addAll(impress.getServiceAgentList());

			twoStepsForOppoCompo.addAgents(hashSet);

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

	public synchronized void addArduino() {
		// TODO Auto-generated method stub
		arduinoCounter++;
		
		ardinoDestinationAgents.addAll(impressAgents);

		ArdinoInstance ardinoInstance = new ArdinoInstance("ArdinoComponent" + arduinoCounter, null,
				ardinoDestinationAgents, agentsConnectionToUPnP, c);
		//ajout des agents d'arduino dans la liste des agents des boutons
		//impressAgents.addAll(ardinoInstance.getServiceAgentList());
		//ardinoInstance.setReceiverSAList(impressAgents);
		ardinoInstance.setReceiverSAList(impressAgents);


		try {
			String ardino = c.createBeanAtPos("Ardino Component" + arduinoCounter, "WComp.UPnPDevice.Arduino_Button", 200, 100);

			agentsConnectionToUPnP.addServiceAgent(ardinoInstance.getServiceAgentList().get(0),
					"Ardino Component" + arduinoCounter);
			agentsConnectionToUPnP.addServiceAgent(ardinoInstance.getServiceAgentList().get(1),
					"Ardino Component" + arduinoCounter);

			hashSet.addAll(ardinoInstance.getServiceAgentList());
			twoStepsForOppoCompo.addAgents(hashSet);

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
