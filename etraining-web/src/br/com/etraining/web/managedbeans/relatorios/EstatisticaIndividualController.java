package br.com.etraining.web.managedbeans.relatorios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;

import br.com.etraining.client.vo.impl.entidades.AlunoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.impl.entidades.PontoGraficoVO;
import br.com.etraining.client.vo.impl.relatorios.geral.ConsultaEstatisticaIndividualVO;
import br.com.etraining.client.vo.impl.relatorios.geral.RespostaConsultaEstatisticaVO;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@SessionScoped
public class EstatisticaIndividualController extends EtrainingManagedBean {

	private static final long serialVersionUID = -6032506735894603834L;

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
		preencherListaExercicio();
		preencherListaAluno();

		if (possuiResultado) {
			pesquisar();
		}

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
		// TODO - Fazer o EJB Funcionar
		this.listaExercicios.add(new SelectItem("", ""));
		this.listaExercicios.clear();
		for (int i = 0; i < 10; i++) {
			this.listaExercicios.add(new SelectItem(new Long(i), "EXERCICIO "
					+ i));
		}
	}

	public void preencherListaAluno() {
		this.listaAlunos.clear();
		this.listaAlunosFiltrados.clear();

		// TODO - Fazer EJB Funcionar

		for (int i = 0; i < 20; i++) {
			AlunoSimplesVO aluno = new AlunoSimplesVO();
			aluno.setId(new Long(i));
			aluno.setNome("ALUNO " + i);
			aluno.setMatricula("1010M" + i);
			this.listaAlunos.add(aluno);
		}
		this.listaAlunosFiltrados.addAll(this.listaAlunos);
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

}
