package br.com.etraining.web.managedbeans.relatorios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;

import br.com.etraining.client.vo.impl.aluno.ConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.AlunoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.entidades.PontoGraficoVO;
import br.com.etraining.client.vo.impl.listaexercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.listaexercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.relatorios.geral.ConsultaEstatisticaIndividualVO;
import br.com.etraining.client.vo.impl.relatorios.geral.RespostaConsultaEstatisticaVO;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.ITratadorNegocioService;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;
import br.com.etraining.web.utils.comparador.ComparadorAlunoSimplesAlfabetico;

@Named
@SessionScoped
public class EstatisticaIndividualController extends EtrainingManagedBean {

	private static final long serialVersionUID = -6032506735894603834L;

	@Inject
	private ITratadorNegocioService service;

	private ConsultaEstatisticaIndividualVO consulta = new ConsultaEstatisticaIndividualVO();
	private RespostaConsultaEstatisticaVO resposta = new RespostaConsultaEstatisticaVO();

	private boolean possuiResultado = false;
	private List<SelectItem> listaExercicios = new ArrayList<SelectItem>();
	private CartesianChartModel grafico;
	private String tituloGrafico;

	private List<AlunoSimplesVO> listaAlunos = new ArrayList<AlunoSimplesVO>();
	private List<AlunoSimplesVO> listaAlunosFiltrados = new ArrayList<AlunoSimplesVO>();
	private AlunoSimplesVO alunoSelecionado = new AlunoSimplesVO();

	public String irParaTelaPesquisa() {
		resposta = new RespostaConsultaEstatisticaVO();
		this.alunoSelecionado = new AlunoSimplesVO();
		this.grafico = null;
		this.tituloGrafico = null;
		this.possuiResultado = false;

		preencherListaExercicio();
		preencherListaAluno();

		return "/pages/relatorios/estatisticaIndividual.xhtml";
	}

	public void pesquisar() {
		boolean hasError = false;
		if (alunoSelecionado == null || alunoSelecionado.getId() == null) {
			addErrorMessage(getMessage("Selecao_Aluno_Obrigatoria"));
			hasError = true;
		}
		if (hasError)
			return;

		this.possuiResultado = true;

		AlunoVO aluno = new AlunoVO();
		aluno.setNome("ALUNO TESTE 01");
		resposta.setAluno(aluno);
		resposta.setListaPontos(new ArrayList<PontoGraficoVO>());

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -30);

		Calendar calFinal = Calendar.getInstance();
		calFinal.setTime(new Date());
		calFinal.add(Calendar.DATE, 60);

		while (cal.before(calFinal)) {
			cal.add(Calendar.DATE, 7);
			PontoGraficoVO ponto = new PontoGraficoVO();
			ponto.setData(cal.getTime());
			ponto.setPontos(new Long(new Double(Math.random() * 500).intValue()));
			resposta.getListaPontos().add(ponto);
		}
		// TODO - Fazer EJB Funcionar

		setTituloGrafico(getMessage("Grafico_Individual_Label")
				+ resposta.getAluno().getNome());

		this.grafico = GeradorGraficoEstatistico.gerarGraficoCartesiano(
				resposta.getListaPontos(), null);
	}

	public void preencherListaExercicio() {
		RespostaConsultaListaExerciciosVO resp = null;
		try {
			resp = (RespostaConsultaListaExerciciosVO) service
					.executa(new ConsultaListaExerciciosVO());

			this.listaExercicios.clear();
			this.listaExercicios.add(new SelectItem("", ""));
			for (ExercicioVO exerc : resp.getListaExercicios()) {
				this.listaExercicios.add(new SelectItem(exerc.getId(), exerc
						.getTitulo()));
			}
		} catch (ViewException e) {
			addExceptionMessage(e);
		}
	}

	public void preencherListaAluno() {
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

	public List<SelectItem> getListaExercicios() {
		return listaExercicios;
	}

	public void setListaExercicios(List<SelectItem> listaExercicios) {
		this.listaExercicios = listaExercicios;
	}

	public boolean getPossuiResultado() {
		return possuiResultado;
	}

	public void setPossuiResultado(boolean possuiResultado) {
		this.possuiResultado = possuiResultado;
	}

	public ConsultaEstatisticaIndividualVO getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaEstatisticaIndividualVO consulta) {
		this.consulta = consulta;
	}

	public CartesianChartModel getGrafico() {
		return grafico;
	}

	public void setGrafico(CartesianChartModel grafico) {
		this.grafico = grafico;
	}

	public String getTituloGrafico() {
		return tituloGrafico;
	}

	public void setTituloGrafico(String tituloGrafico) {
		this.tituloGrafico = tituloGrafico;
	}

	public RespostaConsultaEstatisticaVO getResposta() {
		return resposta;
	}

	public void setResposta(RespostaConsultaEstatisticaVO resposta) {
		this.resposta = resposta;
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

	public ITratadorNegocioService getService() {
		return service;
	}

	public void setService(ITratadorNegocioService service) {
		this.service = service;
	}

}
