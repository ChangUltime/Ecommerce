package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IClientDAO;
import fr.adaming.model.Agent;
import fr.adaming.model.Client;

@Stateful
public class ClientServiceImpl implements IClientService {

	@EJB
	private IClientDAO clientDAO;
	
	public IClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(IClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	@Override
	public List<Client> getAllClients(Agent agent) {
		return clientDAO.getAllClients(agent);
	}

	@Override
	public Client ajouterClient(Client client, Agent agent) {
		client.setAgent(agent);
		return clientDAO.ajouterClient(client);
		
	}

	@Override
	public boolean supprimerClient(Client client, Agent agent) {
		client.setAgent(agent);
		return clientDAO.supprimerClient(client);
	}

	@Override
	public List<Client> chercherClient(Client client, Agent agent) {
		client.setAgent(agent);
		return clientDAO.chercherClient(client);
	}

	@Override
	public Client modifierClient(Client client,Agent agent) {
		client.setAgent(agent);
		return clientDAO.modifierClient(client);
	}

}
