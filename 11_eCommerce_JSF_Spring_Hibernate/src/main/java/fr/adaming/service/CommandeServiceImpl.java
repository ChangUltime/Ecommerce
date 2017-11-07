package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.ICommandeDAO;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Stateful
public class CommandeServiceImpl implements ICommandeService {

	@EJB
	ICommandeDAO commDAO;
	
	@Override
	public Commande createCommande(Commande commande) {
		return commDAO.createCommande(commande);
	}

	@Override
	public Commande commandeExists(Commande commande) {
		return commDAO.commandeExists(commande);
	}

	@Override
	public Commande getCommande(Commande commande) {
		return commDAO.getCommande(commande);
	}

	@Override
	public List<Commande> getCommandeByClient(Client client) {
		return commDAO.getCommandeByClient(client);
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
