package fr.adaming.service;

import javax.ejb.Local;

import fr.adaming.model.Agent;

@Local
public interface IAgentService {

	public Agent exists(Agent agent) throws Exception;
	
	public Agent addAgent(Agent agent) throws Exception;
}
