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

		Categorie cateUpdate = em.find(Categorie.class, categorie.getIdCategorie());
		System.out.println("-----------------------" + cateUpdate);
		try {
			if (!cateUpdate.equals(categorie)) {
				em.merge(categorie);
				return categorie;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public boolean deleteCategorie(Categorie categorie) {
		String req = "DELETE from Categorie cate WHERE cate.idCategorie=:pIdCat";

		Query query = em.createQuery(req);
		query.setParameter("pIdCat", categorie.getIdCategorie());

		int verif = query.executeUpdate();

		if (verif == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Categorie getCategorie(Categorie categorie) {
		String req = "select cat from Categorie cat where cat.idCategorie=:pIdCat";
		
		Query query = em.createQuery(req);
		query.setParameter("pIdCat", categorie.getIdCategorie());
		
		categorie = (Categorie) query.getSingleResult();
		System.out.println("------------------------- "+categorie);
		return categorie;
		
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
