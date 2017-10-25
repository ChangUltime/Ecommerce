package fr.adaming.service;

import javax.ejb.EJB;

import fr.adaming.dao.IClientDAO;
import fr.adaming.model.Client;

public class ClientServiceImpl implements IClientService {

	@EJB
	IClientDAO clientDAO;
	
	@Override
	public Client clientExists(Client client) {
		return clientDAO.clientExists(client);
	}

}
