package implementationSMA;

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
import implementationSMA.agents.ButtonInstance;
import implementationSMA.agents.ImpressInstance;
import implementationSMA.agents.ServiceAgent;

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
			while (!ServiceAgent.getPile().isEmpty()){
				String strToDisplay = ServiceAgent.getPile().depiler();
				if (!strToDisplay.contains("display")){
					System.out.println("\t \t" + strToDisplay);
				} else {
					
				}
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
