package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Client;

@Stateless
public class ClientDAOImpl implements IClientDAO{
	
	@PersistenceContext(unitName="PU")
	EntityManager em;

	@Override
	public Client createClient(Client client) {
		
		if(clientExists(client)!= null){
			em.persist(client);
			System.out.println("Client créé: "+client);
			return client;
		} else {
			return null;
		}
	}

	@Override
	public Client clientExists(Client client) {
		//Cette fonction "récupère" l'client par son mail et password
		String req = "SELECT c FROM Client c WHERE c.password=:pPassword AND c.email=:pEmail";
		
		//
		//System.out.println(client+" le client obtenu ");
		
		Query query = em.createQuery(req);
		query.setParameter("pEmail", client.getEmail());
		query.setParameter("pPassword", client.getPassword());
		
		Client outClient = (Client)query.getSingleResult();
		
		//System.out.println(outClient+" le client retourné ");
		
		if (outClient!=null){
			//System.out.println("Client existant: "+outClient);
			return outClient;
		} else{
			return null;
		}
	}

	@Override
	public Client getClient(Client client) {
		//Cette fonction ne récupère l'client que par son ID
		
		Client foundClient = em.find(Client.class, client.getIdClient());
		
		if (foundClient!=null){
			System.out.println("Client obtenu: "+foundClient);
			return foundClient;
		} else {
			return null;
		}
		
	}

	@Override
	public Client updateClient(Client client) {
		// Cette fonction ne recupere l'client que par son ID
		if(getClient(client)!=null){
			em.merge(client);
			System.out.println("Client modifié: "+client);
			return client;
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteClient(Client client) {
		//Cette méthode récupère encore l'client par son ID
		if(getClient(client)!=null){
			em.remove(client);
			System.out.println("Client supprimé: "+client);
			return true;
		}else{
			return false;
		}
	}

	
}
