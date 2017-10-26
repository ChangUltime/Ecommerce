package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Stateful
public class ProduitServiceImpl implements IProduitService {

	@EJB
	private IProduitDao prodDao;

	@Override
	public Produit addProduit(Produit produit, Categorie categorie) {
		produit.setCategorie(categorie);
		return prodDao.addProduit(produit);
	}

	@Override
	public Produit updateProduit(Produit produit, Categorie categorie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteProduit(Produit produit, Categorie categorie) {
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

		return null;
	}

}
