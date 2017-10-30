package fr.adaming.managedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.service.IAgentService;

@ManagedBean(name="aMB")
@SessionScoped
public class AgentManagedBean implements Serializable {

	@EJB
	IAgentService agentServ;
	
	Agent agent;
	
	HttpSession session;
	
	@PostConstruct
	private void init(){
		agent=new Agent();
	}
	
	public AgentManagedBean() {
		super();
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String seConnecter(){
		// fonction de login de l'agent, qui donne acces a la gestion produits et categories
		FacesContext context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(false);
		
		//On verifie que l'agent existe, et on recupere sa description complete (avec id)
		Agent loginAgent = agentServ.agentExists(this.agent);
		
		if(loginAgent!=null){
			this.agent = loginAgent;
			session.setAttribute("sessionAgent", this.agent);
			context.addMessage(null, new FacesMessage("Session de "+agent.getMail()));
			return "homeAgent";
		}else {
			context.addMessage(null, new FacesMessage("Cet agent n'existe pas, verifiez les identifiants"));
			return "login";
		}
	}
	
	public String seDeconnecter(){
		session.invalidate();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Vous avez été déconnecté avec succès"));
		return "accueil?faces-redirect=true";
	}
}
