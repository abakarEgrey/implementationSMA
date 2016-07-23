package implementationSMA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import com.irit.upnp.ContainerWComp;
import com.irit.upnp.ErrorContainer;
import com.irit.upnp.NoDevice;
import com.irit.upnp.NoService;
import com.irit.upnp.NotLaunched;
import com.irit.upnp.SpyNotRunning;

import fr.irit.smac.libs.tooling.scheduling.IAgentStrategy;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.TwoStepsSystemStrategy;
import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.ArdinoInstance;
import implementationSMA.agents.ButtonInstance;
import implementationSMA.agents.ImpressInstance;
import implementationSMA.agents.ServiceAgent;
import implementationSMA.agents.Winamp;

public class TestTwoSteps {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ContainerWComp c = new ContainerWComp("Container1_Structural_0");

		AgentsConnectionToUPnP agentsConnectionToUPnP = new AgentsConnectionToUPnP();
		
		Pile pile =  new Pile();

		HashSet<ServiceAgent> buttonAgents = new HashSet<ServiceAgent>();

		ImpressInstance impress = new ImpressInstance("ImpressJ", null, buttonAgents, agentsConnectionToUPnP, c);
		//initialisation de la pile
		impress.getServiceAgentList().get(0).setPile(pile);
		impress.getServiceAgentList().get(1).setPile(pile);

		HashSet<ServiceAgent> impressAgents = new HashSet<ServiceAgent>();
		impressAgents.addAll(impress.getServiceAgentList());

		ButtonInstance boutonPred = new ButtonInstance("@Button", null, "prevButton", impressAgents,
				agentsConnectionToUPnP, c);
		//ajout de la pile
		boutonPred.getServiceAgentList().get(0).setPile(pile);
		buttonAgents.addAll(boutonPred.getServiceAgentList());

		ButtonInstance boutonSuiv = new ButtonInstance("@Button1", null, "nextButton", impressAgents,
				agentsConnectionToUPnP, c);
		
		boutonSuiv.getServiceAgentList().get(0).setPile(pile);
		
		buttonAgents.addAll(boutonSuiv.getServiceAgentList());

