package implementationSMA.agents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.seamless.util.Iterators.Synchronized;

import com.irit.upnp.ContainerWComp;
import com.irit.upnp.ErrorContainer;
import com.irit.upnp.NoDevice;
import com.irit.upnp.NoService;
import com.irit.upnp.NotLaunched;

import fr.irit.smac.libs.tooling.scheduling.IAgentStrategy;
import implementationSMA.Pair;

public class AgentsConnectionToUPnP {

	private static Map<String, Set<ServiceAgent>> serviceAgentAndWCompBean = new HashMap<>();

	
	/**
	 * Cette méthode permet un accès synchronisé des agents services qui
	 * apparaissent au cours de l'exécution
	 * 
	 * @param sA
	 * @param wcompBean
	 */
	public synchronized void addServiceAgent(ServiceAgent sA, String wcompBean) {
		Set<ServiceAgent> tmpSet;
		if (AgentsConnectionToUPnP.serviceAgentAndWCompBean.containsKey(wcompBean)) {
			tmpSet = AgentsConnectionToUPnP.serviceAgentAndWCompBean.get(wcompBean);
			tmpSet.add(sA);
			AgentsConnectionToUPnP.serviceAgentAndWCompBean.put(wcompBean, tmpSet);
		} else {
			tmpSet = new HashSet<>();
			tmpSet.add(sA);
			AgentsConnectionToUPnP.serviceAgentAndWCompBean.put(wcompBean, tmpSet);
		}

	}

	/**
	 * Cette méthode permet la connection des composants de manière sysnchronisé
	 * Si le map ne contient pas l'un des agents services alors declenché une
	 * exception sinon effectuer la connection physique
	 * 
	 * @param sAInitiatePhysicCoonection
	 * @param sAAcceptingConnection
	 */
	public synchronized void doPhysicConnection(ServiceAgent sAInitiatePhysicCoonection,
			ServiceAgent sAAcceptingConnection, ContainerWComp container) {

		/*if ((!AgentsConnectionToUPnP.serviceAgentAndWCompBean.containsKey(sAAcceptingConnection))
				|| (!AgentsConnectionToUPnP.serviceAgentAndWCompBean.containsKey(sAAcceptingConnection))) {
			// declencher une exeception

		}*/
		Pair<Boolean, Pair<String, String>> resSAValideate = isServiceAgentValide(sAInitiatePhysicCoonection, sAAcceptingConnection);
		if(!resSAValideate.getFirst()){
			
			// declencher une exeception
		}
		else {
			/*String resSAInitiatePhysicConnection = AgentsConnectionToUPnP.serviceAgentAndWCompBean
					.get(sAAcceptingConnection);
			Map<String, Object> infoBeanSAInitiateConnection = container.getBeanCreationIdAndMap()
					.get(resSAInitiatePhysicConnection);*/
			
			String beanNameSource = resSAValideate.getSecond().getFirst();

			/*String resSAAcceptingConnection = AgentsConnectionToUPnP.serviceAgentAndWCompBean
					.get(sAAcceptingConnection);
			Map<String, Object> infoBeanSAAcceptingConnection = container.getBeanCreationIdAndMap()
					.get(resSAAcceptingConnection);*/
			String beanNameDestination =resSAValideate.getSecond().getSecond();

			/**
			 * effectuer la connection physique
			 */
			try {
				String link = container.createLink(beanNameSource, sAInitiatePhysicCoonection.getEvent(), beanNameDestination,
						sAInitiatePhysicCoonection.getDstAction(), "");
			} catch (NoDevice | ErrorContainer | NoService | NotLaunched e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * Cette methode permet de tester si tous les 2 agents services sont valides
	 * c'est -à dire contenus dans la map. si c'est le cas, alors renvoie true
	 * avec les noms des beans. sinon false avec les noms des beans null
	 * 
	 * @param sAInitiate
	 * @param sAAccept
	 * @return
	 */
	private Pair<Boolean, Pair<String, String>> isServiceAgentValide(ServiceAgent sAInitiate, ServiceAgent sAAccept) {
		Pair<Boolean, Pair<String, String>> res = new Pair<Boolean, Pair<String, String>>(false, null);
		Set<String> wcompBeans = AgentsConnectionToUPnP.serviceAgentAndWCompBean.keySet();
		String sAInitiateBean = "";
		String sAAcceptBean = "";

		for (String wB : wcompBeans) {
			Set<ServiceAgent> setSA = AgentsConnectionToUPnP.serviceAgentAndWCompBean.get(wB);

			if (setSA.contains(sAInitiate)) {
				sAInitiateBean = wB;
			}

			if (setSA.contains(sAAccept)) {
				sAAcceptBean = wB;
			}
		}

		if ((sAInitiateBean != null) && (sAAcceptBean != null)) {
			Pair<String, String> strBeans = new Pair<String, String>(sAInitiateBean, sAAcceptBean);
			res.setFirst(true);
			res.setSecond(strBeans);
		}

		return res;
	}

	public static Map<String, Set<ServiceAgent>> getServiceAgentAndWCompBean() {
		return serviceAgentAndWCompBean;
	}
	
	

}
