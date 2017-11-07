package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.swing.plaf.BorderUIResource.EtchedBorderUIResource;

@Entity
@Table(name="formateurs")
public class Formateur implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_f")
	private int id;
	private String mail;
	private String password;
	
	@OneToMany(mappedBy="formateur")
	private List<Etudiant> listeEtudiant;

	public Formateur() {
		super();
	}

	public Formateur(String mail, String password) {
		super();
		this.mail = mail;
		this.password = password;
	}

	public Formateur(int id, String mail, String password) {
		super();
		this.id = id;
		this.mail = mail;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Etudiant> getListeEtudiant() {
		return listeEtudiant;
	}

	public void setListeEtudiant(List<Etudiant> listeEtudiant) {
		this.listeEtudiant = listeEtudiant;
	}

}
