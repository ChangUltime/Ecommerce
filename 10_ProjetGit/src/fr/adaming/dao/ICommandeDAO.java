package fr.adaming.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Local
public interface ICommandeDAO extends Serializable {

	public Commande createCommande(Commande commande);
	
	public Commande commandeExists(Commande commande);
	
	public Commande getCommande(Commande commande);
	
	public List<Commande> getCommandeByClient(Client client);
	
	public Commande updateCommande(Commande commande);
	
	public boolean deleteCommande(Commande commande);
}
