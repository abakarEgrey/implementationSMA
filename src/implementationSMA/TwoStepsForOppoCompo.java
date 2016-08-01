package implementationSMA;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.irit.smac.libs.tooling.scheduling.IAgentStrategy;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.BadStepRuntimeException;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;
import fr.irit.smac.libs.tooling.scheduling.impl.system.AbstractSystemStrategy;
import fr.irit.smac.libs.tooling.scheduling.impl.system.SynchronizedSystemStrategy;
import implementationSMA.agents.ServiceAgents.ServiceAgent;

public class TwoStepsForOppoCompo extends AbstractSystemStrategy<ITwoStepsAgent> {

	/**
	 * The Enum EState.
	 */
	private enum EState {

		/** The step perceive. */
		PERCEIVE,
		/** The step decideAndAct. */
		DECIDE_ACT
	}

	/** The current state. */
	private volatile EState currentState = EState.PERCEIVE;

	private static final Logger LOGGER = Logger.getLogger(TwoStepsForOppoCompo.class.getName());

	/**
	 * The Class AgentWrapper. Defines the next step of an agent.
	 */
	protected class AgentWrapper implements IAgentStrategy {

		/** The agent. */
		private final ITwoStepsAgent agent;

		/**
		 * Instantiates a new agent wrapper.
		 *
		 * @param agent
		 *            the agent
		 */
		public AgentWrapper(ITwoStepsAgent agent) {
			this.agent = agent;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.irit.smac.libs.tooling.scheduling.IAgentStrategy#nextStep()
		 */
		@Override
		public void nextStep() {

			switch (TwoStepsForOppoCompo.this.currentState) {
			case PERCEIVE:
				agent.perceive();
				break;

			case DECIDE_ACT:
				agent.decideAndAct();
				break;

			default:
				throw new BadStepRuntimeException("case not covered");
			}

		}

	}

	/** The agent wrappers. */
	private final Map<ITwoStepsAgent, AgentWrapper> agentWrappers = new ConcurrentHashMap<ITwoStepsAgent, AgentWrapper>();

	/** The pending added agents. */
	// NOTE: BlockingQueue implem is thread-safe
	private final BlockingQueue<ITwoStepsAgent> pendingAddedAgents = new LinkedBlockingDeque<ITwoStepsAgent>();

	/** The pending removed agents. */
	private final BlockingQueue<ITwoStepsAgent> pendingRemovedAgents = new LinkedBlockingDeque<ITwoStepsAgent>();

	/** The internal system strategy. */
	private final AbstractSystemStrategy<IAgentStrategy> internalSystemStrategy;

	/**
     * Instantiates a new two steps system strategy.
     *
     * @param agents
     *            the agents
     * @param agentExecutor
     *            the agent executor
     */
    public TwoStepsForOppoCompo(Collection<ITwoStepsAgent> agents,
        ExecutorService agentExecutor) {
        super(agentExecutor);

        internalSystemStrategy = new SynchronizedSystemStrategy(
            new LinkedHashSet<IAgentStrategy>(), agentExecutor);
        this.addAgents(agents);

        // replace inherited shutdownRunnable by its own
        final Runnable inheritedShutdownRunnable = shutdownRunnable;
        shutdownRunnable = new Runnable() {

            @Override
            public void run() {
                // shutdown internalSystemStrategy
                // NOTE: since internalSystemStrategy is only accessed by
                // ourself on a step-by-step basis, it is guaranteed that its
                // execution queue will be empty by the time we reach
                // shutdown(). Meaning that no lingering agent execution task
                // will be present in its queue.
                // Consequently we do not need to block to satisfy the interface
                // contract that now agent will be executed after shutdown() has
                // returned.
                internalSystemStrategy.shutdown();

                // propagate to inherited shutdown task
                inheritedShutdownRunnable.run();
            }
        };
    }

	/**
     * Instantiates a new two steps system strategy.
     *
     * @param agents
     *            the agents
     */
    public TwoStepsForOppoCompo(Collection<ITwoStepsAgent> agents) {
        // setting a "reasonable" default size for the thread pool of 2xNbCores
        // (according to e.g.
        // http://codeidol.com/java/java-concurrency/Applying-Thread-Pools/Sizing-Thread-Pools/)
        // user should probably override it to a more suitable value
        this(agents, Executors.newFixedThreadPool(Runtime.getRuntime()
            .availableProcessors() * 2));

    }

	/**
	 * Adds the pending agents. This method is called when a step is run.
	 */
	private void addPendingAgents() {

		Collection<ITwoStepsAgent> drainedAgents = new HashSet<ITwoStepsAgent>();
		pendingAddedAgents.drainTo(drainedAgents);

		for (ITwoStepsAgent agent : drainedAgents) {
			AgentWrapper wrapper = new AgentWrapper(agent);
			agentWrappers.put(agent, wrapper);
			internalSystemStrategy.addAgent(wrapper);
		}

	}
	
	 /**
     * Removes the pending agents.
     * This method is called when a step is finished.
     */
    private void removePendingAgents() {
        Collection<ITwoStepsAgent> drainedAgents = new HashSet<ITwoStepsAgent>();
        pendingRemovedAgents.drainTo(drainedAgents);

        for (ITwoStepsAgent agent : drainedAgents) {
            internalSystemStrategy.removeAgent(agentWrappers.get(agent));
            agentWrappers.remove(agent);

        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.irit.smac.libs.tooling.scheduling.impl.system.AbstractSystemStrategy
     * #doStep()
     */
    @Override
    protected void doStep() {

        addPendingAgents();

        // start the perceive of the agents and block
        currentState = EState.PERCEIVE;
        try {
            internalSystemStrategy.step().get();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.INFO, e.getMessage(), e);
        }
        catch (ExecutionException e) {
            LOGGER.log(Level.INFO, e.getMessage(), e);
        }

        // start the decide-and-act of the agents
        currentState = EState.DECIDE_ACT;
        try {
            internalSystemStrategy.step().get();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.INFO, e.getMessage(), e);
        }
        catch (ExecutionException e) {
            LOGGER.log(Level.INFO, e.getMessage(), e);
        }

        removePendingAgents();

    }
    
    /**
     * Executor Handling.
     *
     * @return the executor service
     */

    @Override
    public ExecutorService getExecutorService() {
        return internalSystemStrategy.getExecutorService();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.irit.smac.libs.tooling.scheduling.impl.system.AbstractSystemStrategy
     * #setExecutorService(java.util.concurrent.ExecutorService)
     */
    @Override
    public void setExecutorService(ExecutorService executor) {
        internalSystemStrategy.setExecutorService(executor);
    }

    /**
     * Agent Handling.
     * Add an agent to the system.
     * 
     * @param agent
     *            the agent
     */

    @Override
    public void addAgent(final ITwoStepsAgent agent) {
        if (!this.agents.contains(agent)) {
            super.addAgent(agent);
            pendingAddedAgents.add(agent);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.irit.smac.libs.tooling.scheduling.impl.system.AbstractSystemStrategy
     * #removeAgent(java.lang.Object)
     */
    @Override
    public void removeAgent(ITwoStepsAgent agent) {
        if (this.agents.contains(agent)) {
            super.removeAgent(agent);
            pendingRemovedAgents.add(agent);

        }
    }
    
    public void setAgents(Collection<ITwoStepsAgent> agents){
    	this.agents = (Set<ITwoStepsAgent>) agents;
    }
    
    public void removeAllAgents(ArrayList<ServiceAgent> arrayList){
    	for (ITwoStepsAgent itsa : arrayList){
    		this.removeAgent(itsa);
    	}
    }

}
