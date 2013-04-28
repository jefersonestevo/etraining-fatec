package br.com.etraining.web.managedbeans.programatreinamento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.impl.aluno.ConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.diasemana.ConsultaDiasSemanaVO;
import br.com.etraining.client.vo.impl.diasemana.RespostaConsultaDiasSemanaVO;
import br.com.etraining.client.vo.impl.entidades.AlunoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.DiaSemanaVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioPropostoVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.ConsultaExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.programatreinamento.AprovarProgramaTreinamentoVO;
import br.com.etraining.client.vo.impl.programatreinamento.ConsultaProgramaTreinamentoAprovacaoVO;
import br.com.etraining.client.vo.impl.programatreinamento.ConsultaProgramaTreinamentoVO;
import br.com.etraining.client.vo.impl.programatreinamento.GerarProgramaTreinamentoVO;
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
	private ExercicioVO exercicio = new ExercicioVO();
	private Integer quantidadeSugerida;
	private List<String> listaDiasSemana = new ArrayList<String>();
	private boolean editavel = true;
	private boolean aprovacao = false;

	public String irParaTelaPesquisa() {
		alunoSelecionado = new AlunoSimplesVO();
		preencherListaAluno();
		limparForm();
		return "/pages/programaTreinamento/programaTreinamentoPesquisa.xhtml";
	}

	public String irParaTelaAprovacao() {
		boolean hasError = validaCampos();
		if (hasError)
			return null;

		try {
			ConsultaProgramaTreinamentoAprovacaoVO consulta = new ConsultaProgramaTreinamentoAprovacaoVO();
			consulta.setIdAluno(alunoSelecionado.getId());
			resposta = (RespostaConsultaProgramaTreinamentoVO) service
					.executa(consulta);
			preparaTela(false, true);
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}

		return "/pages/programaTreinamento/programaTreinamentoEdicao.xhtml";
	}

	public String aprovar() {
		try {
			AprovarProgramaTreinamentoVO aprovar = new AprovarProgramaTreinamentoVO();
			aprovar.setProgramaTreinamento(resposta.getProgramaTreinamento());

			resposta = (RespostaConsultaProgramaTreinamentoVO) service
					.executa(aprovar);
			showSuccessMessage();
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}
		return irParaTelaPesquisa();
	}

	public String irParaTelaGeracao() {
		boolean hasError = validaCampos();
		if (hasError)
			return null;

		try {
			ConsultaProgramaTreinamentoVO consulta = new ConsultaProgramaTreinamentoVO();
			consulta.setIdAluno(alunoSelecionado.getId());
			resposta = (RespostaConsultaProgramaTreinamentoVO) service
					.executa(consulta);
			preparaTela(true, false);
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}

		return "/pages/programaTreinamento/programaTreinamentoEdicao.xhtml";
	}

	public String gerar() {
		try {
			GerarProgramaTreinamentoVO gerar = new GerarProgramaTreinamentoVO();
			gerar.setProgramaTreinamentoAnterior(resposta
					.getProgramaTreinamento());

			resposta = (RespostaConsultaProgramaTreinamentoVO) service
					.executa(gerar);
			preparaTela(true, false);
			showSuccessMessage();
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

	private void preparaTela(boolean editavel, boolean aprovacao)
			throws ViewException {
		if (resposta.getProgramaTreinamento() == null
				|| resposta.getProgramaTreinamento().getId() == null) {
			throw new ViewException(
					CodigoExcecao.PROGRAMA_TREINAMENTO_INEXISTENTE);
		}

		this.exercicio = new ExercicioVO();
		Collections.sort(resposta.getProgramaTreinamento()
				.getListaExercicioProposto(),
				new ComparadorExercicioPropostoPorDiaSemana());

		preencherListaExercicio();

		this.editavel = editavel;
		this.aprovacao = aprovacao;
	}

	public void adicionarExercicio() {
		boolean hasError = false;
		if (getExercicio() == null || getExercicio().getId() == null
				|| new Long(0l).equals(getExercicio().getId())) {
			addErrorMessage(getMessage("Error_Selecao_Exercicio_Adicionar_Exercicio_Proposto"));
			hasError = true;
		}
		if (CollectionUtils.isEmpty(getListaDiasSemana())) {
			addErrorMessage(getMessage("Error_Selecao_Dias_Adicionar_Exercicio_Proposto"));
			hasError = true;
		}
		if (getQuantidadeSugerida() == null || getQuantidadeSugerida() < 1) {
			addErrorMessage(getMessage("Error_Selecao_Quantidade_Sugerida_Exercicio_Proposto"));
			hasError = true;
		}
		if (hasError)
			return;

		try {
			ConsultaExercicioVO cons = new ConsultaExercicioVO();
			cons.setId(getExercicio().getId());
			RespostaConsultaExercicioVO resp = (RespostaConsultaExercicioVO) service
					.executa(cons);

			RespostaConsultaDiasSemanaVO respDS = (RespostaConsultaDiasSemanaVO) service
					.executa(new ConsultaDiasSemanaVO());
			Map<Long, DiaSemanaVO> mapDiasSemana = new HashMap<Long, DiaSemanaVO>();
			for (DiaSemanaVO dia : respDS.getListaDiasSemana()) {
				mapDiasSemana.put(dia.getId(), dia);
			}

			for (String dia : getListaDiasSemana()) {
				ExercicioPropostoVO exerc = new ExercicioPropostoVO();
				DiaSemanaVO diaSemana = mapDiasSemana.get(Long.parseLong(dia));

				exerc.setExercicio(resp.getExercicio());
				exerc.setDiaSemana(diaSemana);
				exerc.setQuantidadeExercicioSugerida(getQuantidadeSugerida());

				if (exerc.getExercicio() == null
						|| exerc.getDiaSemana() == null
						|| resposta.getProgramaTreinamento()
								.getListaExercicioProposto().contains(exerc)) {
					continue;
				}

				resposta.getProgramaTreinamento().getListaExercicioProposto()
						.add(exerc);
			}

			this.setExercicio(new ExercicioVO());
			this.setListaDiasSemana(new ArrayList<String>());
			this.setQuantidadeSugerida(null);

			Collections.sort(resposta.getProgramaTreinamento()
					.getListaExercicioProposto(),
					new ComparadorExercicioPropostoPorDiaSemana());
		} catch (ViewException e) {
			addExceptionMessage(e);
			return;
		}
	}

	public void removerExercicio() {
		Long idExercicio = getLongParameter("idExercicio");
		Long idDiaSemana = getLongParameter("idDiaSemana");

		ExercicioPropostoVO exercicioRemocao = new ExercicioPropostoVO();
		exercicioRemocao.setExercicio(new ExercicioVO(idExercicio));
		exercicioRemocao.setDiaSemana(new DiaSemanaVO(idDiaSemana));
		resposta.getProgramaTreinamento().getListaExercicioProposto()
				.remove(exercicioRemocao);

		Collections.sort(resposta.getProgramaTreinamento()
				.getListaExercicioProposto(),
				new ComparadorExercicioPropostoPorDiaSemana());
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

	public List<String> getListaDiasSemana() {
		return listaDiasSemana;
	}

	public void setListaDiasSemana(List<String> listaDiasSemana) {
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

	public ExercicioVO getExercicio() {
		return exercicio;
	}

	public void setExercicio(ExercicioVO exercicio) {
		this.exercicio = exercicio;
	}

	public Integer getQuantidadeSugerida() {
		return quantidadeSugerida;
	}

	public void setQuantidadeSugerida(Integer quantidadeSugerida) {
		this.quantidadeSugerida = quantidadeSugerida;
	}

}
