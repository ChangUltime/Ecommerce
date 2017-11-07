package fr.adaming.service;

import javax.ejb.Local;

import fr.adaming.model.Agent;

@Local
public interface IAgentService {

	public Agent createAgent(Agent agent);
	
	public Agent agentExists(Agent agent);
	
	public Agent getAgent(Agent agent);
	
	public Agent updateAgent(Agent agent);

	public boolean deleteAgent(Agent agent);
	
}
