package fr.adaming.managedBeans;

import java.io.Serializable;
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
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;

@ManagedBean(name = "cMB")
@SessionScoped
public class ClientManagedBean implements Serializable {

	@ManagedProperty(value="#{cService}")
	IClientService clientServ;
	
	@ManagedProperty(value="#{commService}")
	ICommandeService commandeServ;
	
	private Client client;
	private boolean connected;

	private HttpSession session;

	private List<Commande> listeCommandes;

	@PostConstruct
	public void init() {
		client = new Client();
		connected=false;
		FacesContext context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(true);
	}

	public IClientService getClientServ() {
		return clientServ;
	}

	public void setClientServ(IClientService clientServ) {
		this.clientServ = clientServ;
	}

	public ICommandeService getCommandeServ() {
		return commandeServ;
	}

	public void setCommandeServ(ICommandeService commandeServ) {
		this.commandeServ = commandeServ;
	}

	public ClientManagedBean() {
		super();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public List<Commande> getListeCommandes() {
		return listeCommandes;
	}

	public void setListeCommandes(List<Commande> listeCommandes) {
		this.listeCommandes = listeCommandes;
	}

	public String seConnecter() {
		// fonction de login du client, qui donne acces a la gestion du compte
		// personnel et des commandes

		FacesContext context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(false);

		// On verifie que le client existe, et on recupere sa description
		// complete (avec id)
		System.out.println("-----avant l'appel de clientserv-----");
		Client loginClient = clientServ.clientExists(client);
		System.out.println("-----après l'appel de clientserv-----");

		if (loginClient != null) {
			this.client = loginClient;
			session.setAttribute("sessionClient", this.client);
			connected = true;
			
			listeCommandes = commandeServ.getCommandesByClient(client);
			client.setListeCommandes(listeCommandes);
			session.setAttribute("sessionClient", client);
			
			context.addMessage(null, new FacesMessage("Session de " + client.getNom() + " " + client.getPrenom()));
		} else {
			this.client = new Client();
			context.addMessage(null, new FacesMessage("Ce client n'existe pas, verifiez les identifiants"));
		}
		return "home";
	}

	public String seDeconnecter() {
		connected=false;
		session.invalidate();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Vous avez été déconnecté avec succès"));
		return "home?faces-redirect=true";
	}

	public String creerCompte() {
		// fonction de création de compte du client, à partir de ses
		// informations. éventuellement, seuls mail et password peuvent être
		// demandés pour commencer (option sign in/sign up)

		if (clientServ.clientExists(client) == null) {
			System.out.println("le client avant vérif "+client);
			this.client = clientServ.createClient(client);
			System.out.println("le client après vérif et création "+client);
			seConnecter();
			System.out.println("le client après connection "+client);
			return "modifClient"; // renvoie vers l'ajout du reste des
									// informations. De là, l'annulation doit
									// etre possible, soit par simple
									// suppression, soit en ne créant pas le
									// compte à la ligne précédente mais à
									// l'aide d'une seconde option. ça demanderait de get la session meme sans connection d'un client. probablement nécessaire pour le panier de toute façon
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ce client existe déjà"));
		}
		return "home";
	}
	
	public void updateClient(ActionEvent actionEvent){
		//on recupere la session mais ça sert peut-être à rien vu qu'on est en session scoped
		Client currentClient = (Client)session.getAttribute("sessionClient");
		// au cas ou la session ne comporterait pas d'agent?
		if (currentClient!=null){
			//on update par clientServ
			currentClient = clientServ.updateClient(client);
			// si ça réussit, on renvoie vers sessionClient
			if (currentClient!=null){
				session.setAttribute("sessionClient", currentClient);
				client=currentClient;
			}
		}
	}
	
	public String deleteClient(){
		Client currentClient = (Client)session.getAttribute("sessionClient");
		if(currentClient!=null){
			clientServ.deleteClient(currentClient);
			session.invalidate();
			return "home?faces-redirect=true";
		} else {
			return "home";
		}
		
		
	}
	
}
