package fr.adaming.dao;

import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Categorie;

@Stateless
public class CategorieDaoImpl implements ICategorieDao {

	@PersistenceContext(unitName = "PU")
	private EntityManager em;

	
	@Override
	public Categorie addCategorie(Categorie categorie) {
		em.persist(categorie);
		return categorie;
	}

	@Override
	public Categorie updateCategorie(Categorie categorie) {
		
		return null;
	}

	@Override
	public boolean deleteCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Categorie getCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Categorie> getAllCategorie() {
		// Construction de la requete
		String req = "SELECT cat FROM Categorie cat";

		Query query = em.createQuery(req);

		List<Categorie> listeCat = query.getResultList();

		return listeCat;

	}

}
