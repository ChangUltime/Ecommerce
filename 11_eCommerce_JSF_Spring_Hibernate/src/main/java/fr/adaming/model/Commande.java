package fr.adaming.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name="commandes")
public class Commande implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_commande")
	private Long idCommande;
	private Date dateCommande;
	
	@ManyToOne
	@JoinColumn(name="client_id", referencedColumnName="id_client")
	private Client client;
	
	
	@OneToMany(mappedBy="commande", fetch=FetchType.EAGER , cascade=CascadeType.ALL)//
	private List<LigneDeCommande> listeLignes;

	public Commande() {
		super();
	}

	public Commande(Date dateCommande, Client client) {
		super();
		this.dateCommande = dateCommande;
		this.client = client;
	}

	public Commande(Long idCommande, Date dateCommande, Client client) {
		super();
		this.idCommande = idCommande;
		this.dateCommande = dateCommande;
		this.client = client;
	}

	public Long getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(Long idCommande) {
		this.idCommande = idCommande;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<LigneDeCommande> getListeLignes() {
		return listeLignes;
	}

	public void setListeLignes(List<LigneDeCommande> listeLignes) {
		this.listeLignes = listeLignes;
	}
	
	//methodes utiles
	
	@Override
	public String toString() {
		return "Commande [idCommande=" + idCommande + ", dateCommande=" + dateCommande + ", client=" + client.getEmail() + "]";
	}

	public List<Produit> getListProduits(){
		
		List<Produit>  listeProduits = new ArrayList<Produit>();
		
		for(LigneDeCommande ldc : listeLignes){
			listeProduits.add(ldc.getProduit());
		}
		
		return listeProduits;
	}
	
	public LigneDeCommande getLigneByProduit(Produit produit){
		for(LigneDeCommande ldc : listeLignes){
			if(produit.getDesignation().equals(ldc.getProduit().getDesignation()) && produit.getIdProduit().equals(ldc.getProduit().getIdProduit())){
				return ldc;
			}
		}
		return null;
	}
	
	public void viderPanier(){
		for(LigneDeCommande ldc : this.listeLignes){
			this.listeLignes.remove(ldc);
		}
	}
	
	public double getTotal(){
		double total = 0.;
		for(LigneDeCommande ldc : this.listeLignes){
			total += ldc.getTotal();
		}
		return total;
	}
}
