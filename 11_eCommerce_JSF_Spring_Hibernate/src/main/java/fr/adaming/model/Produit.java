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
import javax.persistence.Transient;

@Entity
@Table(name="produits")
public class Produit implements Serializable{
	// D�claration des attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produit")
	private Long idProduit;
	private String designation;
	private String description;
	private double prix;
	private int stock;
	private boolean selectionne;
	
	@Transient
	private boolean modifProduit;
	
	private double reduc;
	private double prixPromo;
	
	@Transient
	private LigneDeCommande ligne;
	
	// Association UML en JAVA
	@OneToMany(mappedBy="produit", fetch=FetchType.LAZY)//
	private List<LigneDeCommande> ldc;
	
	@ManyToOne
	@JoinColumn(name="categorie_id", referencedColumnName="id_categorie")
	private Categorie categorie;
	
	// D�claration des constructeurs
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

	
	// D�claration des getters setters
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

	public boolean isModifProduit() {
		return modifProduit;
	}

	public void setModifProduit(boolean modifProduit) {
		this.modifProduit = modifProduit;
	}

	public double getReduc() {
		return reduc;
	}

	public void setReduc(double reduc) {
		this.reduc = reduc;
	}

	public double getPrixPromo(){
		return prixPromo;
	}
	
	public void setPrixPromo(double prixPromo) {
		this.prixPromo = prixPromo;
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

	// Redefinition de la m�thode toString
	@Override
	public String toString() {
		return "Produit [idProduit=" + idProduit + ", designation=" + designation + ", description=" + description
				+ ", prix=" + prix + ", stock=" + stock + ", selectionne=" + selectionne + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categorie == null) ? 0 : categorie.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((idProduit == null) ? 0 : idProduit.hashCode());
		result = prime * result + ((ldc == null) ? 0 : ldc.hashCode());
		result = prime * result + ((ligne == null) ? 0 : ligne.hashCode());
		result = prime * result + (modifProduit ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(prix);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(prixPromo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(reduc);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (selectionne ? 1231 : 1237);
		result = prime * result + stock;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		if (categorie == null) {
			if (other.categorie != null)
				return false;
		} else if (!categorie.equals(other.categorie))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (idProduit == null) {
			if (other.idProduit != null)
				return false;
		} else if (!idProduit.equals(other.idProduit))
			return false;
		if (ldc == null) {
			if (other.ldc != null)
				return false;
		} else if (!ldc.equals(other.ldc))
			return false;
		if (ligne == null) {
			if (other.ligne != null)
				return false;
		} else if (!ligne.equals(other.ligne))
			return false;
		if (modifProduit != other.modifProduit)
			return false;
		if (Double.doubleToLongBits(prix) != Double.doubleToLongBits(other.prix))
			return false;
		if (Double.doubleToLongBits(prixPromo) != Double.doubleToLongBits(other.prixPromo))
			return false;
		if (Double.doubleToLongBits(reduc) != Double.doubleToLongBits(other.reduc))
			return false;
		if (selectionne != other.selectionne)
			return false;
		if (stock != other.stock)
			return false;
		return true;
	}
	
	
	// Autre m�thode
	
	
	
}