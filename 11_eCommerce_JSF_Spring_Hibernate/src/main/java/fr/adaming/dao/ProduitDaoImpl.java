package fr.adaming.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao {

	@PersistenceContext(unitName = "PU")
	private EntityManager em;

	@Override
	public Produit produitExists(Produit produit) {
		String req = "SELECT p FROM Produit p WHERE p.designation=:pDesignation";

		Query query = em.createQuery(req);
		query.setParameter("pDesignation", produit.getDesignation());

		Produit outProduit = (Produit) query.getSingleResult();

		if (outProduit != null) {
			return outProduit;
		} else {
			return null;
		}
	}

	@Override
	public Produit addProduit(Produit produit) {
		em.persist(produit);
		return produit;

		// Cette méthode renvoie soit le produit créé, soit un produit déjà
		// existant avec la même désignation
		// Produit checkProduit = produitExists(produit);
		// System.out.println("----------------------" +checkProduit);
		// if (checkProduit!=null) {
		// return checkProduit;
		// } else {
		// em.persist(produit);
		// return produit;
		// }
	}

	@Override
	public Produit updateProduit(Produit produit) {
		// la fonction vérifie que le produit existant est bien différent de
		// celui donné
		// retour de null si la mise à jour n'a pas eu lieu car le produit
		// n'est pas différent
		Produit prodUpdate = em.find(Produit.class, produit.getIdProduit());

		if (!prodUpdate.equals(produit)) {
			em.merge(produit);
			return produit;
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteProduit(Produit produit) {
		String req = "DELETE from Produit prod WHERE prod.idProduit=:pIdProd";

		Query query = em.createQuery(req);
		query.setParameter("pIdProd", produit.getIdProduit());

		int verif = query.executeUpdate();

		if (verif == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Produit getProduit(Produit produit) {
		// on trouve le produit par son ID, puis on renvoie le résultat, ou
		// null
		Produit outProduit = em.find(Produit.class, produit.getIdProduit());
		if (!outProduit.equals(null)) {
			return outProduit;
		} else {
			return null;
		}
	}

	@Override
	public List<Produit> getProduitSelected() {
		String request = "SELECT p FROM Produit p WHERE p.selectionne=:pSelect";

		Query query = em.createQuery(request);
		query.setParameter("pSelect", true);

		List<Produit> result = (List<Produit>) query.getResultList();
		if (result.isEmpty() || result.get(0).equals(null)) {
			return null;
		} else {
			return result;
		}
	}

	@Override
	public List<Produit> getProduitByKW(String[] keywords, boolean allKWs) {

		StringBuilder keywordsPart = new StringBuilder();
		String connector;
		if (allKWs) {
			connector = " AND ";
		} else {
			connector = " OR ";
		}

		for (int index = 0; index < keywords.length; index++) {
			if (index == 0) {
				keywordsPart.append("LOWER(p.description) LIKE LOWER('%" + keywords[index] + "%')");
			} else {
				keywordsPart.append(connector + "LOWER(p.description) LIKE LOWER('%" + keywords[index] + "%')");
			}
		}

		String request = "SELECT p from Produit p WHERE " + keywordsPart.toString();
		Query query = em.createQuery(request);

		List<Produit> result = (List<Produit>) query.getResultList();

		if (result.isEmpty() || result.get(0).equals(null)) {
			return null;
		} else {
			return result;
		}

	}

	@Override
	public List<Produit> getProductByCategorie(Categorie categorie) {
		String request = "SELECT p FROM Produit p WHERE p.categorie.idCategorie=:pCategorie";

		Query query = em.createQuery(request);
		query.setParameter("pCategorie", categorie.getIdCategorie());

		List<Produit> result = (List<Produit>) query.getResultList();
		if (result.isEmpty() || result.get(0).equals(null)) {
			return null;
		} else {
			return result;
		}
	}

	@Override
	public List<Produit> getAllProduit() {
		String request = "SELECT p from Produit p";

		Query query = em.createQuery(request);

		List<Produit> result = (List<Produit>) query.getResultList();

		if (result.isEmpty() || result.get(0).equals(null)) {
			return null;
		} else {
			return result;
		}
	}

}
