package fr.adaming.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;

@Stateless
public class ClientDAOImpl implements IClientDAO {

	@PersistenceContext(unitName="PU_TP")
	private EntityManager em;
	
	@Override
	public List<Client> getAllClients(Agent agent) {
		
		//Ecriture de la requete
		String req = "SELECT c FROM Client c WHERE c.agent.id=:pAgentId";//agent_id=:pAgentId";
		
		Query query = em.createQuery(req);
		query.setParameter("pAgentId", agent.getId());
		
		List<Client> clientsList = (List<Client>)query.getResultList();
		
		return clientsList;
	}

	@Override
	public Client ajouterClient(Client client) {

		em.persist(client);
		
		return client;
		
	}

	@Override
	public boolean supprimerClient(Client client){

		String req = "DELETE from Client c WHERE c.agent.id=:pAgentId AND c.id=:pClientId";
		
		Query query = em.createQuery(req);
		query.setParameter("pAgentId", client.getAgent().getId());
		query.setParameter("pClientId", client.getId());
		
		int verif = query.executeUpdate();
		
		if(verif == 0){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<Client> chercherClient(Client client) {
		//trouver un seul client pour le moment, peut etre avec criteres plus tard
		
		List<Client> rechClients = new ArrayList<Client>();
		Client rechClient = em.find(Client.class, client.getId());
		if (rechClient!=null){
			if (rechClient.getAgent().getId()==client.getAgent().getId()){
				rechClients.add(rechClient);
			} else {
				rechClients.add(null);
			}
		}else {
			rechClients.add(null);
		}
		return rechClients;
	}

	@Override
	public Client modifierClient(Client client) {

		Client initClient = em.find(Client.class, client.getId());
		System.out.println(initClient);
		try {
			if(initClient.getAgent().getId()==client.getAgent().getId()){
				em.merge(client);
				System.out.println(client);
				return client;
			}
		} catch (Exception ex){
			return initClient;
		}
		return null;
	}

}