		impress.setReceiverSAList(buttonAgents);
		
		
		try {
			pause(2000);
			// creation des 2 boutons de manipuler l'ImpressJ
			String bouton1 = c.createBeanAtPos("Bouton 1", "System.Windows.Forms.Button", 600, 400);
			//pause(5000);
			String bouton2 = c.createBeanAtPos("Bouton 2", "System.Windows.Forms.Button", 200, 400);
			//pause(5000);
			// creation de l'ImpressJ
			String impressJS = c.createBeanAtPos("ImpressJS", "WComp.UPnPDevice.ImpressJS", 400, 100);

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

			HashSet<ITwoStepsAgent> hashSet = new HashSet<ITwoStepsAgent>();
			hashSet.addAll(boutonPred.getServiceAgentList());
			//SequentialSystemStrategyForOppoCompo sssfoc = new SequentialSystemStrategyForOppoCompo(hashSet);
			TwoStepsForOppoCompo twoStepsForOppoCompo = new TwoStepsForOppoCompo(hashSet);
			//first step
			ServiceAgent.getPile().empiler("/*==============debut de l'execution du step-0====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-0 terminé=======================*/");
			hashSet.addAll(boutonSuiv.getServiceAgentList());
			hashSet.addAll(impress.getServiceAgentList());
			twoStepsForOppoCompo.addAgents(hashSet);
			/*boolean arret = true;
			while(arret) {
				twoStepsForOppoCompo.doStep();
			}*/
			
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-1====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-1 terminé====================*/");
			//sssfoc.doStep();
			/*hashSet.addAll(boutonSuiv.getServiceAgentList());
			hashSet.addAll(impress.getServiceAgentList());
			twoStepsForOppoCompo.addAgents(hashSet);*/
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-2====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-2 terminé====================*/");
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-3====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-3 terminé====================*/");
			
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-4====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-4 terminé====================*/");
			
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-5====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step- terminé====================*/");
		
			/*sssfoc.doStep();*/
			boutonPred.disappear();
			//boutonSuiv.disappear();
			//creation et apparition de winamp
			Winamp winampComponent = new Winamp("WinampComponent", null, buttonAgents, agentsConnectionToUPnP, c);
			
			winampComponent.getServiceAgentList().get(0).setPile(pile);
			winampComponent.getServiceAgentList().get(1).setPile(pile);
			
			pause(5000);
			String winamp = c.createBeanAtPos("Winamp", "WComp.UPnPDevice.WinampRemote", 400, 200);
			
			agentsConnectionToUPnP.addServiceAgent(winampComponent.getServiceAgentList().get(0), "Winamp");
			agentsConnectionToUPnP.addServiceAgent(winampComponent.getServiceAgentList().get(1), "Winamp");
			//ajout des agents du composant winamp à la liste des agents pouvant recevoir l'annonce des agents
			impressAgents.addAll(winampComponent.getServiceAgentList());
			hashSet.addAll(winampComponent.getServiceAgentList());
			twoStepsForOppoCompo.addAgents(hashSet);
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-6====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-6 terminé====================*/");
			
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-7====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-7 terminé====================*/");
			
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-8====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-8 terminé====================*/");
			
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-8====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-8 terminé====================*/");
			
			ServiceAgent.getPile().empiler("\n\n/*==============debut de l'execution du step-9====================*/");
			twoStepsForOppoCompo.doStep();
			ServiceAgent.getPile().empiler("/*==============execution du step-9 terminé====================*/");
			
//			boolean arret = true;
//			int nbIter = 0;
//			while(arret) {
//				twoStepsForOppoCompo.doStep();
//				nbIter++;
//				if (nbIter > 10){
//					arret = false;
//				}
//			}
			
			//création et apparition du composant ardino avec les 2 boutons physiques
			HashSet<ServiceAgent> ardinoDestinationAgents = new HashSet<>();
			ardinoDestinationAgents.addAll(impressAgents);
			ardinoDestinationAgents.addAll(winampComponent.getServiceAgentList());
			ArdinoInstance ardinoInstance = new ArdinoInstance("ArdinoComponent", null, ardinoDestinationAgents, agentsConnectionToUPnP, c);
			
			ardinoInstance.getServiceAgentList().get(0).setPile(pile);
			ardinoInstance.getServiceAgentList().get(1).setPile(pile);
			
			pause(5000);
			String ardino = c.createBeanAtPos("Ardino Component", "WComp.UPnPDevice.Arduino_Button", 200, 100);
			
			agentsConnectionToUPnP.addServiceAgent(ardinoInstance.getServiceAgentList().get(0), "Ardino Component");
			agentsConnectionToUPnP.addServiceAgent(ardinoInstance.getServiceAgentList().get(1), "Ardino Component");
			
			hashSet.addAll(ardinoInstance.getServiceAgentList());
			twoStepsForOppoCompo.addAgents(hashSet);
			boolean arret = true;
			int nbIter = 0;
			while(arret) {
				twoStepsForOppoCompo.doStep();
				nbIter++;
				if (nbIter > 20){
					arret = false;
				}
			}
			
			pause (5000);
			//boutonPred.disappear();
			
			/*hashSet.addAll(boutonSuiv.getServiceAgentList());
			hashSet.addAll(impress.getServiceAgentList());
			sssfoc.doStep();
			sssfoc.doStep();
			sssfoc.doStep();*/

			// boutonPred.getServiceAgentList().get(0).nextStep();
			/*
			 * IAgentStrategy agent = null;
			 * SequentialSystemStrategyForOppoCompo.AgentWrapper agentRapper =
			 * sssfoc.new AgentWrapper(agent);
			 */
			try {
				BufferedWriter out =  new BufferedWriter(new FileWriter("fichOut.txt"));
				BufferedWriter out1 =  new BufferedWriter(new FileWriter("fichArobase.txt"));
				while (!ServiceAgent.getPile().isEmpty()){
					String strToDisplay = ServiceAgent.getPile().depiler();
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
