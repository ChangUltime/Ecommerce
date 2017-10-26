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
		return prodDao.updateProduit(produit);
	}

	@Override
	public boolean deleteProduit(Produit produit, Categorie categorie) {
		return prodDao.deleteProduit(produit);
	}

	@Override
	public Produit getProduit(Produit produit) {
		return prodDao.getProduit(produit);
	}

	@Override
	public List<Produit> getProduitSelected() {
		return prodDao.getProduitSelected();
	}

	@Override
	public List<Produit> getProduitByKW(String[] keywords, boolean allKWs) {
		return prodDao.getProduitByKW(keywords, allKWs);
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
