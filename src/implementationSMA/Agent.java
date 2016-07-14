package implementationSMA;
import java.util.Set;

import fr.irit.smac.libs.tooling.scheduling.IAgentStrategy;

abstract public class Agent implements IAgentStrategy {
// Properties
	protected String id; //TODO peut être un id
	
//Accessors	
	protected String getId ()
	{
		return id;
	}
	
//Life Cycle
	
	/*
	 * 
	 */
	protected abstract void perceive();
	
	/*
	 * 
	 */
	protected abstract void decide ();
	
	/*
	 * 
	 */
	protected abstract void act ();

	/*
	 * 
	 */
	
	
	public abstract void delete();
	
//IAgentStrategy implementation
	public void nextStep(){
		System.out.println("nexteStep:" +this.getId() + " je suis executé");
 		perceive();
		decide();
		act();
	}
	

}
