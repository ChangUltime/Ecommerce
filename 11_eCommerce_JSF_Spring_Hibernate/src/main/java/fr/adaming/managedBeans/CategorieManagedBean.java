package fr.adaming.managedBeans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFExporter;
import org.primefaces.component.export.PDFOptions;

import fr.adaming.model.Agent;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "catMB")
@RequestScoped
public class CategorieManagedBean implements Serializable {

	@ManagedProperty(value="#{catService}")
	private ICategorieService catService;
	
	@ManagedProperty(value="#{prodService}")
	private IProduitService prodService;

	private Categorie categorie;
	
	private Produit produit;

	private Agent agent;

	private boolean afficheProduitByCat;
	
	
	
	private HttpSession session;

	private List<Categorie> listeCategories;
	
	
	private ExcelOptions excelOpt;
	
	private PDFOptions pdfOpt;

	public CategorieManagedBean() {
		this.categorie = new Categorie();
		
		excelOpt = new ExcelOptions();
        excelOpt.setFacetBgColor("#F88017");
        excelOpt.setFacetFontSize("10");
        excelOpt.setFacetFontColor("#0000ff");
        excelOpt.setFacetFontStyle("BOLD");
        excelOpt.setCellFontColor("#00ff00");
        excelOpt.setCellFontSize("8");
		
		pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#F88017");
        pdfOpt.setFacetFontColor("#0000ff");
        pdfOpt.setFacetFontStyle("BOLD");
        pdfOpt.setCellFontSize("12");
	}

	@PostConstruct // Cette annotation sert a excecute la m�thode juste apr�s
	// l'instanciation du managedBean
	public void init() {
		afficheProduitByCat = false;
		
		// R�cuparation du context
		FacesContext context = FacesContext.getCurrentInstance();

		// R�cuperation de la session
		this.session = (HttpSession) context.getExternalContext().getSession(false);

		// Recuperation de l'agent � partir de la session
		this.agent = (Agent) session.getAttribute("agentSession");
		
		listeCategories = catService.getAllCategorie();
		session.setAttribute("categoriesListe", listeCategories);
		
		categorie.setListeProduits(prodService.getProductByCategorie(categorie));
		
		categorie = new Categorie();
		
	}

	public IProduitService getProdService() {
		return prodService;
	}

	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
	}

	public ICategorieService getCatService() {
		return catService;
	}

	public void setCatService(ICategorieService catService) {
		this.catService = catService;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
		
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public boolean isAfficheProduitByCat() {
		return afficheProduitByCat;
	}

	public void setAfficheProduitByCat(boolean afficheProduitByCat) {
		this.afficheProduitByCat = afficheProduitByCat;
	}
	
	public ExcelOptions getExcelOpt() {
		return excelOpt;
	}

	public void setExcelOpt(ExcelOptions excelOpt) {
		this.excelOpt = excelOpt;
	}
	
	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}

	// M�thode metiers
	public String addCategorie() {
		Categorie cat_out = catService.addCategorie(this.categorie);

		if (cat_out != null) {
			listeCategories = catService.getAllCategorie();

			session.setAttribute("categoriesListe", listeCategories);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categorie ajout�e: " + categorie));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun categorie ajout�e"));

			return "addCategorie";
		}
	}

	public String updateCategorie() {
		Categorie cat_out = catService.updateCategorie(categorie);

		if (cat_out != null) {
			listeCategories = catService.getAllCategorie();

			session.setAttribute("categoriesListe", listeCategories);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categorie modifi�e : " + categorie));

			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("la modification n'a pas �t� effectu�e"));

			return "homeAgent";
		}
	}

	public String deleteCategorie() {

		boolean verif = catService.deleteCategorie(categorie);

		if (verif) {
			listeCategories = catService.getAllCategorie();

			session.setAttribute("categoriesListe", listeCategories);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cat�gorie supprim�e"));
			return "homeAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucun client supprimé"));
			return "deleteCategorie";
		}

	}

	public String getCategorieById() {

		Categorie cat_out = catService.getCategorie(categorie);

		System.out.println("------------------------- " + cat_out);

		if (cat_out.getIdCategorie().equals(categorie.getIdCategorie())) {

			categorie = cat_out;

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cat�gorie trouv�e !"));
			return "getCategorie";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aucune cat�gorie trouv�e !"));
			return "getCategorie";
		}

	}
	
	public Categorie getBeer(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Categorie beer : listeCategories){
            if (id.equals(beer.getIdCategorie())){
                return beer;
            }
        }
        return null;
    }
	
	public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
         
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
             
            cell.setCellStyle(cellStyle);
        }
    }
	
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
 
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "images" + File.separator + "Logo.jpg";
         
        pdf.add(Image.getInstance(logo));
        pdf.add(new Paragraph("Liste des categories"));
    }
}
