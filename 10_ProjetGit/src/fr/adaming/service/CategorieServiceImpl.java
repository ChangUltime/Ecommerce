package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
@Stateful
public class CategorieServiceImpl implements ICategorieService {

	@EJB
	private ICategorieDao catDao;
	
	@Override
	public Categorie addCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return catDao.addCategorie(categorie);
	}

	@Override
	public Categorie updateCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Categorie getCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Categorie> getAllCategorie() {
		// TODO Auto-generated method stub
		return catDao.getAllCategorie();
	}

}
