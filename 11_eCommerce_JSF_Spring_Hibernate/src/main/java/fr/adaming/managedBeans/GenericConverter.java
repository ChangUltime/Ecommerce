package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Component;

import fr.adaming.model.Categorie;

@SuppressWarnings("serial")
@ManagedBean(name = "converterMB")
@ViewScoped
public class GenericConverter implements Converter, Serializable {

//	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
//        if(value != null && value.trim().length() > 0) {
//            try {
//                ProduitManagedBean service = (ProduitManagedBean) fc.getExternalContext().getSessionMap().get("categoriesListe");
//                return service.getThemes().get(Integer.parseInt(value));
//            } catch(NumberFormatException e) {
//                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
//            }
//        }
//        else {
//            return null;
//        }
//    }
// 
//    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
//        if(object != null) {
//            return String.valueOf(((Theme) object).getId());
//        }
//        else {
//            return null;
//        }
//    }  
	
	
	
	
	
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String str[] = value.split(",", 3);

		String infosCategorie[] = new String[3];

		if (value != null && !value.trim().isEmpty()) {
			CategorieManagedBean positionBean = new CategorieManagedBean();

			for (int i = 0; i < str.length; i++) {
				String info[] = str[i].split("=", 2);

				for (int j = 0; j < info.length; j++) {
					System.out.println("----info du tableau : " + info[j]);
				}

				infosCategorie[i] = info[1];

				for (int k = 0; k < infosCategorie.length; k++) {
					System.out.println("infocat : " + infosCategorie[k]);
				}
			}

			System.out.println(value);

			// Position position =
			//
			// positionBean.getPositionService().getPositionById(Integer.parseInt(str[0]));
			Categorie position = new Categorie();
			position.setIdCategorie(Long.parseLong(infosCategorie[0]));
			position.setNomCategorie(infosCategorie[1].trim());

			infosCategorie[2] = infosCategorie[2].replaceAll("]", "");

			position.setDescription(infosCategorie[2]);

			System.out.println("Test : " + position.toString());
			return position.getIdCategorie();
		} else {
			FacesMessage message = new FacesMessage("Veuillez sélectionner un poste");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

	//
	// @Override
	// public Object getAsObject(FacesContext ctx, UIComponent uiComponent,
	// String
	// beerId) {
	// ValueExpression vex =
	// ctx.getApplication().getExpressionFactory()
	// .createValueExpression(ctx.getELContext(),
	// "#{catBean}", CategorieManagedBean.class);
	//
	// CategorieManagedBean beers =
	// (CategorieManagedBean)vex.getValue(ctx.getELContext());
	// return beers.getBeer(Integer.valueOf(beerId));
	// }
	//
	// @Override
	// public String getAsString(FacesContext facesContext, UIComponent
	// uiComponent, Object beer) {
	// return ((Categorie)beer).getIdCategorie().toString();
	// }

}
