package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.PersistenceContext;

import fr.adaming.dao.IAgentDAO;
import fr.adaming.model.Agent;

@Stateful
public class AgentServiceImpl implements IAgentService {
	
	@EJB//cette annotation sert à injecter un ejb, ici l'ejb DAO
	private IAgentDAO agentDAO;

	public IAgentDAO getAgentDAO() {
		return agentDAO;
	}

	public void setAgentDAO(IAgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}
	
	public Agent exists(Agent agent) throws Exception{
		return agentDAO.exists(agent);
	}

	public Agent addAgent(Agent agent) throws Exception {
		return agentDAO.addAgent(agent);
	}
	

}
