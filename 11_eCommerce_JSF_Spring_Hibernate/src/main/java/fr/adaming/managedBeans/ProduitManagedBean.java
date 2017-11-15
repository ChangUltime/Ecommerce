package fr.adaming.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import fr.adaming.model.Agent;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name="prodMB")
@RequestScoped
public class ProduitManagedBean {

	@ManagedProperty(value="#{prodService}")
	IProduitService prodService;

	private Produit produit;
	private double reduc;

	private Categorie categorie;
	private Agent agent;

	HttpSession session;

	private List<Produit> listeProduits;

	private List<Produit> listeProduitsSelected;

	public ProduitManagedBean() {
		this.produit = new Produit();
	}

	@PostConstruct // Cette annotation sert a excecute la m�thode juste
					// apr�s
	// l'instanciation du managedBean
	public void init() {
		// R�cuparation du context
		FacesContext context = FacesContext.getCurrentInstance();

		// R�cuperation de la session
		this.session = (HttpSession) context.getExternalContext().getSession(false);

		// Recuperation de l'agent � partir de la session
		this.agent = (Agent) session.getAttribute("agentSession");

		listeProduits = prodService.getAllProduit();
		System.out.println("La liste des produits à l'initialisation" +listeProduits);
		session.setAttribute("produitsListe", listeProduits);
		
		listeProduitsSelected = prodService.getProduitSelected();
		session.setAttribute("produitsSelectedListe", listeProduitsSelected);

		//produit = new Produit();
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

	public double getReduc() {
		return reduc;
	}

	public void setReduc(double reduc) {
		this.reduc = reduc;
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
		System.out.println("La liste des produits au getter" +listeProduits);
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		System.out.println("La liste des produits au setter" +listeProduits);
		this.listeProduits = listeProduits;
	}

	public List<Produit> getListeProduitsSelected() {
		return listeProduitsSelected;
	}

	public void setListeProduitsSelected(List<Produit> listeProduitsSelected) {
		this.listeProduitsSelected = listeProduitsSelected;
	}

	// M�thode metiers
	public String addProduit() {
		Produit prod_out = prodService.addProduit(produit, categorie);

		System.out.println("----------------------hh" + prod_out);

		if (prod_out != null) {
			listeProduits = prodService.getAllProduit();

			session.setAttribute("produitsListe", listeProduits);

			System.out.println("----------------------------------------" + listeProduits);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit ajout� : " + produit));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit ajout�"));

			return "addProduit";
		}
	}

	public String updateProduit() {
		Produit prod_out = prodService.updateProduit(produit, categorie);

		if (prod_out != null) {
			listeProduits = prodService.getAllProduit();

			session.setAttribute("produitsListe", listeProduits);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit modifi� : " + produit));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit modifi�"));

			return "updateProduit";
		}
	}

	public String deleteProduit() {
		boolean verif = prodService.deleteProduit(produit, categorie);

		if (verif) {
			listeProduits = prodService.getAllProduit();

			session.setAttribute("produitsListe", listeProduits);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit supprim� : " + produit));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit supprim�"));

			return "deleteProduit";
		}
	}

	public void getProductByCategorie() {
		List<Produit> listeProdByCat = prodService.getProductByCategorie(categorie);

		if (listeProdByCat != null) {
			listeProduits = listeProdByCat;
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit trouv�"));

		}
	}

	public void getProductById(ActionEvent event) {
		Produit prod_out = prodService.getProduit(produit);

		if (prod_out != null) {
			produit = prod_out;
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit trouv�"));

		}
	}

	public void getProductSelected() {
		listeProduitsSelected = prodService.getProduitSelected();

		if (listeProduitsSelected != null) {
			listeProduits = listeProduitsSelected;
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit trouv�"));

		}
	}

	public void promoProduit() {
		System.out.println("Le prix du produit: " + produit.getPrix());
		System.out.println("La promotion à appliquer: " + produit.getReduc() + "%");

		produit.setPrixPromo(produit.getPrix() * (1 - (produit.getReduc() / 100)));

		Produit prod_out = prodService.updateProduit(produit, categorie);

		if (prod_out != null) {
			listeProduits = prodService.getAllProduit();

			session.setAttribute("produitsListe", listeProduits);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit modifi� : " + produit));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun produit modifi�"));
		}
	}

//	public void onRowEdit(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Car Edited", ((Produit) event.getObject()).getIdProduit());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//     
//    public void onRowCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Produit) event.getObject()).getIdProduit());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
