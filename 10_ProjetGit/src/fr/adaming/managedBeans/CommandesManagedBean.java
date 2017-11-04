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

@ManagedBean(name = "commMB")
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
	private void init() {

		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			this.client = (Client) session.getAttribute("sessionClient");
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
		System.out.println("Commande obtenue dans commMB: " + commande);
		this.commande = commande;
	}

	public List<Commande> getListeCommandes() {
		return listeCommandes;
	}

	public void setListeCommandes(List<Commande> listeCommandes) {
		this.listeCommandes = listeCommandes;
	}

	public String annulerCommande() {
		// Cette méthode a pour but d'annuler une commande déjà exécutée (ie
		// stockée en base de donnée)

		// On obtient le client depuis la session (pour pouvoir utiliser get
		// commandeByClient)
		Client client = (Client) session.getAttribute("sessionClient");

		// Si le client récupéré n'est pas nul
		if (client != null) {
			// obtenir la commande actuelle depuis le serveur, à condition que
			// le formulaire accédant à ce managedBean a bien reçu une commande
			// et notamment son id (getCommande ne cherche que l'id)
			commande = commandeServ.getCommande(this.commande);
			// Si commandeServ a bien trouvé une commande ayant cet ID
			if (commande != null) {
				// on essaye de la supprimer, verif== true si c'est le cas
				boolean verif = commandeServ.deleteCommande(commande);
				// rajouter éventuellement une limite de date côté
				// service (ou plus logiquement, une colonne "terminee"
				// dans la classe/table commandes)
				
				// si on a bien supprimé quelque chose
				if (verif) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Commande annulée"));
					// on re-get la liste des commandes pour le client (après la
					// supression, donc)
					listeCommandes = commandeServ.getCommandeByClient(client);
					// on affecte la nouvelle liste au client
					client.setListeCommandes(listeCommandes);
					// on renvoie le client dans la session
					session.setAttribute("sessionClient", client);
				} else {
					// en cas d'echec de supression
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Annulation impossible: la commande n'a pas pu être supprimée"));
				}
			} else {
				// en cas d'erreur à l'entree de la commande à supprimer (pas la
				// bonne id)
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Annulation impossible: la commande n'a pas pu être retrouvée"));
			}
			// retour à home client, qui dans l'état actuel affichera la
			// "nouvelle" dernière commande. Si on doit le changer, il faudra
			// que le critere d'affichage sur cette page ne soit pas "derniere"
			// mais "non terminee". La méthode devra passer par commandeServ
			// plutot que par le client lui même comme c'est la cas actuellement
			// (getDerniereCommande dans Client remplacé par exemple par
			// getUnfinishedByClient(client) dans service et dao)
			return "homeClient";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					//pas de client dans la session, on sait pas comment mais on est arrivé sur homeclient sans avoir de session client...
					new FacesMessage("Annulation impossible: pas de session en cours"));
			return "accueil";
		}
	}
}
