package br.com.etraining.web.managedbeans.programatreinamento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.impl.aluno.ConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.AlunoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioPropostoVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.listaexercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.listaexercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.programatreinamento.ConsultaProgramaTreinamentoAprovacaoVO;
import br.com.etraining.client.vo.impl.programatreinamento.ConsultaProgramaTreinamentoVO;
import br.com.etraining.client.vo.impl.programatreinamento.RespostaConsultaProgramaTreinamentoVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.ITratadorNegocioService;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;
import br.com.etraining.web.utils.comparador.ComparadorAlunoSimplesAlfabetico;
import br.com.etraining.web.utils.comparador.ComparadorExercicioPropostoPorDiaSemana;

@Named
@SessionScoped
public class ProgramaTreinamentoController extends EtrainingManagedBean {

	private static final long serialVersionUID = -6032506735894603834L;

	@Inject
	private ITratadorNegocioService service;

	private RespostaConsultaProgramaTreinamentoVO resposta = new RespostaConsultaProgramaTreinamentoVO();

	private List<AlunoSimplesVO> listaAlunos = new ArrayList<AlunoSimplesVO>();
	private List<AlunoSimplesVO> listaAlunosFiltrados = new ArrayList<AlunoSimplesVO>();
	private AlunoSimplesVO alunoSelecionado = new AlunoSimplesVO();

	private List<SelectItem> listaExercicios = new ArrayList<SelectItem>();
	private ExercicioPropostoVO exercicioProposto = new ExercicioPropostoVO();
	private List<Integer> listaDiasSemana = new ArrayList<Integer>();
	private boolean editavel = true;
	private boolean aprovacao = false;

	public String irParaTelaPesquisa() {
		alunoSelecionado = new AlunoSimplesVO();
		preencherListaAluno();

		return "/pages/programaTreinamento/programaTreinamentoPesquisa.xhtml";
	}

	public String irParaTelaAprovacao() {
		boolean hasError = validaCampos();
		if (hasError)
			return null;

		try {
			this.editavel = false;
			this.aprovacao = true;
			ConsultaProgramaTreinamentoAprovacaoVO consulta = new ConsultaProgramaTreinamentoAprovacaoVO();
			consulta.setIdAluno(alunoSelecionado.getId());
			resposta = (RespostaConsultaProgramaTreinamentoVO) service
					.executa(consulta);

			if (resposta.getProgramaTreinamento() == null
					|| resposta.getProgramaTreinamento().getId() == null) {
				throw new ViewException(
						CodigoExcecao.PROGRAMA_TREINAMENTO_INEXISTENTE);
			}

			Collections.sort(resposta.getProgramaTreinamento()
					.getListaExercicioProposto(),
					new ComparadorExercicioPropostoPorDiaSemana());
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}

		return "/pages/programaTreinamento/programaTreinamentoEdicao.xhtml";
	}

	public String irParaTelaEdicao() {
		boolean hasError = validaCampos();
		if (hasError)
			return null;

		try {
			this.editavel = true;
			this.aprovacao = false;
			ConsultaProgramaTreinamentoVO consulta = new ConsultaProgramaTreinamentoVO();
			consulta.setIdAluno(alunoSelecionado.getId());
			resposta = (RespostaConsultaProgramaTreinamentoVO) service
					.executa(consulta);

			if (resposta.getProgramaTreinamento() == null
					|| resposta.getProgramaTreinamento().getId() == null) {
				throw new ViewException(
						CodigoExcecao.PROGRAMA_TREINAMENTO_INEXISTENTE);
			}

			Collections.sort(resposta.getProgramaTreinamento()
					.getListaExercicioProposto(),
					new ComparadorExercicioPropostoPorDiaSemana());

			preencherListaExercicio();
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}

		return "/pages/programaTreinamento/programaTreinamentoEdicao.xhtml";
	}

