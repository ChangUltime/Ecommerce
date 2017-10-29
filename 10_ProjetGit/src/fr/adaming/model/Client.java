package fr.adaming.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="clients")
public class Client implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_client")
	private Long idClient;
	private String nom;
	private String prenom;
	private String adresse;
	private String ville;
	private int codePostal;
	private String email;
	private String password;
	private String telephone;
	
	@OneToMany(mappedBy="client", fetch=FetchType.EAGER)
	private List<Commande> listeCommandes;

	public Client() {
		super();
	}

	public Client(String nom, String prenom, String adresse, String email, String password, String telephone) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
	}

	public Client(Long idClient, String nom, String prenom, String adresse, String email, String password,
			String telephone) {
		super();
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
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

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Commande> getListeCommandes() {
		return listeCommandes;
	}

	public void setListeCommandes(List<Commande> listeCommandes) {
		this.listeCommandes = listeCommandes;
	}
	
	public Commande getDerniereCommande(){
		//trycatch nullpointer
		if(this.listeCommandes.size()>0){
			Commande derniereCommande = this.listeCommandes.get(0);
			for(Commande c : this.listeCommandes){
				if(c.getDateCommande().compareTo(derniereCommande.getDateCommande())>0){
					derniereCommande = c;
				}
			}
			return derniereCommande;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse
				+ ", email=" + email + ", password=" + password + ", telephone=" + telephone + "]";
	}
	
}
