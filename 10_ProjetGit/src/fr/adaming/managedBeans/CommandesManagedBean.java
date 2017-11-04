package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.OneToMany;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneDeCommande;
import fr.adaming.service.ICommandeService;

@ManagedBean(name="commMB")
@ViewScoped
public class CommandesManagedBean implements Serializable {

	@EJB
	private ICommandeService commandeServ;
	
	private HttpSession session;
	
	private Client client;
	
	private Commande commande;
	
	private LigneDeCommande ligneCommande;
	
	private List<Commande> listeCommandes;
	
	@PostConstruct
	private void init(){
		
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null){
			this.client = (Client)session.getAttribute("sessionClient");
			listeCommandes = commandeServ.getCommandeByClient(client);
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		System.out.println("Commande obtenue dans commMB: "+commande);
		this.commande = commande;
	}

	public List<Commande> getListeCommandes() {
		return listeCommandes;
	}

	public void setListeCommandes(List<Commande> listeCommandes) {
		this.listeCommandes = listeCommandes;
	}
	
	public String annulerCommande(){
		Client client = (Client)session.getAttribute("sessionClient");
		if(client!=null){
			commande = commandeServ.getCommande(this.commande);
			if(commande!=null){
				boolean verif = commandeServ.deleteCommande(commande);// TODO rajouter éventuellement une limite de date côté service
				if(verif){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Commande annulée"));
					listeCommandes = commandeServ.getCommandeByClient(client);
					client.setListeCommandes(listeCommandes);
					session.setAttribute("sessionClient", client);
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Annulation impossible: la commande n'a pas pu être supprimée"));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Annulation impossible: la commande n'a pas pu être retrouvée"));
			}
			return "homeClient";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Annulation impossible: pas de session en cours"));
			return "accueil";
		}
	}
}
