package fr.adaming.dao;

import javax.ejb.Local;

import fr.adaming.model.Client;

@Local
public interface IClientDAO {

	public Client createClient(Client client);
	
	public Client clientExists(Client client);
	
	public Client getClient(Client client);
	
	public Client updateClient(Client client);
	
	public boolean deleteClient(Client client);
	
}
