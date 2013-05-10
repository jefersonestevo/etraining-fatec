package br.com.etraining.web.managedbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.dom.PerfilAcesso;
import br.com.etraining.client.vo.impl.realizarlogin.RealizarLoginVO;
import br.com.etraining.client.vo.impl.realizarlogin.RespostaRealizarLoginVO;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.impl.TratadorNegocioService;

@Named
@SessionScoped
public class LoginBean extends EtrainingManagedBean {

	private static final long serialVersionUID = 181348665432314690L;

	@Inject
	private TratadorNegocioService service;

	@Inject
	private DiaExercicioBean diaExercicioBean;

	@Inject
	private SessionBean sessionBean;

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

			RespostaRealizarLoginVO resposta = (RespostaRealizarLoginVO) service
					.executa(request);
			sessionBean.setIdAluno(resposta.getIdAluno());
			sessionBean.setNumeroMatricula(resposta.getNumeroMatricula());

		} catch (ViewException e) {
			addExceptionMessage(e);
			return "/pages/login.jsf";
		}
		diaExercicioBean.setDiaSelecionado(new Date());
		return diaExercicioBean.irParaTelaSelecionarDiaExercicio();
	}

	public String voltarTelaLogin() {
		numeroMatricula = null;
		senha = null;
		return "/pages/login.jsf";
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

	public DiaExercicioBean getDiaExercicioBean() {
		return diaExercicioBean;
	}

	public void setDiaExercicioBean(DiaExercicioBean diaExercicioBean) {
		this.diaExercicioBean = diaExercicioBean;
	}

}
