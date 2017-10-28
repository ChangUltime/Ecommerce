package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Stateless
public class CommandeDAOImpl implements ICommandeDAO {

	@PersistenceContext(unitName="PU")
	private EntityManager em;
	
	@Override
	public Commande createCommande(Commande commande) {
		
		if(commandeExists(commande)!=null){
			em.persist(commande);
			return commande;
		} else {
			return null;
		}
	}

	@Override
	public Commande commandeExists(Commande commande) {
		// a revoir eventuellement, peut etre pas tres utile en plus de getcommande
		String req = "SELECT c FROM Commande c WHERE c.idCommande=:pIdCommande";
		
		Query query = em.createQuery(req);
		query.setParameter("pIdCommande", commande.getIdCommande());
		try{
			return (Commande)query.getSingleResult();
		} catch (NoResultException nrex) {
			return null;
		}
		
	}

	@Override
	public Commande getCommande(Commande commande) {
		Commande foundCommande = em.find(Commande.class, commande.getIdCommande());
		
		if (foundCommande!=null){
			return foundCommande;
		} else {
			return null;
		}
	}

	@Override
	public List<Commande> getCommandeByClient(Client client) {

		String req = "SELECT c from Commande c WHERE c.client.idClient=:pIdClient";
		
		Query query = em.createQuery(req);
		query.setParameter("pIdClient", client.getIdClient());
		
		List<Commande> results = (List<Commande>)query.getResultList();
		
		if(results.size()>0 && results.get(0)!=null){
			return results;
		} else return null;
	}

	@Override
	public Commande updateCommande(Commande commande) {
		if(getCommande(commande)!=null){
			em.merge(commande);
			return commande;
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteCommande(Commande commande) {
		if(getCommande(commande)!=null){
			em.detach(commande);
			return true;
		} else {
			return false;
		}
	}

}
