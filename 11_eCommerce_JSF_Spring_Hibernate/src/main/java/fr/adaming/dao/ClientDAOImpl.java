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
			sf.openSession();
			sf.getCurrentSession().getTransaction().begin();
			
			sf.getCurrentSession().save(client);
			
			sf.getCurrentSession().getTransaction().commit();
			sf.getCurrentSession().close();
			return client;
		} else {
			return null;
		}
	}

	@Override
	public Client clientExists(Client client) {
		// Cette fonction "récupère" le client par son mail et password
		
		//on ouvre une session
		Session session = sf.openSession();
		
		String req = "FROM Client c WHERE c.email=:pEmail AND c.password=:pPassword";
		
		//on crée une requete
		Query query = session.createQuery(req);
		
		query.setParameter("pEmail", client.getEmail());
		query.setParameter("pPassword",client.getPassword());
		
		session.getTransaction().begin();
		try{
			Client outClient = (Client)query.uniqueResult();
			return outClient;
			
		} catch (NonUniqueResultException ex) {
			// si il y a plus d'une entité dans le résultat (ne doit pas arriver...) on renvoie la première
			return (Client) query.list().get(0);
		} finally {
			//on ferme la session, pas besoin de fermer sf a priori
			session.getTransaction().commit();
			session.close();
		}
	}

	@Override
	public Client getClient(Client client) {
		// Cette fonction ne récupère le client que par son ID
		sf.openSession();
		sf.getCurrentSession().getTransaction().begin();
		
		Client outClient = (Client) sf.getCurrentSession().get(Client.class, client);
		
		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();
		return outClient;
	}

	@Override
	public Client updateClient(Client client) {
		// Cette fonction ne recupere le client que par son ID		
		if (getClient(client) != null) {
			sf.openSession();
			sf.getCurrentSession().getTransaction().begin();
			
			sf.getCurrentSession().update(client);
			
			sf.getCurrentSession().getTransaction().commit();
			sf.getCurrentSession().close();
			return client;
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteClient(Client client) {
		// Cette méthode récupère encore le client par son ID
		if (getClient(client) != null) {
			sf.openSession();
			sf.getCurrentSession().getTransaction().begin();
			
			sf.getCurrentSession().delete(client);
			
			sf.getCurrentSession().getTransaction().commit();
			sf.getCurrentSession().close();
			return true;
		} else {
			return false;
		}
	}

}
