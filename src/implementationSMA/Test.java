package implementationSMA;

import java.util.ArrayList;
import java.util.HashSet;

import com.irit.upnp.ContainerWComp;
import com.irit.upnp.ErrorContainer;
import com.irit.upnp.NoDevice;
import com.irit.upnp.NoService;
import com.irit.upnp.NotLaunched;
import com.irit.upnp.SpyNotRunning;

import fr.irit.smac.libs.tooling.avt.range.IMutableRange;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.IAgentStrategy;
import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.InstanceAgents.ButtonInstance;
import implementationSMA.agents.InstanceAgents.ImpressInstance;
import implementationSMA.agents.InstanceAgents.WinampInstance;
import implementationSMA.agents.ServiceAgents.ServiceAgent;
import fr.irit.smac.libs.tooling.messaging.impl.AgentMsgBox;
import fr.irit.smac.libs.tooling.messaging.impl.BasicMutableDirectory;
import fr.irit.smac.libs.tooling.messaging.impl.IMutableDirectory;

public class Test {

	/**
	 * 
	 * @param args
	 */
	public static ArrayList<SAMsgBoxHistoryAgent> listSAM = new ArrayList<SAMsgBoxHistoryAgent>(4);

	private static void doSAMstep() {
		for (SAMsgBoxHistoryAgent sam : listSAM) {
			sam.nextStep();
		}
	}

	public static void main(String[] args) {

		// 2 boutons
		/*
		 * IMutableDirectory<AbstractMessage> iMutableDirectory = new
		 * BasicMutableDirectory<AbstractMessage>(); IMsgBox<AbstractMessage>
		 * buttonMessageBox = new AgentMsgBox<AbstractMessage>("Button",
		 * iMutableDirectory);
		 */
		// Routage routage = new Routage();
		// creation de ImpressJ
		ContainerWComp c = new ContainerWComp("Container1_Structural_0");

		AgentsConnectionToUPnP agentsConnectionToUPnP = new AgentsConnectionToUPnP();

		HashSet<ServiceAgent> buttonAgents = new HashSet<ServiceAgent>();

		ImpressInstance impress = new ImpressInstance("ImpressJ", null, buttonAgents, agentsConnectionToUPnP, c);

		HashSet<ServiceAgent> impressAgents = new HashSet<ServiceAgent>();
		impressAgents.addAll(impress.getServiceAgentList());

		ButtonInstance boutonPred = new ButtonInstance("@Button", null, "prevButton", impressAgents,
				agentsConnectionToUPnP, c);
		buttonAgents.addAll(boutonPred.getServiceAgentList());

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
			//pause(5000);
			// creation de l'ImpressJ
			String impressJS = c.createBeanAtPos("ImpressJS", "WComp.UPnPDevice.ImpressJS", 400, 100);
			String joystickArd = c.createBeanAtPos("Arduino Joystick", "WComp.UPnPDevice.Arduino_Joystick", 200, 200);
			String winamp1 = c.createBeanAtPos("Winamp", "WComp.UPnPDevice.WinampRemote", 400, 200);

			//@SuppressWarnings("unused")
			//String linkBut1 = c.createLink("Bouton 1", "Click", "ImpressJS", "Next", "");
			//String linkDown = c.createLink("Arduino Joystick", "X_Down_Event", "Winamp", "VolumeDown", "");
			
			//c.removeBean("Bouton 1");
			// ajout dans le map

			agentsConnectionToUPnP.addServiceAgent(impress.getServiceAgentList().get(0), "ImpressJS");
			agentsConnectionToUPnP.addServiceAgent(impress.getServiceAgentList().get(1), "ImpressJS");

			agentsConnectionToUPnP.addServiceAgent(boutonPred.getServiceAgentList().get(0), "Bouton 1");
			agentsConnectionToUPnP.addServiceAgent(boutonSuiv.getServiceAgentList().get(0), "Bouton 2");

			// debut sma
			/*
			 * ButtonInstance boutonSuiv = new ButtonInstance("@Button1", null,
			 * "nextButton", listServiceAgents);
			 */

			// Ajout dans hashset

			HashSet<IAgentStrategy> hashSet = new HashSet<IAgentStrategy>();
			hashSet.addAll(boutonPred.getServiceAgentList());
			// hashSet.addAll(boutonSuiv.getServiceAgentList());
			// hashSet.addAll(impress.getServiceAgentList());

			/*
			 * SequentialSystemStrategyForOppoCompo sssfoc = new
			 * SequentialSystemStrategyForOppoCompo(hashSet); sssfoc.doStep();
			 * sssfoc.doStep(); sssfoc.doStep(); sssfoc.doStep();
			 */

			// boutonPred.getServiceAgentList().get(0).nextStep();
			/*
			 * IAgentStrategy agent = null;
			 * SequentialSystemStrategyForOppoCompo.AgentWrapper agentRapper =
			 * sssfoc.new AgentWrapper(agent);
			 */

			System.out.println("/*==============debut de l'execution du step-1====================*/");
			// step-1: le boutonPred = B1 envoie une annonce aux 2 agents de
			// ImpressJ. il crée un agent contexte ACB1_1
			// Le bouton B2 et les interfaces I1 et I2 ne font rien

			boutonPred.getServiceAgentList().get(0).nextStep();

			System.out.println("/*==============execution du step-1 terminé=======================*/");

			System.out.println("/*===================debut de l'execution du step-2===============*/");
			// step-2: L'interface I1 = ImpressJ1 a une message de type ANNONCER
			// dans sa boite provenant de B1. Il envoie a B1 le message REPONDRE
			// et
			// crée un agent contexte ACI1_1
			// L'interface I2 = ImpressJ2 a une message de type ANNONCER dans sa
			// boite provenant de B1. Il envoie à B1 le message REPONDRE et crée
			// un
			// agent contexte ACI2_1
			// Le bouton B2 = boutonSuiv n'a rien dans sa boite et effectue une
			// ANNONCE vers les interfaces I1 et I2 d'ImpressJ. Il crée un agent
			// contexte ACB2_1
			// Le bouton B1 ne fait rien.

			for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
				impress.getServiceAgentList().get(i).nextStep();
			}

