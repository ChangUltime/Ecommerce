package fr.adaming.dao;

import javax.ejb.Local;

import fr.adaming.model.Agent;

@Local
public interface IAgentDAO {

	public Agent createAgent(Agent agent);
	
	public Agent agentExists(Agent agent);
	
	public Agent getAgent(Agent agent);
	
	public Agent updateAgent(Agent agent);
	
	public boolean deleteClient(Agent agent);
}
