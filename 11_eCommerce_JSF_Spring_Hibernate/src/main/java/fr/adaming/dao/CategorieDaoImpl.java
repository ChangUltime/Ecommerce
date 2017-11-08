package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;

@Repository
public class CategorieDaoImpl implements ICategorieDao {

	@Autowired
	private SessionFactory sf;

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Categorie addCategorie(Categorie categorie) {
		Session s = sf.getCurrentSession();
		
		s.save(categorie);
		
		return categorie;
	}

	@Override
	public Categorie updateCategorie(Categorie categorie) {
		Session s = sf.getCurrentSession();
		
		s.update(categorie);
		
		return categorie;
	}

	@Override
	public boolean deleteCategorie(Categorie categorie) {
		Session s = sf.getCurrentSession();
		
		String req = "DELETE from Categorie cate WHERE cate.idCategorie=:pIdCat";

		Query query = s.createQuery(req);
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
		Session s = sf.getCurrentSession();
		
		String req = "select cat from Categorie cat where cat.idCategorie=:pIdCat";
		
		Query query = s.createQuery(req);
		query.setParameter("pIdCat", categorie.getIdCategorie());
		
		categorie = (Categorie) query.uniqueResult();
		return categorie;
		
	}

	@Override
	public List<Categorie> getAllCategorie() {
		Session s = sf.getCurrentSession();
		
		// Construction de la requete
		String req = "SELECT cat FROM Categorie cat";

		Query query = s.createQuery(req);

		List<Categorie> listeCat = query.list();

		return listeCat;

	}

}
