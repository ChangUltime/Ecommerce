package fr.adaming.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "etudiants")
public class Etudiant implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_e")
	private int id;
	private String nom;
	private String prenom;

	@Temporal(TemporalType.DATE)
	private Date dn;

	@ManyToOne
	@JoinColumn(name="f_id", referencedColumnName="id_f")
	private Formateur formateur;
	
	public Etudiant() {
		super();
	}

	public Etudiant(String nom, String prenom, Date dn) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dn = dn;
	}

	public Etudiant(int id, String nom, String prenom, Date dn) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dn = dn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDn() {
		return dn;
	}

	public void setDn(Date dn) {
		this.dn = dn;
	}

	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

}
