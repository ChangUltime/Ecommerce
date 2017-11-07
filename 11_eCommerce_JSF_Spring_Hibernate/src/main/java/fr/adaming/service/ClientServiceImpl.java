package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IClientDAO;
import fr.adaming.model.Client;

@Stateful
public class ClientServiceImpl implements IClientService {

	@EJB
	IClientDAO clientDAO;
	
	@Override
	public Client clientExists(Client client) {
		return clientDAO.clientExists(client);
	}

	@Override
	public Client createClient(Client client) {
		return clientDAO.createClient(client);
	}

	@Override
	public Client getClient(Client client) {
		return clientDAO.getClient(client);
	}

	@Override
	public Client updateClient(Client client) {
		return clientDAO.updateClient(client);
	}

	@Override
	public boolean deleteClient(Client client) {
		return clientDAO.deleteClient(client);
	}

}
