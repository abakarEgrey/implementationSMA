package implementationSMA;

import java.util.HashMap;
import java.util.HashSet;

import com.irit.upnp.ContainerWComp;
import com.irit.upnp.ErrorContainer;
import com.irit.upnp.NoDevice;
import com.irit.upnp.NoService;
import com.irit.upnp.NotLaunched;
import com.irit.upnp.SpyNotRunning;

import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;
import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.InstanceAgents.ArduinoInstance;
import implementationSMA.agents.InstanceAgents.ButtonInstance;
import implementationSMA.agents.InstanceAgents.ImpressInstance;
import implementationSMA.agents.InstanceAgents.InstanceAgent;
import implementationSMA.agents.InstanceAgents.Winamp;
import implementationSMA.agents.ServiceAgents.ServiceAgent;

public class TestUI {

	static ContainerWComp c = new ContainerWComp("Container1_Structural_0");

	private AgentsConnectionToUPnP agentsConnectionToUPnP = new AgentsConnectionToUPnP();

	private HashSet<ServiceAgent> impressAgents = new HashSet<ServiceAgent>();
	private HashSet<ServiceAgent> impressDestinationAgents = new HashSet<ServiceAgent>();

	private HashSet<ServiceAgent> buttonAgents = new HashSet<ServiceAgent>();
	private HashSet<ServiceAgent> buttonDestinationAgents = new HashSet<ServiceAgent>();

	private HashSet<ServiceAgent> ardinoAgents = new HashSet<>();
	private HashSet<ServiceAgent> ardinoDestinationAgents = new HashSet<>();

	private HashSet<ServiceAgent> winampAgents = new HashSet<>();
	private HashSet<ServiceAgent> winampDestinationAgents = new HashSet<>();

	private HashSet<ServiceAgent> joystickAgents = new HashSet<>();

	private HashSet<ITwoStepsAgent> hashSet = new HashSet<ITwoStepsAgent>();
	private TwoStepsForOppoCompo twoStepsForOppoCompo = new TwoStepsForOppoCompo(hashSet);
	private HashMap<String, InstanceAgent> hashMapUiInstance = new HashMap<>();

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

	static int buttonCounter = 0, buttonInstanceCounter = 0, impressCounter = 0, impressInstanceCounter = 0,
			arduinoCounter = 0, arduinoInstanceCounter = 0, winampCounter = 0, winampInstanceCounter = 0;
	
	private int[] getCircle(int x0, int y0, int rayon, double angle)
	{
		int xP = (int)(x0 + rayon * Math.cos(angle));
		int yP = (int)(y0 + rayon * Math.sin(angle));
		return new int[]{ xP, yP};
	}

