package fr.adaming.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "prodMB")
@Table(name = "produits")
@RequestScoped
public class ProduitManagedBean {

	@EJB
	IProduitService prodService;

	private Produit produit;
	private Categorie categorie;
	private Agent agent;

	HttpSession session;

	private List<Produit> listeProduits;

	public ProduitManagedBean() {
		this.produit = new Produit();
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

	public IProduitService getProdService() {
		return prodService;
	}

	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
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

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	// Méthode metiers
	public String addProduit() {
		Produit prod_out = prodService.addProduit(produit, categorie);

		System.out.println("----------------------hh" + prod_out);

		if (prod_out != null) {
			listeProduits = prodService.getAllProduit();

			session.setAttribute("produitsListe", listeProduits);

			System.out.println("----------------------------------------" + listeProduits);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit ajouté : " + produit));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit ajouté"));

			return "addProduit";
		}
	}

	public String updateProduit() {
		Produit prod_out = prodService.updateProduit(produit, categorie);

		if (prod_out != null) {
			listeProduits = prodService.getAllProduit();

			session.setAttribute("produitsListe", listeProduits);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit modifié : " + produit));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit modifié"));

			return "updateProduit";
		}
	}

	public String deleteProduit() {
		boolean verif = prodService.deleteProduit(produit, categorie);

		if (verif) {
			listeProduits = prodService.getAllProduit();

			session.setAttribute("produitsListe", listeProduits);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit supprimé : " + produit));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit supprimé"));

			return "deleteProduit";
		}
	}

	public void getProductByCategorie(ActionEvent event) {
		categorie.setIdCategorie((Long)event.getComponent().getAttributes().get("idCat"));
		List<Produit> listeProdByCat = prodService.getProductByCategorie(categorie);

		if (listeProdByCat != null) {
			listeProduits = listeProdByCat;
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit trouvé"));

		}
	}

	public String outcome(){
		return "home";
	}
}
