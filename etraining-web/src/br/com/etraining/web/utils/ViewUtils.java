package br.com.etraining.web.utils;

import java.io.IOException;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@SessionScoped
public class ViewUtils extends EtrainingManagedBean {

	private static final long serialVersionUID = 2772867976278177792L;

	public static final int QNTD_LINHAS = 10;
	private static final String QNTD_SUGESTAO_LINHAS = "10, 20, 30";

	public String getNomeUsuarioLogado() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRemoteUser();
	}

	public void deslogar() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
			try {
				ExternalContext ext = FacesContext.getCurrentInstance()
						.getExternalContext();
				ext.redirect(ext.getRequestContextPath() + "/pages/home.jsf");
			} catch (IOException e) {
			}
		}
	}

	public int getQntdLinhas() {
		return QNTD_LINHAS;
	}

	public String getSugestaoQntdLinhas() {
		return QNTD_SUGESTAO_LINHAS;
	}

}
