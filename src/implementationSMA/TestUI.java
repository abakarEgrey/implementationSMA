package implementationSMA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
import implementationSMA.agents.InstanceAgents.WinampInstance;
import implementationSMA.agents.ServiceAgents.ServiceAgent;
import implementationSMA.enumeration.InstanceType;

public class TestUI {

	static ContainerWComp c = new ContainerWComp("Container1_Structural_0");

	private AgentsConnectionToUPnP agentsConnectionToUPnP = new AgentsConnectionToUPnP();

	private HashSet<ServiceAgent> impressAgents = new HashSet<ServiceAgent>();
	private HashSet<ServiceAgent> impressDestinationAgents = new HashSet<ServiceAgent>();

	private HashSet<ServiceAgent> buttonAgents = new HashSet<ServiceAgent>();
	private HashSet<ServiceAgent> buttonDestinationAgents = new HashSet<ServiceAgent>();

	private HashSet<ServiceAgent> arduinoAgents = new HashSet<>();
	private HashSet<ServiceAgent> arduinoDestinationAgents = new HashSet<>();

	private HashSet<ServiceAgent> winampAgents = new HashSet<>();
	private HashSet<ServiceAgent> winampDestinationAgents = new HashSet<>();

	private HashSet<ServiceAgent> joystickAgents = new HashSet<>();

	private HashSet<ITwoStepsAgent> hashSet = new HashSet<ITwoStepsAgent>();
	private TwoStepsForOppoCompo twoStepsForOppoCompo = new TwoStepsForOppoCompo(hashSet);
	private HashMap<String, InstanceAgent> hashMapUiInstance = new HashMap<>();

	private Pile pile = new Pile();
	
	//pour tester le code
	private static int nbBoutons = 0;

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
			arduinoCounter = 0, arduinoInstanceCounter = 0, winampCounter = 0, winampInstanceCounter = 0,
			componentCounter = 0;

	/**
	 * 
	 * @param x0
	 * @param y0
	 * @param rayon
	 * @param angle
	 * @return
	 */
	private int[] getCircle(int x0, int y0, int rayon, double angle) {
		int xP = (int) (x0 + rayon * Math.cos(angle));
		int yP = (int) (y0 + rayon * Math.sin(angle));
		return new int[] { xP, yP };
	}