	private boolean validaCampos() {
		boolean hasError = false;
		if (alunoSelecionado == null || alunoSelecionado.getId() == null) {
			addErrorMessage(getMessage("Selecao_Aluno_Obrigatoria"));
			hasError = true;
		}
		return hasError;
	}

	public String alterar() {

		// TODO - Fazer EJB Funcionar
		return "/pages/programaTreinamento/programaTreinamentoEdicao.xhtml";
	}

	public void adicionarExercicio() {
		boolean hasError = false;
		if (getExercicioProposto() == null
				|| getExercicioProposto().getId() == null) {
			addErrorMessage("Error_Selecao_Exercicio_Adicionar_Exercicio_Proposto");
			hasError = true;
		}
		if (CollectionUtils.isEmpty(getListaDiasSemana())) {
			addErrorMessage("Error_Selecao_Dias_Adicionar_Exercicio_Proposto");
			hasError = true;
		}
		if (hasError)
			return;

	}

	public void removerExercicio() {

	}

	private void preencherListaAluno() {
		this.listaAlunos.clear();
		this.listaAlunosFiltrados.clear();

		RespostaConsultaListaAlunoSimplesVO resp = null;

		try {
			resp = (RespostaConsultaListaAlunoSimplesVO) service
					.executa(new ConsultaListaAlunoSimplesVO());

			Collections.sort(resp.getListaAlunos(),
					new ComparadorAlunoSimplesAlfabetico());

			this.listaAlunos.addAll(resp.getListaAlunos());
			this.listaAlunosFiltrados.addAll(resp.getListaAlunos());
		} catch (ViewException e) {
			addExceptionMessage(e);
		}
	}

	private void preencherListaExercicio() throws ViewException {
		RespostaConsultaListaExerciciosVO resp = null;
		resp = (RespostaConsultaListaExerciciosVO) service
				.executa(new ConsultaListaExerciciosVO());

		this.listaExercicios.clear();
		this.listaExercicios.add(new SelectItem("", ""));
		for (ExercicioVO exerc : resp.getListaExercicios()) {
			this.listaExercicios.add(new SelectItem(exerc.getId(), exerc
					.getTitulo()));
		}
	}

	public List<AlunoSimplesVO> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<AlunoSimplesVO> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public AlunoSimplesVO getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(AlunoSimplesVO alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}

	public List<AlunoSimplesVO> getListaAlunosFiltrados() {
		return listaAlunosFiltrados;
	}

	public void setListaAlunosFiltrados(
			List<AlunoSimplesVO> listaAlunosFiltrados) {
		this.listaAlunosFiltrados = listaAlunosFiltrados;
	}

	public List<SelectItem> getListaExercicios() {
		return listaExercicios;
	}

	public void setListaExercicios(List<SelectItem> listaExercicios) {
		this.listaExercicios = listaExercicios;
	}

	public RespostaConsultaProgramaTreinamentoVO getResposta() {
		return resposta;
	}

	public void setResposta(RespostaConsultaProgramaTreinamentoVO resposta) {
		this.resposta = resposta;
	}

	public ExercicioPropostoVO getExercicioProposto() {
		return exercicioProposto;
	}

	public void setExercicioProposto(ExercicioPropostoVO exercicioProposto) {
		this.exercicioProposto = exercicioProposto;
	}

	public List<Integer> getListaDiasSemana() {
		return listaDiasSemana;
	}

	public void setListaDiasSemana(List<Integer> listaDiasSemana) {
		this.listaDiasSemana = listaDiasSemana;
	}

	public ITratadorNegocioService getService() {
		return service;
	}

	public void setService(ITratadorNegocioService service) {
		this.service = service;
	}

	public boolean isEditavel() {
		return editavel;
	}

	public void setEditavel(boolean editavel) {
		this.editavel = editavel;
	}

	public boolean isAprovacao() {
		return aprovacao;
	}

	public void setAprovacao(boolean aprovacao) {
		this.aprovacao = aprovacao;
	}

}
