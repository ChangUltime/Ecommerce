package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

public interface ICategorieDao {
	public Produit addCategorie(Categorie categorie);
	public Produit updateCategorie(Categorie categorie);
	public boolean deleteCategorie(Categorie categorie);
	public Produit getCategorie(Categorie categorie);
	public List<Produit> getAllCategorie();
}
