package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
		String req = "SELECT c FROM Commande c WHERE c.idCommande=:pIdCommande";
		
		Query query = em.createQuery(req);
		query.setParameter("pIdCommande", commande.getIdCommande());
		
		Commande outCommande = (Commande)query.getSingleResult();
		
		if(outCommande!=null){
			return outCommande;
		} else {
			return null;
		}
		
	}

	@Override
	public Commande getCommande(Commande commande) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Commande> getCommandeByClient(Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Commande updateCommande(Commande commande) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteCommande(Commande commande) {
		// TODO Auto-generated method stub
		return false;
	}

}
