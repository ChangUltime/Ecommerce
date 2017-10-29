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

import fr.adaming.model.Client;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;

@ManagedBean(name = "cMB")
@SessionScoped
public class ClientManagedBean implements Serializable {

	@EJB
	IClientService clientServ;
	
	@EJB
	ICommandeService commandeServ;
	
	private Client client;
	private boolean connected;

	private HttpSession session;

	@PostConstruct
	public void init() {
		client = new Client();
		connected=false;
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

	public String seConnecter() {
		// fonction de login du client, qui donne acces a la gestion du compte
		// personnel et des commandes

		FacesContext context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(true);

		// On verifie que le client existe, et on recupere sa description
		// complete (avec id)
		System.out.println("-----avant l'appel de clientserv-----");
		Client loginClient = clientServ.clientExists(client);
		System.out.println("-----après l'appel de clientserv-----");

		if (loginClient != null) {
			this.client = loginClient;
			session.setAttribute("sessionClient", this.client);
			connected = true;
			context.addMessage(null, new FacesMessage("Session de " + client.getNom() + " " + client.getPrenom()));
		} else {
			context.addMessage(null, new FacesMessage("Ce client n'existe pas, verifiez les identifiants"));
		}
		return "accueil";
	}

	public String seDeconnecter() {
		connected=false;
		session.invalidate();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Vous avez été déconnecté avec succès"));
		return "accueil?faces-redirect=true";
	}

	public String creerCompte() {
		// fonction de création de compte du client, à partir de ses
		// informations. éventuellement, seuls mail et password peuvent être
		// demandés pour commencer (option sign in/sign up)

		if (clientServ.clientExists(client) == null) {
			clientServ.createClient(client);
			return "accountInfo"; // renvoie vers l'ajout du reste des
									// informations. De là, l'annulation doit
									// etre possible, soit par simple
									// suppression, soit en ne créant pas le
									// compte à la ligne précédente mais à
									// l'aide d'une seconde option. ça demanderait de get la session meme sans connection d'un client. probablement nécessaire pour le panier de toute façon
		}
		return "accueil";
	}

}
