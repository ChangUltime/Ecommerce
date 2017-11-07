package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Etudiant;
import fr.adaming.model.Formateur;
import fr.adaming.service.IEtudiantService;
import fr.adaming.service.IFormateurService;

@ManagedBean(name="fMB")
@RequestScoped
public class FormateurManagedBean implements Serializable{

	// Déclaration et injection des dépendences
	@ManagedProperty(value="#{fService}") // Injection de dépendances en utilisant l'annotation JSF
	private IFormateurService formateurService;
	
	@ManagedProperty(value="#{eService}")
	private IEtudiantService etudiantService;
	
	// Déclaration des attributs vues dans la page xhtml
	private Formateur formateur;

	public FormateurManagedBean() {
		this.formateur = new Formateur();
	}

	// Setter pour l'injection de dépendances
	public void setFormateurService(IFormateurService formateurService) {
		this.formateurService = formateurService;
	}

	public void setEtudiantService(IEtudiantService etudiantService) {
		this.etudiantService = etudiantService;
	}

	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}
	
	public String seConnecter(){
		// Appelle de la méthode service
		Formateur fOut = formateurService.isExist(this.formateur);
		
		if(fOut!=null){
			//Stockage du formateur trouvé dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fSession", fOut);
			
			// Récuperation de la liste des étudiants du formateur trouvé
			List<Etudiant> listeEtudiants = etudiantService.getAllEtudiants(fOut);
			
			// Stockage de la liste d'étudiants dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("etudiantsListe", listeEtudiants);
			return "Succes";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Identifiant ID ou Password erroné."));
			return "Echec";
		}
	}
	
	public String seDeconnecter(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "login";
	}
	
}
