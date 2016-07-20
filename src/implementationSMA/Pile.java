package implementationSMA;
import java.util.Stack;

public class  Pile {

	private static Stack<String> displayThreadDecisions = new Stack<>();
	
	/**
	 * Emiler les affichages des agents dans pile d'une maniere synchronisée
	 * @param methodToDisplay
	 */
	public static synchronized void empiler(String methodToDisplay){
		Pile.displayThreadDecisions.push(methodToDisplay);
	}
	
	/**
	 * méthode permettant de depiler la pile contenant les affichages des agents
	 * @return
	 */
	public static synchronized String depiler(){
		if (!Pile.displayThreadDecisions.isEmpty()){
			return Pile.displayThreadDecisions.pop();
		}
		return null;
	}
	
	public static boolean isEmpty(){
		return Pile.displayThreadDecisions.isEmpty();
	}
}
