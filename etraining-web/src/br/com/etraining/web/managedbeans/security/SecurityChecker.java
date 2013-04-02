package br.com.etraining.web.managedbeans.security;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@SessionScoped
public class SecurityChecker extends EtrainingManagedBean {

	private static final long serialVersionUID = -3460371068901136271L;

	public boolean temPermissao(String role) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.isUserInRole(role);
	}

}
