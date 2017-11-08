package fr.adaming.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;


public interface ICategorieService {
	public Categorie addCategorie(Categorie categorie);
	public Categorie updateCategorie(Categorie categorie);
	public boolean deleteCategorie(Categorie categorie);
	public Categorie getCategorie(Categorie categorie);
	public List<Categorie> getAllCategorie();
}
