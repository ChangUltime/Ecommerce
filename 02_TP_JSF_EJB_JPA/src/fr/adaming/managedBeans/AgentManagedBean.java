package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;
import fr.adaming.service.IAgentService;
import fr.adaming.service.IClientService;

@ManagedBean(name = "aMB")
@SessionScoped
public class AgentManagedBean implements Serializable {

	// transformation de l'association en java et injection de service
	@EJB
	private IAgentService agentService;
	@EJB
	private IClientService clientService;

	// Attributs utilisés dans la page
	private Agent agent;

	// Constructeur vide
	public AgentManagedBean() {
		this.agent = new Agent();// DOit etre instancié au départ, sinon target
									// unreachable
	}

	public IAgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	// m�thodes m�tier du managedBean
	public String seConnecter() {
		try {
			Agent agent_out = agentService.exists(agent);
			System.out.println(agent_out);
			
			//ajouter l'agent dans la session
			
			FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessionAgent", agent_out);
			
			//List<Client> clientsList = clientService.getAllClients(agent_out);
			
			//methode alternative qui utilise les managedBeans et le fetch de agent pour clientsList
			List<Client> clientsList = agent_out.getListeClients();
			
			//ajouter la liste de clients dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientsList", clientsList);
			
			
			return "succes";
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Identifiant ou mot de passe erronés"));
			return "echec";
		}
	}
	
	
	public String seDeconnecter() {
		//recuperation de la session de fermeture
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login";
		
	}
	
	
	public String ajouterAgent(){

			try {
				agentService.addAgent(agent);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'agent a été créé"));
				return "login";
			} catch (Exception e1) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cr�ation impossible"));
				e1.printStackTrace();
			}
		return "login";
	}

}
