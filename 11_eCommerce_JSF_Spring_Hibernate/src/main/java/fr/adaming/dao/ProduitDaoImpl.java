package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao {

	@Autowired
	private SessionFactory sf;

	// Setter pour l'injection de dépendance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Produit produitExists(Produit produit) {
		Session s = sf.getCurrentSession();

		String req = "SELECT p FROM Produit p WHERE p.designation=:pDesignation";

		Query query = s.createQuery(req);
		query.setParameter("pDesignation", produit.getDesignation());

		Produit outProduit = (Produit) query.uniqueResult();

		return outProduit;
	}

	@Override
	public Produit addProduit(Produit produit) {
		Session s = sf.getCurrentSession();

		s.save(produit);

		return produit;
	}

	@Override
	public Produit updateProduit(Produit produit) {

		Session s = sf.getCurrentSession();

		s.update(produit);

		return produit;
	}

	@Override
	public boolean deleteProduit(Produit produit) {

		Session s = sf.getCurrentSession();

		String req = "DELETE from Produit prod WHERE prod.idProduit=:pIdProd";

		Query query = s.createQuery(req);
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
		Session s = sf.getCurrentSession();
		// on trouve le produit par son ID, puis on renvoie le rÃ©sultat, ou
		// null
		Produit outProduit = (Produit) s.get(Produit.class, produit.getIdProduit());

		return outProduit;
	}

	@Override
	public List<Produit> getProduitSelected() {
		Session s = sf.getCurrentSession();
		String req = "SELECT p FROM Produit p WHERE p.selectionne=:pSelect";

		Query query = s.createQuery(req);
		query.setParameter("pSelect", true);

		List<Produit> liste = query.list();

		return liste;

	}

	@Override
	public List<Produit> getProduitByKW(String[] keywords, boolean allKWs) {
		Session s = sf.getCurrentSession();

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
		Query query = s.createQuery(request);

		List<Produit> liste = query.list();

		if (liste.isEmpty() || liste.get(0).equals(null)) {
			return null;
		} else {
			return liste;
		}

	}

	@Override
	public List<Produit> getProductByCategorie(Categorie categorie) {
		Session s = sf.getCurrentSession();

		String req = "SELECT p FROM Produit p WHERE p.categorie.idCategorie=:pCategorie";

		Query query = s.createQuery(req);
		query.setParameter("pCategorie", categorie.getIdCategorie());

		List<Produit> liste = query.list();
		if (liste.isEmpty() || liste.get(0).equals(null)) {
			return null;
		} else {
			return liste;
		}
	}

	@Override
	public List<Produit> getAllProduit() {
		Session s = sf.getCurrentSession();

		String req = "SELECT p from Produit p";

		Query query = s.createQuery(req);

		List<Produit> liste = query.list();

		if (liste.isEmpty() || liste.get(0).equals(null)) {
			return null;
		} else {
			return liste;
		}
	}
}
