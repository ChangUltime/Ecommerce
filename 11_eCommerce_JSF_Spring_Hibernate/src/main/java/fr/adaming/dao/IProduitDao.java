package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

public interface IProduitDao {

	public Produit produitExists(Produit produit);

	public Produit addProduit(Produit produit);

	public Produit updateProduit(Produit produit);

	public boolean deleteProduit(Produit produit);

	public Produit getProduit(Produit produit);

	public List<Produit> getProduitSelected();

	public List<Produit> getProduitByKW(String[] keywords, boolean allKWs);

	public List<Produit> getProductByCategorie(Categorie categorie);

	public List<Produit> getAllProduit();
}
