package br.com.etraining.web.managedbeans.relatorios;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.relatorios.geral.ConsultaEstatisticaConstants;
import br.com.etraining.client.vo.impl.relatorios.geral.ConsultaEstatisticaGeralVO;
import br.com.etraining.client.vo.impl.relatorios.geral.RespostaConsultaEstatisticaVO;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.ITratadorNegocioService;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@SessionScoped
public class EstatisticaGeralController extends EtrainingManagedBean {

	private static final long serialVersionUID = -6032506735894603834L;

	@Inject
	private ITratadorNegocioService service;

	@Inject
	private GeradorGraficoEstatistico geradorGrafico;

	private ConsultaEstatisticaGeralVO consulta = new ConsultaEstatisticaGeralVO();
	private RespostaConsultaEstatisticaVO resposta = new RespostaConsultaEstatisticaVO();

	private boolean possuiResultado = false;
	private List<SelectItem> listaTiposConsulta = new ArrayList<SelectItem>();
	private List<SelectItem> listaExercicios = new ArrayList<SelectItem>();
	private CartesianChartModel grafico;
	private String tituloGrafico;

	@PostConstruct
	public void inicializar() {
		listaTiposConsulta = new ArrayList<SelectItem>();
		listaTiposConsulta.add(new SelectItem(
				ConsultaEstatisticaConstants.CONSULTA_POR_PONTOS,
				getMessage("Consulta_Por_Pontos")));
		listaTiposConsulta.add(new SelectItem(
				ConsultaEstatisticaConstants.CONSULTA_POR_EXERCICIOS,
				getMessage("Consulta_Por_Exercicio")));
	}

	public String irParaTelaPesquisa() {
		preencherListaExercicio();

		if (possuiResultado) {
			pesquisar();
		}

		return "/pages/relatorios/estatisticaGeral.xhtml";
	}

	public void pesquisar() {

		// ExercicioVO exercicio = new ExercicioVO();
		// exercicio.setTitulo("EXERCICIO 01");
		// resposta.setExercicio(exercicio);
		// resposta.setTipoGrafico(ConsultaEstatisticaConstants.CONSULTA_POR_EXERCICIOS);
		// resposta.setListaPontosReais(new ArrayList<PontoGraficoVO>());
		//
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date());
		// cal.add(Calendar.DATE, -30);
		//
		// Calendar calFinal = Calendar.getInstance();
		// calFinal.setTime(new Date());
		// calFinal.add(Calendar.DATE, 60);
		//
		// while (cal.before(calFinal)) {
		// cal.add(Calendar.DATE, 7);
		// PontoGraficoVO ponto = new PontoGraficoVO(cal.getTime());
		// ponto.setPontos(new Double(Math.random() * 500).longValue());
		// resposta.getListaPontosReais().add(ponto);
		// }
		//
		// cal = Calendar.getInstance();
		// cal.setTime(new Date());
		// cal.add(Calendar.DATE, -30);
		//
		// while (cal.before(calFinal)) {
		// cal.add(Calendar.DATE, 7);
		// PontoGraficoVO ponto = new PontoGraficoVO(cal.getTime());
		// ponto.setPontos(new Long(new Double(Math.random() *
		// 500).intValue()));
		// resposta.getListaPontosPropostos().add(ponto);
		// }

		try {
			resposta = (RespostaConsultaEstatisticaVO) service
					.executa(consulta);

			if (ConsultaEstatisticaConstants.CONSULTA_POR_PONTOS
					.equals(resposta.getTipoGrafico())) {
				setTituloGrafico(getMessage("Grafico_Geral_Por_Pontos"));
			} else if (ConsultaEstatisticaConstants.CONSULTA_POR_EXERCICIOS
					.equals(resposta.getTipoGrafico())) {
				setTituloGrafico(getMessage("Grafico_Geral_Por_Exercicio")
						+ resposta.getExercicio().getTitulo());
			}

			this.grafico = geradorGrafico.gerarGraficoCartesiano(resposta);
			this.possuiResultado = true;
		} catch (ViewException e) {
			addExceptionMessage(e);
			this.possuiResultado = false;
		}
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

	public List<SelectItem> getListaTiposConsulta() {
		return listaTiposConsulta;
	}

	public void setListaTiposConsulta(List<SelectItem> listaTiposConsulta) {
		this.listaTiposConsulta = listaTiposConsulta;
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

	public ConsultaEstatisticaGeralVO getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaEstatisticaGeralVO consulta) {
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

	public ITratadorNegocioService getService() {
		return service;
	}

	public void setService(ITratadorNegocioService service) {
		this.service = service;
	}

	public GeradorGraficoEstatistico getGeradorGrafico() {
		return geradorGrafico;
	}

	public void setGeradorGrafico(GeradorGraficoEstatistico geradorGrafico) {
		this.geradorGrafico = geradorGrafico;
	}

}