			boutonSuiv.getServiceAgentList().get(0).nextStep();

			System.out.println("/*===================execution du step-2 terminé===================*/");

			System.out.println("/*====================debut de l'execution du step-3==============*/");
			// step-3: le bouton pred traite les reponses recues de la part de
			// la
			// part de ImpressJ (par exemple demande de connexion pour l'une des
			// interfaces de ImpressJ). Le bouton suiv peut recevoir des
			// reponses a
			// son annonce au cycle precedent. Les agents de ImpressJ feront des
			// annonces.
			// version 2: L'interface I1 a un message de type ANNONCER provenant
			// de
			// B2 = boutonsuiv. Vue qu'elle n'est pas connecté, elle envoie le
			// message REPONDRE et crée un agent contexte ACI1_2 ou ACI1_1 est
			// actif.
			// L'interface I2 a un message de type ANNONCER provenant de
			// B2 = boutonsuiv. Vue qu'elle n'est pas connecté, elle envoie le
			// message REPONDRE et crée un agent contexte ACI2_2 ou ACI2_1 est
			// actif.
			// Le bouton B1 a 2 messages de type REPONDRE dans sa boite
			// provenant de
			// I1 et I2. Dans notre cas, il repond par:
			// SECONNECTER à I1 -> création de l'agent contexte ACB1_2
			// NERIENFAIRE à I2 -> création de l'agent contexte ACB1_3
			// B2 ne fait rien

			// boutonSuiv.getServiceAgentList().get(0).nextStep();

