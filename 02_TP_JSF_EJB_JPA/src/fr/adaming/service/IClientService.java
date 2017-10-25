package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;

@Local
public interface IClientService {
	
	public List<Client> getAllClients(Agent agent);
	
	public Client ajouterClient(Client client, Agent agent);
	
	public boolean supprimerClient(Client client, Agent agent);
	
	public List<Client> chercherClient(Client client,Agent agent);
	
	public Client modifierClient(Client client, Agent agent);

}
