package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "catMB")
@RequestScoped
public class CategorieManagedBean implements Serializable {

	@ManagedProperty(value="#{catService}")
	ICategorieService catService;
	
	@ManagedProperty(value="#{prodService}")
	IProduitService prodService;

	private Categorie categorie;
	
	private Produit produit;

	private Agent agent;

	private boolean afficheProduitByCat;
	
	
	
	private HttpSession session;

	private List<Categorie> listeCategories;

	public CategorieManagedBean() {
		this.categorie = new Categorie();
	}

	@PostConstruct // Cette annotation sert a excecute la méthode juste après
	// l'instanciation du managedBean
	public void init() {
		afficheProduitByCat = false;
		
		// Récuparation du context
		FacesContext context = FacesContext.getCurrentInstance();

		// Récuperation de la session
		this.session = (HttpSession) context.getExternalContext().getSession(false);

		// Recuperation de l'agent à partir de la session
		this.agent = (Agent) session.getAttribute("agentSession");
		
		listeCategories = catService.getAllCategorie();
		session.setAttribute("categoriesListe", listeCategories);
		
		categorie.setListeProduits(prodService.getProductByCategorie(categorie));
		
		categorie = new Categorie();
		
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

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
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

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public boolean isAfficheProduitByCat() {
		return afficheProduitByCat;
	}

	public void setAfficheProduitByCat(boolean afficheProduitByCat) {
		this.afficheProduitByCat = afficheProduitByCat;
	}

	// Méthode metiers
	public String addCategorie() {
		Categorie cat_out = catService.addCategorie(this.categorie);

		if (cat_out != null) {
			listeCategories = catService.getAllCategorie();

			session.setAttribute("categoriesListe", listeCategories);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categorie ajoutée: " + categorie));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun categorie ajoutée"));

			return "addCategorie";
		}
	}

	public String updateCategorie() {
		Categorie cat_out = catService.updateCategorie(categorie);

		if (cat_out != null) {
			listeCategories = catService.getAllCategorie();

			session.setAttribute("categoriesListe", listeCategories);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categorie modifiée : " + categorie));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("la modification n'a pas été effectuée"));

			return "homeAgent";
		}
	}

	public String deleteCategorie() {

		boolean verif = catService.deleteCategorie(categorie);

		if (verif) {
			listeCategories = catService.getAllCategorie();

			session.setAttribute("categoriesListe", listeCategories);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Catégorie supprimée"));
			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun client supprimÃ©"));
			return "deleteCategorie";
		}

	}

	public String getCategorieById() {

		Categorie cat_out = catService.getCategorie(categorie);

		System.out.println("------------------------- " + cat_out);

		if (cat_out.getIdCategorie().equals(categorie.getIdCategorie())) {

			categorie = cat_out;

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Catégorie trouvée !"));
			return "getCategorie";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucune catégorie trouvée !"));
			return "getCategorie";
		}

	}
}
