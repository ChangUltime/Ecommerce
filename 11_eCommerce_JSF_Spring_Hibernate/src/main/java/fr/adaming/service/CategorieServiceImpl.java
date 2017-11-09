package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Service("catService")
@Transactional
public class CategorieServiceImpl implements ICategorieService {

	@Autowired
	private ICategorieDao cateDao;

	public ICategorieDao getCateDao() {
		return cateDao;
	}

	public void setCateDao(ICategorieDao cateDao) {
		this.cateDao = cateDao;
	}

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
