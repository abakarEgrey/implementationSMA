package implementationSMA.agents;
import java.util.Set;

import fr.irit.smac.libs.tooling.scheduling.IAgentStrategy;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;
import implementationSMA.Pile;

abstract public class Agent implements IAgentStrategy, ITwoStepsAgent {
// Properties
	
	
	protected String id; //TODO peut être un id
	
//Accessors	
	public String getId ()
	{
		return id;
	}
	
//Life Cycle
	
	/*
	 * 
	 */
	public abstract void perceive();
	
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
	
	/**
	 * 
	 */
	public  void decideAndAct(){
		decide();
		act();
	}
	
//IAgentStrategy implementation
	public void nextStep(){
		System.out.println("nexteStep:" +this.getId() + " je suis executé");
 		perceive();
 		decideAndAct();
		//decide();
		//act();
	}
	

}
