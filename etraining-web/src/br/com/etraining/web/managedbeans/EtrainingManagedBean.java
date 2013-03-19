package br.com.etraining.web.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class EtrainingManagedBean {

	public void addErrorMessage(String titulo, String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, message));
	}

	public void addErrorMessage(String message) {
		addErrorMessage(null, message);
	}

	public void addInfoMessage(String titulo, String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, message));
	}

	public void addInfoMessage(String message) {
		addInfoMessage(null, message);
	}

	public void addWarnMessage(String titulo, String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, message));
	}

	public void addWarnMessage(String message) {
		addWarnMessage(null, message);
	}
}
