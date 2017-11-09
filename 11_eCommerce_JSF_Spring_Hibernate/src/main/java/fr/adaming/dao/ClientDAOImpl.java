package fr.adaming.dao;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;

@Repository
public class ClientDAOImpl implements IClientDAO {

	@Autowired
	private SessionFactory sf;

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Client createClient(Client client) {
		Client outClient = clientExists(client);
		if (outClient == null) {
			sf.getCurrentSession().save(client);
			return client;
		} else {
			return null;
		}
	}

	@Override
	public Client clientExists(Client client) {
		// Cette fonction "récupère" le client par son mail et password
		
		//on ouvre une session
		Session session = sf.getCurrentSession();
		
		String req = "FROM Client c WHERE c.email=:pEmail AND c.password=:pPassword";
		
		//on crée une requete
		Query query = session.createQuery(req);
		
		query.setParameter("pEmail", client.getEmail());
		query.setParameter("pPassword",client.getPassword());
		Client outClient;
		try{
			outClient = (Client)query.uniqueResult();
		} catch (NonUniqueResultException ex) {
			// si il y a plus d'une entité dans le résultat (ne doit pas arriver...) on renvoie la première
			outClient = (Client) query.list().get(0);
		}
		return outClient;
	}

	@Override
	public Client getClient(Client client) {
		// Cette fonction ne récupère le client que par son ID
		
		Client outClient = (Client) sf.getCurrentSession().get(Client.class, client);
		
		return outClient;
	}

	@Override
	public Client updateClient(Client client) {
		// Cette fonction ne recupere le client que par son ID		
		if (getClient(client) != null) {
			
			sf.getCurrentSession().update(client);
			
			return client;
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteClient(Client client) {
		// Cette méthode récupère encore le client par son ID
		if (getClient(client) != null) {
			
			sf.getCurrentSession().delete(client);
			
			return true;
		} else {
			return false;
		}
	}

}
