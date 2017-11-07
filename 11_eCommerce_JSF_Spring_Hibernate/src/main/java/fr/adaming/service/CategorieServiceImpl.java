package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Stateful
public class CategorieServiceImpl implements ICategorieService {

	@EJB
	private ICategorieDao cateDao;

	@Override
	public Categorie addCategorie(Categorie categorie) {

		return cateDao.addCategorie(categorie);
	}

	@Override
	public Categorie updateCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return cateDao.updateCategorie(categorie);
	}

	@Override
	public boolean deleteCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return cateDao.deleteCategorie(categorie);
	}

	@Override
	public Categorie getCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return cateDao.getCategorie(categorie);
	}

	@Override
	public List<Categorie> getAllCategorie() {
		// TODO Auto-generated method stub
		return cateDao.getAllCategorie();
	}

}
