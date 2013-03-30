package br.com.etraining.web.managedbeans;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class EtrainingManagedBean implements Serializable {

	private static final long serialVersionUID = 640707863239671690L;

	public void addErrorMessage(String titulo, String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, message));
	}

	public void addErrorMessage(String titulo) {
		addErrorMessage(titulo, null);
	}

	public void addInfoMessage(String titulo, String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, message));
	}

	public void addInfoMessage(String titulo) {
		addInfoMessage(titulo, null);
	}

	public void addWarnMessage(String titulo, String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, message));
	}

	public void addWarnMessage(String titulo) {
		addWarnMessage(titulo, null);
	}

	public String getMessage(String chave) {
		try {
			ResourceBundle bundle = FacesContext
					.getCurrentInstance()
					.getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(),
							"message");
			return bundle.getString(chave);
		} catch (MissingResourceException e) {
			return chave;
		}
	}
}
