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

	// D�claration et injection des d�pendences
	@ManagedProperty(value="#{fService}") // Injection de d�pendances en utilisant l'annotation JSF
	private IFormateurService formateurService;
	
	@ManagedProperty(value="#{eService}")
	private IEtudiantService etudiantService;
	
	// D�claration des attributs vues dans la page xhtml
	private Formateur formateur;

	public FormateurManagedBean() {
		this.formateur = new Formateur();
	}

	// Setter pour l'injection de d�pendances
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
		// Appelle de la m�thode service
		Formateur fOut = formateurService.isExist(this.formateur);
		
		if(fOut!=null){
			//Stockage du formateur trouv� dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fSession", fOut);
			
			// R�cuperation de la liste des �tudiants du formateur trouv�
			List<Etudiant> listeEtudiants = etudiantService.getAllEtudiants(fOut);
			
			// Stockage de la liste d'�tudiants dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("etudiantsListe", listeEtudiants);
			return "Succes";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Identifiant ID ou Password erron�."));
			return "Echec";
		}
	}
	
	public String seDeconnecter(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "login";
	}
	
}