	public synchronized void addButton() {
		nbBoutons++;
		buttonCounter++;
		buttonInstanceCounter++;
		componentCounter++;
		String beanName = "Button " + buttonCounter;
		ButtonInstance boutonComponent = new ButtonInstance(beanName, null, "Button " + buttonCounter,
				buttonDestinationAgents, agentsConnectionToUPnP, c);
		// stockage de beanName
		boutonComponent.setBeanName(beanName);
		hashMapUiInstance.put(beanName, boutonComponent);
		TestUI.smaInterface.addInstance(boutonComponent.getId());
		// ajout de la pile
		// boutonPred.getServiceAgentList().get(0).setPile(pile);
		buttonAgents.addAll(boutonComponent.getServiceAgentList());
		boutonComponent.setReceiverSAList(buttonDestinationAgents);

		impressDestinationAgents.addAll(buttonAgents);
		winampDestinationAgents.addAll(buttonAgents);

		try {
			// pause(2000);
			// creation des 2 boutons de manipuler l'ImpressJ
			int[] position = getCircle(350, 300, 250, (componentCounter - 1) * 60.0);
			String bouton1 = c.createBeanAtPos(beanName, "System.Windows.Forms.Button", position[0], position[1]);

			agentsConnectionToUPnP.addServiceAgent(boutonComponent.getServiceAgentList().get(0), beanName);

			hashSet.addAll(boutonComponent.getServiceAgentList());
			twoStepsForOppoCompo.addAgents(hashSet);

			boutonComponent.addOnRemovedListener(new OnRemovedListener() {

				@Override
				public void onRemoved(Object object) {

					ButtonInstance instance = (ButtonInstance) object;
					String name = instance.getBeanName();
					// TestUI.smaInterface.showConnexion("impressDestinationAgents
					// avant supp", impressDestinationAgents.size() + "");
					impressDestinationAgents.removeAll(instance.getServiceAgentList());
					// TestUI.smaInterface.showConnexion("impressDestinationAgents
					// apres supp", impressDestinationAgents.size() + "");
					// TestUI.smaInterface.showConnexion("winampDestinationAgents
					// avant supp", winampDestinationAgents.size() + "");
					winampDestinationAgents.removeAll(instance.getServiceAgentList());
					// TestUI.smaInterface.showConnexion("winampDestinationAgents
					// apres supp", winampDestinationAgents.size() + "");
					// TestUI.smaInterface.showConnexion("buttonAgents avant
					// supp", buttonAgents.size() + "");
					buttonAgents.removeAll(instance.getServiceAgentList());
					// TestUI.smaInterface.showConnexion("buttonAgents apres
					// supp", buttonAgents.size() + "");

					// TestUI.smaInterface.showConnexion("hashSet avant supp",
					// hashSet.size() + "");
					//pour le test
					HashSet<ITwoStepsAgent> hashSetLocal = new HashSet<ITwoStepsAgent>();
					hashSet.removeAll(instance.getServiceAgentList());
					hashSetLocal = hashSet;
					String aff = "";
					for (ITwoStepsAgent itsa : hashSet){
						ServiceAgent sa = (ServiceAgent)itsa;
						aff += "\t" + sa.getId();
					}
					TestUI.smaInterface.showConnexion("les agents du hashset apres suppression ",aff + "");
					upDatesAgent(InstanceType.ButtonInstance, instance);
					//twoStepsForOppoCompo.addAgents(hashSet);
					//twoStepsForOppoCompo.setAgents(hashSet);
					twoStepsForOppoCompo.removeAllAgents(instance.getServiceAgentList());
					for (ServiceAgent sA : instance.getServiceAgentList()) {
						sA.setALive(false);
					}
					TestUI.smaInterface.showConnexion("twoStepsForOppoCompo.getAgents().size()", twoStepsForOppoCompo.getAgents().size() + "");
					
					try {
						c.removeBean(name);
						ServiceAgent.affichage = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
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
		componentCounter++;

		String beanName = "ImpressJ" + impressCounter;
		ImpressInstance impressComponent = new ImpressInstance(beanName, null, impressDestinationAgents,
				agentsConnectionToUPnP, c);

		// stockage de beanName
		impressComponent.setBeanName(beanName);
		hashMapUiInstance.put(beanName, impressComponent);
		TestUI.smaInterface.addInstance(impressComponent.getId());

		impressAgents.addAll(impressComponent.getServiceAgentList());
		impressComponent.setReceiverSAList(impressDestinationAgents);

		arduinoDestinationAgents.addAll(impressAgents);
		buttonDestinationAgents.addAll(impressAgents);

		try {
			int[] position = getCircle(350, 300, 250, (componentCounter - 1) * 60.0);

			String impressJS = c.createBeanAtPos(beanName, "WComp.UPnPDevice.ImpressJS", position[0], position[1]);
			// String impressJS1 = c.createBeanAtPos(beanName + "b",
			// "WComp.UPnPDevice.ImpressJS", position[0] + 50, position[1] +
			// 50);

			// ajout dans le map

			agentsConnectionToUPnP.addServiceAgent(impressComponent.getServiceAgentList().get(0), beanName);
			agentsConnectionToUPnP.addServiceAgent(impressComponent.getServiceAgentList().get(1), beanName);
			hashSet.addAll(impressComponent.getServiceAgentList());

			twoStepsForOppoCompo.addAgents(hashSet);

			impressComponent.addOnRemovedListener(new OnRemovedListener() {

				@Override
				public void onRemoved(Object object) {

					ImpressInstance instance = (ImpressInstance) object;
					String name = instance.getBeanName();
					// TestUI.smaInterface.showConnexion("impressDestinationAgents
					// avant supp", impressDestinationAgents.size() + "");
					impressAgents.removeAll(instance.getServiceAgentList());
					// TestUI.smaInterface.showConnexion("impressDestinationAgents
					// apres supp", impressDestinationAgents.size() + "");
					// TestUI.smaInterface.showConnexion("winampDestinationAgents
					// avant supp", winampDestinationAgents.size() + "");
					arduinoDestinationAgents.removeAll(instance.getServiceAgentList());
					// TestUI.smaInterface.showConnexion("winampDestinationAgents
					// apres supp", winampDestinationAgents.size() + "");
					// TestUI.smaInterface.showConnexion("buttonAgents avant
					// supp", buttonAgents.size() + "");
					buttonDestinationAgents.removeAll(instance.getServiceAgentList());
					// TestUI.smaInterface.showConnexion("buttonAgents apres
					// supp", buttonAgents.size() + "");

					// TestUI.smaInterface.showConnexion("hashSet avant supp",
					// hashSet.size() + "");
					hashSet.removeAll(instance.getServiceAgentList());
					// TestUI.smaInterface.showConnexion("hashSet avant supp",
					// hashSet.size() + "");
					upDatesAgent(InstanceType.ImpressInstance, instance);
					//twoStepsForOppoCompo.addAgents(hashSet);
					twoStepsForOppoCompo.setAgents(hashSet);

					try {
						c.removeBean(name);
					} catch (Exception e) {
						e.printStackTrace();
					}
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

	public synchronized void addWinamp() {
		winampCounter++;
		componentCounter++;

		String beanName = "Winamp" + winampCounter;

		WinampInstance winampComponent = new WinampInstance(beanName, null, buttonAgents, agentsConnectionToUPnP, c);

		// stockage de beanName
		winampComponent.setBeanName(beanName);
		hashMapUiInstance.put(beanName, winampComponent);
		TestUI.smaInterface.addInstance(winampComponent.getId());

		winampAgents.addAll(winampComponent.getServiceAgentList());
		winampComponent.setReceiverSAList(winampDestinationAgents);

		arduinoDestinationAgents.addAll(winampAgents);
		buttonDestinationAgents.addAll(winampAgents);

		try {
			int[] position = getCircle(350, 300, 250, (componentCounter - 1) * 60.0);

			String winamp = c.createBeanAtPos(beanName, "WComp.UPnPDevice.WinampRemote", position[0], position[1]);

			agentsConnectionToUPnP.addServiceAgent(winampComponent.getServiceAgentList().get(0), beanName);
			agentsConnectionToUPnP.addServiceAgent(winampComponent.getServiceAgentList().get(1), beanName);
			hashSet.addAll(winampComponent.getServiceAgentList());

			twoStepsForOppoCompo.addAgents(hashSet);

			winampComponent.addOnRemovedListener(new OnRemovedListener() {

				@Override
				public void onRemoved(Object object) {

					WinampInstance instance = (WinampInstance) object;
					String name = instance.getBeanName();

					winampAgents.removeAll(instance.getServiceAgentList());

					arduinoDestinationAgents.removeAll(instance.getServiceAgentList());

					buttonDestinationAgents.removeAll(instance.getServiceAgentList());

					hashSet.removeAll(instance.getServiceAgentList());

					upDatesAgent(InstanceType.WinampInstance, instance);
					//twoStepsForOppoCompo.addAgents(hashSet);
					twoStepsForOppoCompo.setAgents(hashSet);

					try {
						c.removeBean(name);
					} catch (Exception e) {
						e.printStackTrace();
					}
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

	/**
	 * 
	 */
	public synchronized void addArduino() {
		// TODO Auto-generated method stub
		arduinoCounter++;
		componentCounter++;
		// ardinoDestinationAgents.addAll(impressAgents);
		// ardinoDestinationAgents.addAll(winampAgents);
		String beanName = "ArdinoComponent" + arduinoCounter;

		ArduinoInstance arduinoComponent = new ArduinoInstance(beanName, null, arduinoDestinationAgents,
				agentsConnectionToUPnP, c);
				// ajout des agents d'arduino dans la liste des agents des
				// boutons
				// impressAgents.addAll(ardinoInstance.getServiceAgentList());
				// ardinoInstance.setReceiverSAList(impressAgents);

		// stockage de beanName
		arduinoComponent.setBeanName(beanName);
		hashMapUiInstance.put(beanName, arduinoComponent);
		TestUI.smaInterface.addInstance(arduinoComponent.getId());

		arduinoAgents.addAll(arduinoComponent.getServiceAgentList());

		arduinoComponent.setReceiverSAList(arduinoDestinationAgents);

		impressDestinationAgents.addAll(arduinoAgents);
		winampDestinationAgents.addAll(arduinoAgents);

		try {
			int[] position = getCircle(350, 300, 250, (componentCounter - 1) * 60.0);
			
			String ardino = c.createBeanAtPos(beanName, "WComp.UPnPDevice.Arduino_Button",
					position[0], position[1]);

			agentsConnectionToUPnP.addServiceAgent(arduinoComponent.getServiceAgentList().get(0),
					beanName);
			agentsConnectionToUPnP.addServiceAgent(arduinoComponent.getServiceAgentList().get(1),
					beanName);

			hashSet.addAll(arduinoComponent.getServiceAgentList());
			twoStepsForOppoCompo.addAgents(hashSet);
			
			arduinoComponent.addOnRemovedListener(new OnRemovedListener() {

				@Override
				public void onRemoved(Object object) {

					ArduinoInstance instance = (ArduinoInstance) object;
					String name = instance.getBeanName();
					
					arduinoAgents.removeAll(instance.getServiceAgentList());
					
					impressDestinationAgents.removeAll(instance.getServiceAgentList());
					
					winampDestinationAgents.removeAll(instance.getServiceAgentList());
					
					hashSet.removeAll(instance.getServiceAgentList());
					
					upDatesAgent(InstanceType.ArduinoInstance, instance);
					//twoStepsForOppoCompo.addAgents(hashSet);
					twoStepsForOppoCompo.setAgents(hashSet);

					try {
						c.removeBean(name);
					} catch (Exception e) {
						e.printStackTrace();
					}
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

	public void removeAgent(String id) {
		// TODO Auto-generated method stub
		InstanceAgent agent = hashMapUiInstance.get(id);
		if (agent != null) {
			if (agent.getInstanceName().equals("ButtonInstance")) {
				((ButtonInstance) agent).destroy();
				hashMapUiInstance.remove(id);
			} else if (agent.getInstanceName().equals("ImpressInstance")) {
				((ImpressInstance) agent).destroy();
			} else if (agent.getInstanceName().equals("WinampInstance")) {
				((WinampInstance) agent).destroy();
			} else if (agent.getInstanceName().equals("ArduinoInstance")) {
				((ArduinoInstance) agent).destroy();
			} else {
				// une exception
			}
		}
		smaInterface.update(0, smaInterface.getjList1().getModel().getSize() - 1);
	}

	/**
	 * Cette methode permet de mettre à jour les listes (la liste contenant les
	 * agents services de l'instance et la liste des agents destination)
	 * 
	 * @param instanceType:
	 *            une énumération
	 * @param instance:
	 *            represente l'instance de l'agent Instance
	 */
	public void upDatesAgent(InstanceType instanceType, InstanceAgent instance) {
		Set<String> setOfInstanceId;
		switch (instanceType) {
		case ButtonInstance:
			setOfInstanceId = this.hashMapUiInstance.keySet();
			for (String idInstanceAgent : setOfInstanceId) {
				InstanceAgent instanceAgent = this.hashMapUiInstance.get(idInstanceAgent);
				// modifier les listes destination des tous les agents avec qui
				// communiquent les agents du bouton instance
				if (!instanceAgent.getInstanceName().equals("ButtonInstance")) {
					if (instanceAgent.getInstanceName().equals("ImpressInstance")) {
						instanceAgent.setReceiverSAList(impressDestinationAgents);
					}
					if (instanceAgent.getInstanceName().equals("WinampInstance")) {
						instanceAgent.setReceiverSAList(winampDestinationAgents);
					}
				}
			}
			break;
		case ImpressInstance:
			setOfInstanceId = this.hashMapUiInstance.keySet();
			for (String idInstanceAgent : setOfInstanceId) {
				InstanceAgent instanceAgent = this.hashMapUiInstance.get(idInstanceAgent);
				// modifier les listes destination des tous les agents avec qui
				// communiquent les agents du bouton instance
				if (!instanceAgent.getInstanceName().equals("ImpressInstance")) {
					if (instanceAgent.getInstanceName().equals("ButtonInstance")) {
						instanceAgent.setReceiverSAList(buttonDestinationAgents);
					}
					if (instanceAgent.getInstanceName().equals("ArduinoInstance")) {
						instanceAgent.setReceiverSAList(arduinoDestinationAgents);
					}
				}
			}
			break;
		case WinampInstance:
			setOfInstanceId = this.hashMapUiInstance.keySet();
			for (String idInstanceAgent : setOfInstanceId) {
				InstanceAgent instanceAgent = this.hashMapUiInstance.get(idInstanceAgent);
				// modifier les listes destination des tous les agents avec qui
				// communiquent les agents du bouton instance
				if (!instanceAgent.getInstanceName().equals("WinampInstance")) {
					if (instanceAgent.getInstanceName().equals("ButtonInstance")) {
						instanceAgent.setReceiverSAList(buttonDestinationAgents);
					}
					if (instanceAgent.getInstanceName().equals("ArduinoInstance")) {
						instanceAgent.setReceiverSAList(arduinoDestinationAgents);
					}
				}
			}
			break;
		case ArduinoInstance:
			setOfInstanceId = this.hashMapUiInstance.keySet();
			for (String idInstanceAgent : setOfInstanceId) {
				InstanceAgent instanceAgent = this.hashMapUiInstance.get(idInstanceAgent);
				// modifier les listes destination des tous les agents avec qui
				// communiquent les agents du bouton instance
				if (!instanceAgent.getInstanceName().equals("ArduinoInstance")) {
					if (instanceAgent.getInstanceName().equals("ImpressInstance")) {
						instanceAgent.setReceiverSAList(impressDestinationAgents);
					}
					if (instanceAgent.getInstanceName().equals("WinampInstance")) {
						instanceAgent.setReceiverSAList(winampDestinationAgents);
					}
				}
			}
			break;
		default:
			// throw exception
		}
	}

	public static SmaInterface smaInterface = new SmaInterface("creation sma");

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nbIteration = 0;
		smaInterface.run();
		
		boolean arret = true;
		while (arret) {
			TestUI.getInstance().getTwoStepsForOppoCompo().doStep();
			//pause(10);
			if (nbBoutons > 3){
				arret = false;
			}
			nbIteration++;
		}
		
		try {
			BufferedWriter out =  new BufferedWriter(new FileWriter("fichOut.txt"));
			BufferedWriter out1 =  new BufferedWriter(new FileWriter("fichArobase.txt"));
			ServiceAgent.getPile();
			while (!Pile.isEmpty()){
				ServiceAgent.getPile();
				String strToDisplay = Pile.depiler();
				if (!strToDisplay.contains("display")){
					out.write("\t \t" + strToDisplay + "\n");
					if (strToDisplay.startsWith("@")){
						out1.write(strToDisplay + "\n");
					}
					//System.out.println("\t \t" + strToDisplay);
				} else {
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
