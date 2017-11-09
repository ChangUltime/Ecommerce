package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAgentDAO;
import fr.adaming.model.Agent;

@Service("aService")
@Transactional
public class AgentServiceImpl implements IAgentService{

	@Autowired
	private IAgentDAO agentDAO;
	
	public IAgentDAO getAgentDAO() {
		return agentDAO;
	}

	public void setAgentDAO(IAgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	@Override
	public Agent createAgent(Agent agent) {
		return null;
	}

	@Override
	public Agent agentExists(Agent agent) {
		return agentDAO.agentExists(agent);
	}

	@Override
	public Agent getAgent(Agent agent) {
		return null;
	}

	@Override
	public Agent updateAgent(Agent agent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAgent(Agent agent) {
		return false;
	}

}
