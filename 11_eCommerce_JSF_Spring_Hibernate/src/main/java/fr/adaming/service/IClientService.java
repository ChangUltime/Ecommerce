package fr.adaming.service;

import fr.adaming.model.Client;

public interface IClientService {

	public Client clientExists(Client client);

	public Client createClient(Client client);

	public Client getClient(Client client);

	public Client updateClient(Client client);

	public boolean deleteClient(Client client);
}
