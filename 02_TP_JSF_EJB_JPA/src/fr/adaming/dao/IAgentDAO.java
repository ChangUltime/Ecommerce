package fr.adaming.dao;

import javax.ejb.Local;

import fr.adaming.model.Agent;

@Local
public interface IAgentDAO {
	
	public Agent exists(Agent agent) throws Exception;
	
	public Agent addAgent(Agent agent) throws Exception;

}
