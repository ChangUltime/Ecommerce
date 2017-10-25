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

@ManagedBean(name="cMB")
@SessionScoped
public class ClientManagedBean implements Serializable {

	@EJB
	IClientService clientServ;
	
	Client client;
	
	HttpSession session;

	@PostConstruct
	public void init(){
		client = new Client();
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
	
	public String seConnecter(){
		// fonction de login du client, qui donne acces a la gestion du compte personnel et des commandes
		FacesContext context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(false);
		
		//On verifie que le client existe, et on recupere sa description complete (avec id)
		Client loginClient = clientServ.clientExists(client);
		
		if(loginClient!=null){
			this.client = loginClient;
			session.setAttribute("sessionClient", this.client);
			context.addMessage(null, new FacesMessage("Session de "+client.getNom()+" "+client.getPrenom()));
			return "commandes";
		}else {
			context.addMessage(null, new FacesMessage("Ce client n'existe pas, verifiez les identifiants"));
			return "login";
		}
	}
	
	public String seDeconnecter(){
		session.invalidate();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Vous avez été déconnecté avec succès"));
		return "accueil";
	}
	
}
