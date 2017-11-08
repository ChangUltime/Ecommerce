package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.EtudiantDaoImpl;
import fr.adaming.dao.IEtudiantDao;
import fr.adaming.model.Etudiant;
import fr.adaming.model.Formateur;

@Service("eService")
@Transactional
public class EtudiantServiceImpl implements IEtudiantService{

	@Autowired
	private IEtudiantDao etudiantDao;
	
	// Setter pour l'injection de dépendences
	public void setEtudiantDao(IEtudiantDao etudiantDao) {
		this.etudiantDao = etudiantDao;
	}

	@Override
	public List<Etudiant> getAllEtudiants(Formateur f) {
		// TODO Auto-generated method stub
		return etudiantDao.getAllEtudiants(f);
	}

	@Override
	public Etudiant getEtudiant(Etudiant e) {
		// TODO Auto-generated method stub
		return etudiantDao.getEtudiant(e);
	}

	@Override
	public Etudiant addEtudiant(Etudiant e) {
		return etudiantDao.addEtudiant(e);
	}

	@Override
	public Etudiant updateEtudiant(Etudiant e) {
		return etudiantDao.updateEtudiant(e);
		
	}

	@Override
	public int deleteEtudiant(Etudiant e) {
		
		return etudiantDao.deleteEtudiant(e);
	}
	

}
