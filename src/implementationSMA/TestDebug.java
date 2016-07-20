package implementationSMA;

import java.util.HashSet;

import com.irit.upnp.ContainerWComp;

import fr.irit.smac.libs.tooling.scheduling.IAgentStrategy;
import implementationSMA.agents.AgentsConnectionToUPnP;
import implementationSMA.agents.ButtonInstance;
import implementationSMA.agents.ImpressInstance;
import implementationSMA.agents.ServiceAgent;

public class TestDebug {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContainerWComp c = new ContainerWComp("Container1_Structural_0");

		AgentsConnectionToUPnP agentsConnectionToUPnP = new AgentsConnectionToUPnP();

		HashSet<ServiceAgent> buttonAgents = new HashSet<ServiceAgent>();
		
		ImpressInstance impress = new ImpressInstance("ImpressJ", null, buttonAgents, agentsConnectionToUPnP, c);

		HashSet<ServiceAgent> impressAgents = new HashSet<ServiceAgent>();
		impressAgents.addAll(impress.getServiceAgentList());

		ButtonInstance boutonPred = new ButtonInstance("@Button", null, "prevButton", impressAgents, agentsConnectionToUPnP,c);
		buttonAgents.addAll(boutonPred.getServiceAgentList());

		ButtonInstance boutonSuiv = new ButtonInstance("@Button1", null, "nextButton", impressAgents, agentsConnectionToUPnP, c);
		buttonAgents.addAll(boutonSuiv.getServiceAgentList());

		impress.setReceiverSAList(buttonAgents);

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
		
		System.out.println("/*==============debut de l'execution du step-2====================*/");
		// step-1: le boutonPred = B1 envoie une annonce aux 2 agents de
		// ImpressJ. il crée un agent contexte ACB1_1
		// Le bouton B2 et les interfaces I1 et I2 ne font rien

		//boutonPred.getServiceAgentList().get(0).nextStep();

		System.out.println("/*==============execution du step-2 terminé=======================*/");
		
		

	}

}
