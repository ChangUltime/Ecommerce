package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table
public class LigneDeCommande implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ligne")
	private Long id;
	private int quantite;
	private int prix;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="produit_id", referencedColumnName="id_produit")
	private Produit produit;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="commande_id",referencedColumnName="id_commande")
	private Commande commande;

	public LigneDeCommande() {
		super();
	}

	public LigneDeCommande(int quantite, int prix, Produit produit, Commande commande) {
		super();
		this.quantite = quantite;
		this.prix = prix;
	}

	public LigneDeCommande(Long id, int quantite, int prix, Produit produit, Commande commande) {
		super();
		this.id = id;
		this.quantite = quantite;
		this.prix = prix;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	@Override
	public String toString() {
		return "LigneDeCommande [id=" + id + ", quantite=" + quantite + ", prix=" + prix + ", produit=" + produit.getDesignation() + "]";
	}
	
	
	
}
