package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Etudiant;
import fr.adaming.model.Formateur;

public interface IEtudiantService {

	public List<Etudiant> getAllEtudiants(Formateur f);
	public Etudiant getEtudiant(Etudiant e);
	public Etudiant addEtudiant(Etudiant e);
	public Etudiant updateEtudiant(Etudiant e);
	public int deleteEtudiant(Etudiant e);
}
