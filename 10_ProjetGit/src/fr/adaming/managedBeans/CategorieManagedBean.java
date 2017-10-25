package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Categorie;
import fr.adaming.service.ICategorieService;

@ManagedBean
@Table(name = "categories")
@ApplicationScoped
public class CategorieManagedBean implements Serializable {

	@EJB
	ICategorieService catService;

	private Categorie categorie;

	private Agent agent;
	
	private List<Categorie> listeCategories;

	private HttpSession session;

	public CategorieManagedBean() {
		this.categorie = new Categorie();
	}

	@PostConstruct // Cette annotation sert a excecute la méthode juste après
	// l'instanciation du managedBean
	public void init() {
		// Récuparation du context
		FacesContext context = FacesContext.getCurrentInstance();

		// Récuperation de la session
		this.session = (HttpSession) context.getExternalContext().getSession(false);

		// Recuperation de l'agent à partir de la session
		this.agent = (Agent) session.getAttribute("agentSession");
	}

	public ICategorieService getCatService() {
		return catService;
	}

	public void setCatService(ICategorieService catService) {
		this.catService = catService;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	// Méthode metiers
	public String addCategorie() {
		Categorie cat_out = catService.addCategorie(this.categorie);

		if (cat_out != null) {
			session.setAttribute("categoriesListe", listeCategories);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categorie ajoutée: " + categorie));
			
			return "homeAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun categorie ajoutée"));
			
			return "categorie";
		}
		
		

	}
}
