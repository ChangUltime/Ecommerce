package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICommandeDAO;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Service("commService")
@Transactional
public class CommandeServiceImpl implements ICommandeService {

	@Autowired
	private ICommandeDAO commDAO;

	@Override
	public Commande createCommande(Commande commande) {
		return commDAO.createCommande(commande);
	}

	@Override
	public Commande getCommande(Commande commande) {
		return commDAO.getCommande(commande);
	}

	@Override
	public List<Commande> getCommandesByClient(Client client) {
		return commDAO.getCommandesByClient(client);
	}

	@Override
	public Commande updateCommande(Commande commande) {
		return commDAO.updateCommande(commande);
	}

	@Override
	public boolean deleteCommande(Commande commande) {
		return commDAO.deleteCommande(commande);
	}

}
