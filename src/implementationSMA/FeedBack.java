package implementationSMA;

import implementationSMA.messages.ContextAgentProposition;

public class FeedBack {
	//this value is 0 or 1
	private int feedBackValue;
	private  ContextAgentProposition cAP;
	
	public FeedBack(int feedBackValue, ContextAgentProposition cAP) {
		this.feedBackValue = feedBackValue;
		this.cAP = cAP;
	}

	public int getFeedBackValue() {
		return feedBackValue;
	}

	public ContextAgentProposition getcAP() {
		return cAP;
	}
	
}
