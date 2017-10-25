package fr.adaming.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceContext;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;

@Local
public interface IClientDAO extends Serializable {
	
	public List<Client> getAllClients(Agent agent);
	
	public Client ajouterClient(Client client);
	
	public boolean supprimerClient(Client client);

	public List<Client> chercherClient(Client client);
	
	public Client modifierClient(Client client);
	
}
