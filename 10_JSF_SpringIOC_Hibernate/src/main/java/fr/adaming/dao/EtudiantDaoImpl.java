package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Etudiant;
import fr.adaming.model.Formateur;

@Repository
public class EtudiantDaoImpl implements IEtudiantDao {

	@Autowired
	private SessionFactory sf;

	// Setter pour l'injection
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public List<Etudiant> getAllEtudiants(Formateur f) {
		Session s = sf.getCurrentSession();

		String req = "from Etudiant e where e.formateur.id=:pIdf";

		Query query = s.createQuery(req);

		query.setParameter("pIdf", f.getId());

		List<Etudiant> listeEtudiants = query.list();

		return listeEtudiants;
	}

	@Override
	public Etudiant getEtudiant(Etudiant e) {
		Session s = sf.getCurrentSession();

		String req = "from Etudiant e where e.id=:pId and e.formateur.id=:pIdf";

		Query query = s.createQuery(req);

		query.setParameter("pId", e.getId());
		query.setParameter("pIdf", e.getFormateur().getId());
		
		Etudiant etudiant = (Etudiant) query.uniqueResult();
		
		return etudiant;
	}

	@Override
	public Etudiant addEtudiant(Etudiant e) {
		Session s = sf.getCurrentSession();
		s.save(e);

		return e;
	}

	@Override
	public Etudiant updateEtudiant(Etudiant e) {
		Session s = sf.getCurrentSession();
		s.update(e);

//		Etudiant eOut = (Etudiant) s.get(Etudiant.class, e.getId());
//		
//		eOut.setNom(e.getNom());
//		eOut.setPrenom(e.getPrenom());
//		eOut.setDn(e.getDn());
//		
//		s.saveOrUpdate(eOut);
//		
//		return eOut;
		return e;
	}

	@Override
	public Etudiant deleteEtudiant(Etudiant e) {
		Session s = sf.getCurrentSession();

		// s.delete(e);
		String req = "delete from Etudiant e where e.id=:pId and e.formateur.id=:pIdf";

		Query query = s.createQuery(req);

		query.setParameter("pId", e.getId());
		query.setParameter("pIdf", e.getFormateur().getId());

		Etudiant eOut = (Etudiant) query.uniqueResult();

		return eOut;
	}

}
