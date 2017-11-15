package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneDeCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name="panierMB")
@SessionScoped
public class PanierManagedBean implements Serializable {

	@ManagedProperty(value="#{commService}")
	private ICommandeService commandeServ;
	
	@ManagedProperty(value="#{prodService}")
	private IProduitService produitServ;
	
	private Produit produit;
	
	private LigneDeCommande ligne;
	
	private Commande panier;
	
	private Client client;
	
	private HttpSession session ;
	
	@PostConstruct
	public void init(){
		// On obtient une session pour conserver le panier au cours de la navigation
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		// On cherche à obtenir le client actuel depuis la session, si il existe (ie session déjà créée par clientMB)
		client = (Client)session.getAttribute("sessionClient");
		
		this.produit = new Produit();
		
		// Si le client existe, le panier (Commande) lui est attribué
		if(client!=null){
			//la création de panier, qui est une Commande, ne passe pas par serv afin de ne pas l'enregistrer (pour le moment)
			panier = new Commande(new Date(), client);
		} else {
			// Si ce n'est pas le cas, Client vide qui sera revérifié en cas de requete d'enregistrement de commande
			panier = new Commande(new Date(), new Client());
		}
		//Le panier est enregistré dans la session
		session.setAttribute("panier", panier);
	}
	
	public ICommandeService getCommandeServ() {
		return commandeServ;
	}

	public void setCommandeServ(ICommandeService commandeServ) {
		this.commandeServ = commandeServ;
	}

	public IProduitService getProduitServ() {
		return produitServ;
	}

	public void setProduitServ(IProduitService produitServ) {
		this.produitServ = produitServ;
	}

	public Produit getProduit() {
		System.out.println("Le produit au getter" +produit);
		return produit;
	}

	public void setProduit(Produit produit) {
		System.out.println("Le produit au setter" +produit);
		this.produit = produit;
	}

	public Commande getPanier() {
		return panier;
	}

	public void setPanier(Commande panier) {
		this.panier = panier;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public LigneDeCommande getLigne() {
		return ligne;
	}

	public void setLigne(LigneDeCommande ligne) {
		this.ligne = ligne;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void ajouterProduit(ActionEvent event){
		// ajoute le produit donné dans un form, et vérifie si le produit est déjà dans le panier
		
		System.out.println("blablabla"+produit);
		Produit reqProduit = produitServ.getProduit(produit);
		System.out.println("Apres recuperation du produit "+reqProduit);
		if (reqProduit!=null){
			//on verifie si le produit est déjà présent, on part du principe que non(false)
			boolean produitPresent = false;
			//on cherche si une ligne contient le produit a rajouter, le nom ("designation") ET l'id ("idproduit") doivent correspondre.
			//normalement c'est le cas vu qu'on est passé par produitServ...
			LigneDeCommande ligneCommande = panier.getLigneByProduit(reqProduit);
			System.out.println("Apres recuperation de la ligne "+panier);
			//si on trouve une ligne qui correspond, on incremente la quantite et on met a jour le total
			if(ligneCommande!=null){
				ligneCommande.incrementerQuantite();
				ligneCommande.getTotal(); // Note: Commande.getTotal() existe: une méthode qui calcule le total des totaux, sans stocker le résultat.
				System.out.println("Apres incrementation de la ligne "+panier);
			}else{
				//sinon, on rajoute une nouvelle ligne
				panier.getListeLignes().add(new LigneDeCommande(1, reqProduit.getPrix(), reqProduit, panier));
				System.out.println("Apres création de la ligne "+panier);
			}
		//on renvoie le panier dans la session
		session.setAttribute("panier", panier);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ce produit n'existe pas!"));
		}	
	}
	
	public void incremeterLigne(){
		//Fonction qui incrémente le nombre de produits dans une ligne (à utiliser dans le panneau panier directement)
		//nécessite que le bouton renvoie la ligne vers ce MB (target : panierMB.ligne)
		
		//on vérifie que la ligne renvoyée n'est pas nulle, et go
		if(ligne!=null){
			panier.getLigneByProduit(ligne.getProduit()).incrementerQuantite();
			panier.getLigneByProduit(ligne.getProduit()).getTotal();
			
			session.setAttribute("panier", panier);
		}
		
	}
	
	public void decrementerLigne(){
		//Fonction qui réduit d'un le nombre de produits dans une ligne, avec supression si on arrive à 0
		//on vérifie que la ligne renvoyée n'est pas nulle, et go
		if(ligne!=null){
			System.out.println("Le panier avant "+panier);
			if(ligne.getQuantite()>1){
				panier.getLigneByProduit(ligne.getProduit()).decrementerQuantite();
				panier.getLigneByProduit(ligne.getProduit()).getTotal();
				
			} else {
				panier.getListeLignes().remove(ligne);
			}
			session.setAttribute("panier", panier);
			System.out.println("Le panier après "+panier);
		}
		
	}
	
	public void changerQuantiteLigne(){
		//Fonction au cas ou on voudrait changer directement le nombre de produits avec un field
	}
	
	public void supprimerLigne(){
		//Fonction pour supprimer carrément une ligne
	}
	
	public void enleverProduit(){ // A PRIORI PAS UTILISEE (remplacer par decrementer ligne ou supprimer ligne, plus logique)
		//meme chose que ajouter, mais à l'envers, avec différence si la ligne devient vide
		Produit reqProduit = produitServ.getProduit(produit);
		if (reqProduit!=null){
			boolean produitPresent = true; //Ici, on part du principe que le produit est bien dans le panier, mais on vérifie
			LigneDeCommande lignePanier = panier.getLigneByProduit(reqProduit);
			
			if(lignePanier!=null){
				lignePanier.decrementerQuantite();
				lignePanier.getTotal();
				if(lignePanier.getQuantite()==0){
					//si la quantite de la ligne est 0 après soustraction, on vire la ligne
					panier.getListeLignes().remove(lignePanier);
				}
			}
		session.setAttribute("panier", panier);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ce produit n'existe pas!"));
		}	
	}
	
	public void viderPanier(){
		//on vire tout
		if(panier.getListeLignes().size()!=0){
			panier.viderPanier();
		}
		session.setAttribute("panier", panier);
	}
	
	public String passerCommande(){
		//commande a faire dans managesbean séparé éventuellement, écrite en dessous sinon. Il faut juste une page intermédiare avec les infos de paiement une vérification de numéro de CB correct...
		client = (Client)session.getAttribute("sessionClient");
		if(client!=null){
			//redirection vers une page de confirmation, A CONDITION que le client soit connecté
			return "orderPage";
		} else {
			//si pas de client connecté, on redirige vers la homepage (ici accueil, à changer vers la nouvelle) pour que la connection soit faite
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inscription ou login nécessaire pour enregistrer une commande"));
			return "accueil";
		}
	}
	
	public String enregistrerCommande(){
		client = (Client)session.getAttribute("sessionClient");
		// on vérifie encore si le client est loggé
		if(client!=null){
			
			//on affecte le client connecté au panier, avant d'en faire une commande
			panier.setClient(client);
			
			//on cree la commande
			Commande outPanier = commandeServ.createCommande(panier);
			if (outPanier!=null){
				//si réussite de la création, on vide le panier et on renvoie à la page de récapitulatif des commandes
				panier = new Commande(new Date(), client);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Commande validée"));
				return "commandesClient";
			} else {
				//sinon, retour a la page de confirmation
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Commande échouée, veuillez rééssayer"));
				return "orderPage";
			}
		} else {
			return "accueil";
		}
	}
	
}
