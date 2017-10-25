package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IAgentDAO;
import fr.adaming.model.Agent;

@Stateful
public class AgentServiceImpl implements IAgentService{

	@EJB
	private IAgentDAO agentDAO;
	
	@Override
	public Agent createAgent(Agent agent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent agentExists(Agent agent) {
		return agentDAO.agentExists(agent);
	}

	@Override
	public Agent getAgent(Agent agent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent updateAgent(Agent agent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteClient(Agent agent) {
		// TODO Auto-generated method stub
		return false;
	}

}
