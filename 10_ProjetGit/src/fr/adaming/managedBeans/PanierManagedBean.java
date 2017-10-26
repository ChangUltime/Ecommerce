package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.dao.ICommandeDAO;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneDeCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean
@SessionScoped
public class PanierManagedBean implements Serializable {

	
	@EJB
	private ICommandeService commandeServ;
	
	@EJB
	IProduitService produitServ;
	
	private Produit produit;
	
	private Commande panier;
	
	HttpSession session ;
	
	@PostConstruct
	public void init(){
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
	
	public void ajouterProduit(){

		Produit reqProduit = produitServ.getProduit(produit);
		if (reqProduit!=null){
			boolean produitPresent = false;
			List<Produit> listeP = panier.getListProduits();
			
			LigneDeCommande lignePanier = panier.getLigneByProduit(reqProduit);
			
			if(lignePanier!=null){
				lignePanier.incrementerQuantite();
				lignePanier.calculerTotal();
			}else{
				panier.getListeLignes().add(new LigneDeCommande(1, reqProduit.getPrix(), reqProduit, panier));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ce produit n'existe pas!"));
		}	
	}
	
	public void enleverProduit(){

		Produit reqProduit = produitServ.getProduit(produit);
		if (reqProduit!=null){
			boolean produitPresent = true;
			List<Produit> listeP = panier.getListProduits();
			
			LigneDeCommande lignePanier = panier.getLigneByProduit(reqProduit);
			
			if(lignePanier!=null){
				lignePanier.decrementerQuantite();
				lignePanier.calculerTotal();
				if(lignePanier.getQuantite()==0){
					listeP.remove(lignePanier);
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ce produit n'existe pas!"));
		}	
	}
	
}