			for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
				impress.getServiceAgentList().get(i).nextStep();

			}
			boutonPred.getServiceAgentList().get(0).nextStep();
			System.out.println("/*====================execution du step-3 terminé================*/");
			System.out.println("/*====================debut de l'execution du step-4==============*/");

			impress.getServiceAgentList().get(0).nextStep();
			boutonSuiv.getServiceAgentList().get(0).nextStep();
			impress.getServiceAgentList().get(1).nextStep();

			/*
			 * for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
			 * impress.getServiceAgentList().get(i).nextStep();
			 * 
			 * } boutonSuiv.getServiceAgentList().get(0).nextStep();
			 */

			System.out.println("/*====================execution du step-4 terminé================*/");

			System.out.println("/*====================debut de l'execution du step-5==============*/");

			boutonSuiv.getServiceAgentList().get(0).nextStep();
			impress.getServiceAgentList().get(0).nextStep();
			boutonPred.getServiceAgentList().get(0).nextStep();

			// impress.getServiceAgentList().get(1).nextStep();

			System.out.println("/*====================execution du step-5 terminé================*/");

			System.out.println("/*====================debut de l'execution du step-6==============*/");

			for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
				impress.getServiceAgentList().get(i).nextStep();

			}

			boutonPred.getServiceAgentList().get(0).nextStep();

			System.out.println("/*====================execution du step-6 terminé================*/");

			System.out.println("/*====================debut de l'execution du step-7==============*/");
			// boutonPred (B_1) et impress.getServiceAgentList().get(0) (I_1)
			// sont
			// connectés et ne s'exécute pas
			// impress.getServiceAgentList().get(1) (I_2) ne s'exécute pas au
			// cours
			// de ce cycle. Il va attendre le message de boutonSuiv(B_2)

			boutonSuiv.getServiceAgentList().get(0).nextStep();

			System.out.println("/*====================execution du step-7   terminé================*/");

			System.out.println("/*====================debut de l'execution du step-8==============*/");
			// boutonSuiv(B_2) n'est pas exécuté
			impress.getServiceAgentList().get(1).nextStep();

			System.out.println("/*====================execution du step-8   terminé================*/");

			System.out.println("/*====================debut de l'execution du step-9==============*/");

			// impress.getServiceAgentList().get(1) (I_2) n'est pas exécuté
			// pendant
			// ce cycle
			boutonSuiv.getServiceAgentList().get(0).nextStep();

			System.out.println("/*====================execution du step-9   terminé================*/");

			System.out.println("/*====================debut de l'execution du step-10==============*/");

			// boutonSuiv(B_2) n'est pas exécuté
			impress.getServiceAgentList().get(1).nextStep();

			System.out.println("/*====================execution du step-10   terminé================*/");

			System.out.println("/*====================debut de l'execution du step-11==============*/");

			// impress.getServiceAgentList().get(1) (I_2) n'est pas exécuté
			// pendant
			// ce cycle
			boutonSuiv.getServiceAgentList().get(0).nextStep();

			System.out.println("/*====================execution du step-11   terminé================*/");
			// finsma
			boutonSuiv.disappear();

			WinampInstance winampComponent = new WinampInstance("WinampComponent", null, buttonAgents, agentsConnectionToUPnP, c);
			String winamp = c.createBeanAtPos("Winamp", "WComp.UPnPDevice.WinampRemote", 400, 200);
			agentsConnectionToUPnP.addServiceAgent(winampComponent.getServiceAgentList().get(0), "Winamp");
			agentsConnectionToUPnP.addServiceAgent(winampComponent.getServiceAgentList().get(1), "Winamp");
			// ajout des agents du composant winamp à la liste des agents
			// pouvant recevoir l'annonce des agents
			impressAgents.addAll(winampComponent.getServiceAgentList());
			System.out.println("/*====================debut de l'execution du step-11bis==============*/");
			boutonPred.getServiceAgentList().get(0).nextStep();
			//boutonSuiv.nextStep();
			for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
				impress.getServiceAgentList().get(i).nextStep();
				winampComponent.getServiceAgentList().get(i).nextStep();

			}
			System.out.println("/*====================execution du step-11Bis   terminé================*/");

			System.out.println("/*====================debut de l'execution du step-12==============*/");
			boutonPred.getServiceAgentList().get(0).nextStep();
			boutonSuiv.nextStep();
			for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
				impress.getServiceAgentList().get(i).nextStep();
				winampComponent.getServiceAgentList().get(i).nextStep();

			}
			System.out.println("/*====================execution du step-12   terminé================*/");
			
			System.out.println("/*====================debut de l'execution du step-13==============*/");
			boutonPred.getServiceAgentList().get(0).nextStep();
			boutonSuiv.nextStep();
			for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
				impress.getServiceAgentList().get(i).nextStep();
				winampComponent.getServiceAgentList().get(i).nextStep();

			}
			System.out.println("/*====================execution du step-13   terminé================*/");
			
			System.out.println("/*====================debut de l'execution du step-14==============*/");
			boutonPred.getServiceAgentList().get(0).nextStep();
			boutonSuiv.nextStep();
			for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
				impress.getServiceAgentList().get(i).nextStep();
				winampComponent.getServiceAgentList().get(i).nextStep();

			}
			System.out.println("/*====================execution du step-14   terminé================*/");
			
			System.out.println("/*====================debut de l'execution du step-15==============*/");
			boutonPred.getServiceAgentList().get(0).nextStep();
			boutonSuiv.nextStep();
			for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
				impress.getServiceAgentList().get(i).nextStep();
				winampComponent.getServiceAgentList().get(i).nextStep();

			}
			System.out.println("/*====================execution du step-15   terminé================*/");
			
			System.out.println("/*====================debut de l'execution du step-16==============*/");
			boutonPred.getServiceAgentList().get(0).nextStep();
			boutonSuiv.nextStep();
			for (int i = 0; i < impress.getServiceAgentList().size(); i++) {
				impress.getServiceAgentList().get(i).nextStep();
				winampComponent.getServiceAgentList().get(i).nextStep();

			}
			System.out.println("/*====================execution du step-16   terminé================*/");

			pause(100000);
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
