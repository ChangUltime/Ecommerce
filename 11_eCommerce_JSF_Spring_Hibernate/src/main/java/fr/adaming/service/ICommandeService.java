package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

public interface ICommandeService {

	public Commande createCommande(Commande commande);

	public Commande getCommande(Commande commande);

	public List<Commande> getCommandesByClient(Client client);

	public Commande updateCommande(Commande commande);

	public boolean deleteCommande(Commande commande);

}