	public synchronized void addButton() {
		buttonCounter++;
		buttonInstanceCounter++;
		ButtonInstance boutonComponent = new ButtonInstance("@Button" + buttonCounter, null, "Button " + buttonCounter,
				buttonDestinationAgents, agentsConnectionToUPnP, c);
		// ajout de la pile
		// boutonPred.getServiceAgentList().get(0).setPile(pile);
		buttonAgents.addAll(boutonComponent.getServiceAgentList());
		boutonComponent.setReceiverSAList(buttonDestinationAgents);

		
		impressDestinationAgents.addAll(buttonAgents);
		winampDestinationAgents.addAll(buttonAgents);

		try {
			// pause(2000);
			// creation des 2 boutons de manipuler l'ImpressJ
			int[] position = getCircle(350, 300, 250, (buttonCounter - 1) * 60.0);
			String bouton1 = c.createBeanAtPos("Bouton " + buttonCounter,
					"System.Windows.Forms.Button",
					position[0], position[1]);
			
			agentsConnectionToUPnP.addServiceAgent(boutonComponent.getServiceAgentList().get(0),
					"Bouton " + buttonCounter);
			
			hashSet.addAll(boutonComponent.getServiceAgentList());
			twoStepsForOppoCompo.addAgents(hashSet);
			
			boutonComponent.addOnRemovedListener(new OnRemovedListener() {
				
				@Override
				public void onRemoved(Object object) {
					// TODO Auto-generated method stub
					impressDestinationAgents.remove(object);
					winampDestinationAgents.remove(object);
					buttonAgents.remove(object);
					hashSet.remove(object);
				}
			});

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

	public synchronized void addImpressJ() {

		// impressDestinationAgents.addAll(buttonAgents);
		// impressDestinationAgents.addAll(ardinoAgents);
		impressCounter++;
		ImpressInstance impressComponent = new ImpressInstance("ImpressJ" + impressCounter, null,
				impressDestinationAgents, agentsConnectionToUPnP, c);
		impressAgents.addAll(impressComponent.getServiceAgentList());
		impressComponent.setReceiverSAList(impressDestinationAgents);

		ardinoDestinationAgents.addAll(impressAgents);
		buttonDestinationAgents.addAll(impressAgents);

		try {
			String impressJS = c.createBeanAtPos("ImpressJS" + impressCounter, "WComp.UPnPDevice.ImpressJS", 350, 300);

			// ajout dans le map

			agentsConnectionToUPnP.addServiceAgent(impressComponent.getServiceAgentList().get(0),
					"ImpressJS" + impressCounter);
			agentsConnectionToUPnP.addServiceAgent(impressComponent.getServiceAgentList().get(1),
					"ImpressJS" + impressCounter);
			hashSet.addAll(impressComponent.getServiceAgentList());

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

	public synchronized void addWinamp() {
		winampCounter++;

		Winamp winampComponent = new Winamp("WinampComponent" + winampCounter, null, buttonAgents,
				agentsConnectionToUPnP, c);

		winampAgents.addAll(winampComponent.getServiceAgentList());
		winampComponent.setReceiverSAList(winampDestinationAgents);

		ardinoDestinationAgents.addAll(winampAgents);
		buttonDestinationAgents.addAll(winampAgents);

		try {
			String winamp = c.createBeanAtPos("Winamp" + winampCounter, "WComp.UPnPDevice.WinampRemote", 350, 400);

			agentsConnectionToUPnP.addServiceAgent(winampComponent.getServiceAgentList().get(0),
					"Winamp" + winampCounter);
			agentsConnectionToUPnP.addServiceAgent(winampComponent.getServiceAgentList().get(1),
					"Winamp" + winampCounter);
			hashSet.addAll(winampComponent.getServiceAgentList());

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

	/**
	 * 
	 */
	public synchronized void addArduino() {
		// TODO Auto-generated method stub
		arduinoCounter++;

		// ardinoDestinationAgents.addAll(impressAgents);
		// ardinoDestinationAgents.addAll(winampAgents);

		ArduinoInstance arduinoComponent = new ArduinoInstance("ArdinoComponent" + arduinoCounter, null,
				ardinoDestinationAgents, agentsConnectionToUPnP, c);
		// ajout des agents d'arduino dans la liste des agents des boutons
		// impressAgents.addAll(ardinoInstance.getServiceAgentList());
		// ardinoInstance.setReceiverSAList(impressAgents);
		arduinoComponent.setReceiverSAList(ardinoDestinationAgents);

		impressDestinationAgents.addAll(ardinoAgents);
		winampDestinationAgents.addAll(ardinoAgents);

		try {
			String ardino = c.createBeanAtPos("Ardino Component" + arduinoCounter, 
					"WComp.UPnPDevice.Arduino_Button",
					200, 100);

			agentsConnectionToUPnP.addServiceAgent(arduinoComponent.getServiceAgentList().get(0),
					"Ardino Component" + arduinoCounter);
			agentsConnectionToUPnP.addServiceAgent(arduinoComponent.getServiceAgentList().get(1),
					"Ardino Component" + arduinoCounter);

			hashSet.addAll(arduinoComponent.getServiceAgentList());
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

	public static SmaInterface smaInterface = new SmaInterface("creation sma");

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		smaInterface.run();

		boolean arret = true;
		while (arret) {
			TestUI.getInstance().getTwoStepsForOppoCompo().doStep();
		}

	}

}
