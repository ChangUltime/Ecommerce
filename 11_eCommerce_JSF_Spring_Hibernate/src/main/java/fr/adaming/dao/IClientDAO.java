package fr.adaming.dao;

import fr.adaming.model.Client;

public interface IClientDAO {

	public Client createClient(Client client);

	public Client clientExists(Client client);

	public Client getClient(Client client);

	public Client updateClient(Client client);

	public boolean deleteClient(Client client);

}
