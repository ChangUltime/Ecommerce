package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;
import fr.adaming.service.IClientService;
import jdk.nashorn.internal.runtime.Context;

@ManagedBean(name="cMB")
@Table(name="clients")
@RequestScoped
public class ClientManagedBean implements Serializable {
	
	@EJB
	IClientService clientServ;
	
	private List<Client> clientsList;
	
	private List<Client> searchList;
	
	private Client client;
	
	private HttpSession session;
	
	private Agent agent;

	public ClientManagedBean() {
		this.client = new Client();
		this.clientsList = (List<Client>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clientsList");
	}

	@PostConstruct//Sert à exécuter la méthode juste après l'instanciation du managedBean
	public void init(){
		//recuperation du contexte
		FacesContext context = FacesContext.getCurrentInstance();
		
		//recuperation de la session à partir du contexte
		this.session = (HttpSession)context.getExternalContext().getSession(false);
		
		//recuperation de l'agent a partir de la session
		this.agent = (Agent)this.session.getAttribute("sessionAgent");
	}
	public IClientService getClientServ() {
		return clientServ;
	}

	public void setClientServ(IClientService clientServ) {
		this.clientServ = clientServ;
	}

	public List<Client> getClientsList() {
		return clientsList;
	}

	public void setClientsList(List<Client> clientsList) {
		this.clientsList = clientsList;
	}

	public List<Client> getSearchList() {
		return searchList;
	}

	public void setSearchList(List<Client> searchList) {
		this.searchList = searchList;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public void ajouter(){
		
		Client clientOut = clientServ.ajouterClient(this.client,this.agent);
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientsList",clientServ.getAllClients(agent));
		//alternative avec simple getClientsList
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientsList",agent.getListeClients());
		if(clientOut.getId()!=0){
			session.setAttribute("clientsList",clientServ.getAllClients(agent));
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client ajouté: "+client));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun client ajouté"));
		}
		
		
		
	}
	
	public void supprimer(){
		
		boolean verif = clientServ.supprimerClient(client,agent);
		
		if(verif){
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientsList",clientServ.getAllClients(agent));
			//alternative avec simple getClientsList
			session.setAttribute("clientsList",clientServ.getAllClients(agent));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client supprimé"));
		} else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun client supprimé"));
		}

	}
	
	public void rechercher(){		
		searchList = clientServ.chercherClient(client,agent);
		
		if(searchList.get(0)!=null){
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientsList",clientServ.getAllClients(agent));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(searchList.size()+ " Client(s) trouvés"));
		} else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun client (accessible) trouvé"));
		}

	}

	public void modifier(){		
		client.setAgent(agent);
		
		
		
		if(clientServ.modifierClient(client, agent)==client){
			session.setAttribute("clientsList",clientServ.getAllClients(agent));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client modifié"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de client modifié"));
		}

	}
}
