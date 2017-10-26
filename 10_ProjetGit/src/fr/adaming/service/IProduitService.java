package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

public interface IProduitService {
	public Produit addProduit(Produit produit, Categorie categorie);

	public Produit updateProduit(Produit produit, Categorie categorie);

	public boolean deleteProduit(Produit produit, Categorie categorie);

	public Produit getProduit(Produit produit);

	public List<Produit> getProduitSelected(Produit produit);

	public List<Produit> getProduitByKW(Produit produit);

	public List<Produit> getProductByCategorie(Categorie categorie);

	public List<Produit> getAllProduit();
}
