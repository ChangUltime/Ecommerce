package fr.adaming.managedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.service.IAgentService;

@ManagedBean(name="aMB")
@SessionScoped
public class AgentManagedBean implements Serializable {

	@ManagedProperty(value="#{aService}")
	private IAgentService agentServ;
	
	private Agent agent;
	
	private HttpSession session;
	
	private boolean connected;
	
	@PostConstruct
	private void init(){
		this.agent=new Agent();
		this.connected = false;
	}
	
	public IAgentService getAgentServ() {
		return agentServ;
	}

	public void setAgentServ(IAgentService agentServ) {
		this.agentServ = agentServ;
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

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
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
			setConnected(true);
			return "homeAgent";
		}else {
			context.addMessage(null, new FacesMessage("Cet agent n'existe pas, verifiez les identifiants"));
			return "home";
		}
	}
	
	public String seDeconnecter(){
		session.invalidate();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Vous avez été déconnecté avec succès"));
		setConnected(false);
		return "home?faces-redirect=true";
	}
}
