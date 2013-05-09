package br.com.etraining.web.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.dom.PerfilAcesso;
import br.com.etraining.client.vo.impl.realizarlogin.RealizarLoginVO;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.impl.TratadorNegocioService;

@Named
@SessionScoped
public class LoginBean extends EtrainingManagedBean {

	private static final long serialVersionUID = 181348665432314690L;

	@Inject
	private TratadorNegocioService service;

	private String numeroMatricula;
	private String senha;

	public String login() {
		try {

			RealizarLoginVO request = new RealizarLoginVO();
			request.setNumeroMatricula(numeroMatricula);
			request.setSenha(senha);
			List<PerfilAcesso> listaPerfisAceito = new ArrayList<PerfilAcesso>();
			listaPerfisAceito.add(PerfilAcesso.ALUNO);
			request.setPerfisAceitos(listaPerfisAceito);

			RealizarLoginVO resp = (RealizarLoginVO) service.executa(request);
			resp.getNumeroMatricula();

		} catch (ViewException e) {
			addExceptionMessage(e);
			return "/pages/login.jsf";
		}

		return "/pages/listaDiaExercicio.jsf";
	}

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
