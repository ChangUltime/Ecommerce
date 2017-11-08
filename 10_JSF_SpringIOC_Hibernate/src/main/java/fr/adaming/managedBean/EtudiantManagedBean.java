package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Etudiant;
import fr.adaming.model.Formateur;
import fr.adaming.service.IEtudiantService;

@ManagedBean(name = "eMB")
@RequestScoped
public class EtudiantManagedBean implements Serializable {

	@ManagedProperty(value = "#{eService}")
	private IEtudiantService etudiantService;

	private Etudiant etudiant;
	private Formateur formateur;

	HttpSession maSession;

	public EtudiantManagedBean() {
		this.etudiant = new Etudiant();
	}

	@PostConstruct
	public void init() {
		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		this.formateur = (Formateur) maSession.getAttribute("fSession");
	}

	public void setEtudiantService(IEtudiantService etudiantService) {
		this.etudiantService = etudiantService;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public String addEtudiant() {
		// relier le formateur à l'etudiant
		this.etudiant.setFormateur(this.formateur);

		Etudiant eOut = etudiantService.addEtudiant(etudiant);

		if (eOut.getId() != 0) {
			etudiant = eOut;
			List<Etudiant> listeEtudiants = etudiantService.getAllEtudiants(etudiant.getFormateur());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("etudiantsListe",
					listeEtudiants);

			return "accueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'étudiant n'a pas été ajouté"));

			return "addStudent";
		}
	}

	public String updateEtudiant() {
		// Relier le formateur à l'étudiant
		etudiant.setFormateur(formateur);

		Etudiant eOut = etudiantService.updateEtudiant(etudiant);

		if (eOut.getId() != 0) {
			etudiant = eOut;
			List<Etudiant> listeEtudiants = etudiantService.getAllEtudiants(etudiant.getFormateur());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("etudiantsListe",
					listeEtudiants);

			return "accueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'étudiant n'a pas été modifié"));

			return "updateStudent";
		}

	}

	public String deleteEtudiant() {
		// Relier le formateur à l'étudiant
		etudiant.setFormateur(formateur);

		int verif = etudiantService.deleteEtudiant(etudiant);
		
		if (verif == 1) {
			List<Etudiant> listeEtudiants = etudiantService.getAllEtudiants(formateur);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("etudiantsListe",
					listeEtudiants);

			return "accueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'étudiant n'a pas été modifié"));

			return "deleteStudent";
		}
	}

	public String getEtudiantById() {
		etudiant.setFormateur(formateur);

		Etudiant eOut = etudiantService.getEtudiant(etudiant);

		if (eOut.getId() != 0) {
			this.etudiant = eOut;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'étudiant trouvé !"));
			
			return "getEtud";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'étudiant introuvable?"));

			return "getEtud";
		}
	}
}
