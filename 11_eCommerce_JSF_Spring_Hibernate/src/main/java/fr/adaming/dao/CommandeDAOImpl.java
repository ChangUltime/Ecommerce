package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Repository
public class CommandeDAOImpl implements ICommandeDAO {

	@Autowired
	private SessionFactory sf;

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Commande createCommande(Commande commande) {
		sf.openSession();
		sf.getCurrentSession().getTransaction().begin();
		
		sf.getCurrentSession().save(commande);
		
		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();
		return commande;
	}

	@Override
	public Commande getCommande(Commande commande) {
		sf.openSession();
		sf.getCurrentSession().getTransaction().begin();
		
		Commande outCommande = (Commande) sf.getCurrentSession().get(Commande.class, commande);
		
		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();
		return outCommande;
	}

	@Override
	public List<Commande> getCommandesByClient(Client client) {
		sf.openSession();
		sf.getCurrentSession().getTransaction().begin();

		String req = "FROM Commande c WHERE c.client.idClient=:pIdClient";

		Query query = sf.getCurrentSession().createQuery(req);
		query.setParameter("pIdClient", client.getIdClient());

		List<Commande> results = (List<Commande>) query.list();

		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();
		if (results.size() > 0 && results.get(0) != null) {
			return results;
		} else
			return null;
	}

	@Override
	public Commande updateCommande(Commande commande) {
		if (getCommande(commande) != null) {
			sf.openSession();
			sf.getCurrentSession().getTransaction().begin();
			
			sf.getCurrentSession().update(commande);
			
			sf.getCurrentSession().getTransaction().commit();
			sf.getCurrentSession().close();
			return commande;
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteCommande(Commande commande) {
		if (getCommande(commande) != null) {
			sf.openSession();
			sf.getCurrentSession().getTransaction().begin();
			
			sf.getCurrentSession().delete(commande);
			
			sf.getCurrentSession().getTransaction().commit();
			sf.getCurrentSession().close();
			return true;
		} else {
			return false;
		}
	}

}
