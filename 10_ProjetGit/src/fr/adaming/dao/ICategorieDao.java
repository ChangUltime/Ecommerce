package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

public interface ICategorieDao {
	public Categorie addCategorie(Categorie categorie);
	public Categorie updateCategorie(Categorie categorie);
	public boolean deleteCategorie(Categorie categorie);
	public Categorie getCategorie(Categorie categorie);
	public List<Categorie> getAllCategorie();
}
