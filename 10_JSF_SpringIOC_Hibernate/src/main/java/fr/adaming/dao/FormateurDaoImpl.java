package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Formateur;

@Repository // Annotation pour la déclaration du bean
public class FormateurDaoImpl implements IFormateurDao{

	// Injection automatique du collaborateur sessionFactoryBean
	@Autowired()
	private SessionFactory sf;
	
	// Le setter pour l'injection
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Formateur isExist(Formateur f) {
		// Récuperation de la session
		Session s = sf.getCurrentSession();
		
		// Requete HQL
		String req = "from Formateur f where f.mail=:pMail and f.password=:pPassword";
		
		// Créer un objet query
		Query query = s.createQuery(req);
		
		// Passage des parametres
		query.setParameter("pMail", f.getMail());
		query.setParameter("pPassword", f.getPassword());
		
		// Envoyer la requete et récuperer le resultat
		Formateur fOut = (Formateur) query.uniqueResult();
		
		return fOut;
	}

}
