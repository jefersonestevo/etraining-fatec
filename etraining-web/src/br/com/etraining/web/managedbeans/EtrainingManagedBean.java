package br.com.etraining.web.managedbeans;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.etraining.web.exceptions.ViewException;

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

	public void addExceptionMessage(ViewException e) {
		addErrorMessage(getMessage("messageException", e.getCodigoExcecao()
				.getCodigo().toString()));
	}

	public String getMessage(String chave) {
		return getMessage("message", chave);
	}

	private String getMessage(String resource, String chave) {
		try {
			ResourceBundle bundle = FacesContext
					.getCurrentInstance()
					.getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(),
							resource);
			return bundle.getString(chave);
		} catch (MissingResourceException e) {
			return chave;
		}
	}

	public Long getLongParameter(String param) {
		return new Long(getStringParameter(param));
	}

	public String getStringParameter(String param) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(param);
	}
}
