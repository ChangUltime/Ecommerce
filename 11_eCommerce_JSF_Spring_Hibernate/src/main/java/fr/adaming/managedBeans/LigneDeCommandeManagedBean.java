package fr.adaming.managedBeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneDeCommande;
import fr.adaming.model.Produit;

@ManagedBean(name="ldcMB")
@RequestScoped
public class LigneDeCommandeManagedBean implements Serializable{

	private LigneDeCommande ligneCommande;
	
	private Produit produit;
	
	private Commande panier;
	
	private Commande commande;
}
