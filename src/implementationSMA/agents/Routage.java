package implementationSMA.agents;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.irit.smac.libs.tooling.messaging.AgentMessaging;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.messaging.impl.Ref;
import implementationSMA.agents.InstanceAgents.InstanceAgent;
import implementationSMA.messages.AbstractMessage;

/**
 * 
 * @author amahamat
 * @param <T>
 *
 */
public class Routage extends Agent {
	/**
	 * This class receive the annonce of an instance agent and send to others
	 * Intances agents
	 */
	/**
	 * This parameter contains the annonce messages
	 */
	private IMsgBox<AbstractMessage> messageBox;
	private List<InstanceAgent> instanceAgentList;
	private Ref<AbstractMessage> refRoutage;
	private Set<AbstractMessage> messagesInBox;

	/**
	 * default constructor
	 */
	public Routage() {
		super();
		this.messageBox = (IMsgBox<AbstractMessage>) AgentMessaging.getMsgBox(
				id, AbstractMessage.class);
		this.refRoutage =  this.getMessageBox().getRef();
		this.instanceAgentList = new ArrayList<InstanceAgent>();
		this.messagesInBox = new HashSet<AbstractMessage>();
	}

	
	public Ref<AbstractMessage> getRefRoutage() {
		return refRoutage;
	}

	public IMsgBox<AbstractMessage> getMessageBox() {
		return messageBox;
	}
	/**
	 * this method allows the instance agent to add itself to the list of instance agents
	 * @param ia
	 */
	public void addInstanceAgent(InstanceAgent ia){
		this.instanceAgentList.add(ia);
	}

	@Override
	public void perceive() {
		// TODO Auto-generated method stub
		// get messages from messageBox
		this.messagesInBox = new HashSet<AbstractMessage>(
				messageBox.getMsgs());
		//get all instances agents which send it a message
		for (AbstractMessage am : this.messagesInBox){
			this.instanceAgentList.add(am.getInstanceAgent());
		}
	}

	@Override
	protected void decide() {
		// TODO Auto-generated method stub
		//decision is to send all messages in the box
	}

	@Override
	protected void act() {
		// TODO Auto-generated method stub
		//send all messages in the box 
		for (AbstractMessage am : this.messagesInBox){
			this.broadcast(am, am.getInstanceAgent());
		}

	}
	/**
	 * 
	 */
	public  void decideAndAct(){
		decide();
		act();
	}
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		this.removeallInstancesAgents();
		//may be not usefull to delete this class ?

	}

	/**
	 * This method allows to brodcast the annonce of Instance agent
	 * @param am 
	 * 
	 * @param iA
	 */
	private void broadcast(AbstractMessage am, InstanceAgent iA) {
		for (InstanceAgent ia : this.instanceAgentList){
			if (iA.getId() != ia.getId()){
				this.messageBox.send(am, ia.getRefInstanceAgent());
			}
		}
	}
	/**
	 * 
	 * @param instanceAgent
	 * @return
	 */
	public boolean remove(InstanceAgent instanceAgent){
		for (InstanceAgent ia : this.instanceAgentList){
			if (ia.getId().equals(instanceAgent.getId())){
				this.remove(ia);
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 */
	private void removeallInstancesAgents(){
		this.instanceAgentList.clear();
	}
	
}
