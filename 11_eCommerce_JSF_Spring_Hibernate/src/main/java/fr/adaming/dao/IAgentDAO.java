package fr.adaming.dao;

import fr.adaming.model.Agent;

public interface IAgentDAO {

	public Agent createAgent(Agent agent);

	public Agent agentExists(Agent agent);

	public Agent getAgent(Agent agent);

	public Agent updateAgent(Agent agent);

	public boolean deleteClient(Agent agent);
}
