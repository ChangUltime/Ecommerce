package fr.adaming.service;

import javax.ejb.Local;

import fr.adaming.model.Client;

@Local
public interface IClientService {

	public Client clientExists(Client client);
	
	public Client createClient(Client client);
	
	public Client getClient(Client client);
	
	public Client updateClient(Client client);
	
	public boolean deleteClient(Client client);
}
