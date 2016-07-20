package implementationSMA;
import java.util.ArrayList;

import implementationSMA.agents.Agent;
import implementationSMA.agents.ServiceAgent;
import implementationSMA.messages.AbstractMessage;
import implementationSMA.messages.ContextAgentProposition;
import implementationSMA.messages.ServiceAgentMessage;

public class SAMsgBoxHistoryAgent extends Agent {
	private ServiceAgent myServiceAgent;
	private ArrayList<ContextAgentProposition> propositionsAC;
	private ArrayList<ServiceAgentMessage> saMessages;

	public SAMsgBoxHistoryAgent(ServiceAgent _myServiceAgent) {
		this.myServiceAgent = _myServiceAgent;
		this.propositionsAC = new ArrayList<ContextAgentProposition>();
		this.saMessages = new ArrayList<ServiceAgentMessage>();
	}

	public ServiceAgent getMyServiceAgent() {
		return myServiceAgent;
	}

	private void setMyServiceAgent(ServiceAgent myServiceAgent) {
		this.myServiceAgent = myServiceAgent;
	}

	public ArrayList<ContextAgentProposition> getPropositionsAC() {
		return propositionsAC;
	}

	private void setPropositionsAC(
			ArrayList<ContextAgentProposition> propositionsAC) {
		this.propositionsAC = propositionsAC;
	}

	public ArrayList<ServiceAgentMessage> getSaMessages() {
		//effectuer un cycle
		//this.nextStep();
		return saMessages;
	}

	private void setSaMessages(ArrayList<ServiceAgentMessage> saMessages) {
		this.saMessages = saMessages;
	}

	@Override
	public void perceive() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void decide() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void act() {
		ArrayList<AbstractMessage> abstractMessages = myServiceAgent
				.getMessages();
		Pair<ArrayList<ContextAgentProposition>, ArrayList<ServiceAgentMessage>> sortedMessages = AbstractMessage
				.sortAbstractMIntoCAPandSAM(abstractMessages);
		;

		propositionsAC = sortedMessages.getFirst();
		saMessages = sortedMessages.getSecond();

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	public void changePorpositionList(
			ArrayList<ContextAgentProposition> newArray) {
		this.propositionsAC = newArray;
	}

}
