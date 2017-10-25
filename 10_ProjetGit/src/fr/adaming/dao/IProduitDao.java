package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

public interface IProduitDao {
	public Produit addProduit(Produit produit);
	public Produit updateProduit(Produit produit);
	public boolean deleteProduit(Produit produit);
	public Produit getProduit(Produit produit);
	public Produit getProduitSelected(Produit produit);
	public Produit getProduitByKW(Produit produit);
	public List<Produit> getProductByCategorie(Categorie categorie);
	public List<Produit> getAllProduit();
}
