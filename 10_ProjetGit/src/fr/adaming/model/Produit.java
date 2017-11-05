package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="produits")
public class Produit implements Serializable{
	// Déclaration des attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produit")
	private Long idProduit;
	private String designation;
	private String description;
	private double prix;
	private int stock;
	private boolean selectionne;
	private double reduc;
	
	private LigneDeCommande ligne;
	
	// Association UML en JAVA
	@OneToMany(mappedBy="produit", fetch=FetchType.EAGER)
	private List<LigneDeCommande> ldc;
	
	@ManyToOne
	@JoinColumn(name="categorie_id", referencedColumnName="id_categorie")
	private Categorie categorie;
	
	// Déclaration des constructeurs
	public Produit() {
		super();
	}

	public Produit(String designation, String description, double prix, int stock, boolean selectionne) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.stock = stock;
		this.selectionne = selectionne;
	}

	public Produit(Long idProduit, String designation, String description, double prix, int stock,
			boolean selectionne) {
		super();
		this.idProduit = idProduit;
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.stock = stock;
		this.selectionne = selectionne;
	}

	
	// Déclaration des getters setters
	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}

	public double getReduc() {
		return reduc;
	}

	public void setReduc(double reduc) {
		this.reduc = reduc;
	}

	public LigneDeCommande getLigne() {
		return ligne;
	}

	public void setLigne(LigneDeCommande ligne) {
		this.ligne = ligne;
	}

	public List<LigneDeCommande> getLdc() {
		return ldc;
	}

	public void setLdc(List<LigneDeCommande> ldc) {
		this.ldc = ldc;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	// Redefinition de la méthode toString
	@Override
	public String toString() {
		return "Produit [idProduit=" + idProduit + ", designation=" + designation + ", description=" + description
				+ ", prix=" + prix + ", stock=" + stock + ", selectionne=" + selectionne + "]";
	}
	
	
	
}