package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Categorie;

@Local
public interface ICategorieDao {
	public Categorie addCategorie(Categorie categorie);
	public Categorie updateCategorie(Categorie categorie);
	public boolean deleteCategorie(Categorie categorie);
	public Categorie getCategorie(Categorie categorie);
	public List<Categorie> getAllCategorie();
}
