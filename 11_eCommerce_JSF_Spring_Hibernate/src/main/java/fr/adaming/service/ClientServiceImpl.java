package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDAO;
import fr.adaming.model.Client;

@Service("cService")
@Transactional
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDAO clientDAO;

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
