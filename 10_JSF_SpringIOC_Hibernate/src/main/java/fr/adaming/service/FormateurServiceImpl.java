package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IFormateurDao;
import fr.adaming.model.Formateur;

@Service("fService") // Annotation pour le d�finir comme un bean
@Transactional // Sp�cifie au conteneur Spring que toutes les m�thodes de la classe sont transactionnelles. La bonne pratique est de mettre cette annotation sur service
public class FormateurServiceImpl implements IFormateurService{

	@Autowired
	private IFormateurDao formateurDao;
	
	// Setter pour injection de d�pendance
	public void setFormateurDao(IFormateurDao formateurDao) {
		this.formateurDao = formateurDao;
	}

	@Override
	public Formateur isExist(Formateur f) {
		// TODO Auto-generated method stub
		return formateurDao.isExist(f);
	}

}
