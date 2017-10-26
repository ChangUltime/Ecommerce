package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Stateless
public class ProduitDaoImpl implements IProduitDao {

	@PersistenceContext(unitName="PU")
	private EntityManager em;
	
	@Override
	public Produit addProduit(Produit produit) {
		em.persist(produit);
		return produit;
	}

	@Override
	public Produit updateProduit(Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteProduit(Produit produit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Produit getProduit(Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getProduitSelected(Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getProduitByKW(Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getProductByCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getAllProduit() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
