package br.com.etraining.web.managedbeans.aluno;

import java.util.Collections;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.aluno.ConsultaAlunoVO;
import br.com.etraining.client.vo.impl.aluno.ConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaAlunoVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaListaAlunoSimplesVO;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.ITratadorNegocioService;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;
import br.com.etraining.web.utils.comparador.ComparadorAlunoSimplesAlfabetico;

@Named
@SessionScoped
public class AlunoController extends EtrainingManagedBean {

	private static final long serialVersionUID = 6811465607206792357L;

	@Inject
	private ITratadorNegocioService service;

	private ConsultaListaAlunoSimplesVO consulta = new ConsultaListaAlunoSimplesVO();
	private RespostaConsultaListaAlunoSimplesVO respostaLista;
	private RespostaConsultaAlunoVO resposta = new RespostaConsultaAlunoVO();

	public String irParaTelaPesquisa() {
		consulta = new ConsultaListaAlunoSimplesVO();
		respostaLista = new RespostaConsultaListaAlunoSimplesVO();
		resposta = new RespostaConsultaAlunoVO();

		return "/pages/aluno/pesquisaAluno.xhtml";
	}

	public void pesquisar() {
		respostaLista = new RespostaConsultaListaAlunoSimplesVO();
		RespostaConsultaListaAlunoSimplesVO resp = null;

		try {
			resp = (RespostaConsultaListaAlunoSimplesVO) service
					.executa(consulta);

			Collections.sort(resp.getListaAlunos(),
					new ComparadorAlunoSimplesAlfabetico());

			respostaLista.getListaAlunos().addAll(resp.getListaAlunos());
		} catch (ViewException e) {
			addExceptionMessage(e);
		}
	}

	public String irParaTelaEdicao() {
		Long id = getLongParameter("id");
		return irParaTelaEdicao(id);
	}

	protected String irParaTelaEdicao(Long id) {
		try {
			ConsultaAlunoVO consulta = new ConsultaAlunoVO();
			consulta.setIdAluno(id);
			resposta = (RespostaConsultaAlunoVO) service.executa(consulta);
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}
		return "/pages/aluno/editarAluno.xhtml";
	}

	public String alterar() {

		System.out.println("ALTERANDO");
		// TODO - Consertar EJB
		Long id = null;
		return irParaTelaEdicao(id);
	}

	public ConsultaListaAlunoSimplesVO getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaListaAlunoSimplesVO consulta) {
		this.consulta = consulta;
	}

	public ITratadorNegocioService getService() {
		return service;
	}

	public void setService(ITratadorNegocioService service) {
		this.service = service;
	}

	public RespostaConsultaListaAlunoSimplesVO getRespostaLista() {
		return respostaLista;
	}

	public void setRespostaLista(
			RespostaConsultaListaAlunoSimplesVO respostaLista) {
		this.respostaLista = respostaLista;
	}

	public RespostaConsultaAlunoVO getResposta() {
		return resposta;
	}

	public void setResposta(RespostaConsultaAlunoVO resposta) {
		this.resposta = resposta;
	}

}
