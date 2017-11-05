package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Local
public interface IProduitService {
	
	public Produit produitExists(Produit produit);
	
	public Produit addProduit(Produit produit, Categorie categorie);

	public Produit updateProduit(Produit produit, Categorie categorie);

	public boolean deleteProduit(Produit produit, Categorie categorie);

	public Produit getProduit(Produit produit);

	public List<Produit> getProduitSelected();

	public List<Produit> getProduitByKW(String[] keywords, boolean allKWs);

	public List<Produit> getProductByCategorie(Categorie categorie);

	public List<Produit> getAllProduit();
}
