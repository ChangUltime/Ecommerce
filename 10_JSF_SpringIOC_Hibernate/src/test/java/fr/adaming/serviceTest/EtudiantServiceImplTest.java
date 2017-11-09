package fr.adaming.serviceTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.model.Etudiant;
import fr.adaming.model.Formateur;
import fr.adaming.service.IEtudiantService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
public class EtudiantServiceImplTest {
	// Injection de la dépendance EtudiantService
	@Autowired
	private IEtudiantService etudiantService;

	private Formateur f;

	@Before
	public void setUp() {

		f = new Formateur(1, "f@f", "f");
	}

	@Test // Cas de test de la taille de la liste retournée
	public void testGetAllEtudiants() {
		int resultatAttendu = 2;

		Formateur f = new Formateur(1, "f@f", "f");

		assertEquals(resultatAttendu, etudiantService.getAllEtudiants(f).size());
	}

	@Test // Cas de test du premier element de la liste
	public void testGetAllEtudiants1() {
		String resultatAttendu = "CHANG";

		assertEquals(resultatAttendu, etudiantService.getAllEtudiants(f).get(0).getNom());
	}

	@Test
	public void testGetEtudiant() {
		String resultatAttendu = "THAO";

		Etudiant e = new Etudiant();
		e.setId(2);
		e.setFormateur(f);

		assertEquals(resultatAttendu, etudiantService.getEtudiant(e).getNom());
	}

	@Test
	@Transactional // Pour rendre les methodes qui ne sont pas
					// transactionnelles, transactionnelles
	@Rollback(true) // Annuler la transaction après l'execution de cette methode
	public void testAddEtudiant() {
		Etudiant e = new Etudiant();
		e.setNom("HOHO");
		e.setPrenom("Hihi");
		e.setDn(new Date(1996 - 05 - 07));
		e.setFormateur(f);

		int nbEtudiant = etudiantService.getAllEtudiants(f).size();
		int resultatAttendu = nbEtudiant + 1;

		etudiantService.addEtudiant(e);

		assertEquals(resultatAttendu, etudiantService.getAllEtudiants(f).size());
	}

	@Test
	@Rollback(true)
	@Transactional
	public void testAddEtudiant1() {
		Etudiant e = new Etudiant();
		e.setNom("HOHO");
		e.setPrenom("Hihi");
		e.setDn(new Date(1996 - 05 - 07));
		e.setFormateur(f);

		String resultatAttendu = "HOHO";

		e=etudiantService.addEtudiant(e);

		assertEquals(resultatAttendu, etudiantService.getEtudiant(e).getNom());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdateEtudiant(){
		Etudiant e = new Etudiant();
		e.setId(1);
		e.setNom("HOHO");
		e.setPrenom("Hihi");
		e.setDn(new Date(1996-05-07));
		e.setFormateur(f);
		
		e = etudiantService.updateEtudiant(e);
		
		String resultatAttendu = "HOHO";
		
		assertEquals(resultatAttendu, etudiantService.getEtudiant(e).getNom());
		
//		Etudiant e = new Etudiant();
//		e.setId(1);
//		e.setNom("HOHO");
//		e.setPrenom("Hihi");
//		e.setDn(new Date(1996-05-07));
//		e.setFormateur(f);
//		
//		etudiantService.updateEtudiant(e);
//		
//		Etudiant e1 = etudiantService.getEtudiant(e);
//		
//		Object[] listeAttendus = {1, "HOHO", "Hihi", new Date(1996-05-07)};
//		Object[] listeObtenus = { e1.getId(), e1.getNom(), e1.getPrenom(), e1.getDn()};
//		
//		assertArrayEquals(listeAttendus, listeObtenus);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDeleteEtudiant(){
		List<Etudiant> liste = etudiantService.getAllEtudiants(f);
		
		Etudiant e = new Etudiant();
		e.setId(liste.get(0).getId());
		e.setFormateur(f);
		
		etudiantService.deleteEtudiant(e);
		
		assertNull(etudiantService.getEtudiant(e));
	}
	
}
