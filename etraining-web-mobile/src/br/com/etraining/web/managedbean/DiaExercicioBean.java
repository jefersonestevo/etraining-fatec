package br.com.etraining.web.managedbean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.DiaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioRealizadoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.ConsultaListaExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.InsercaoExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RemocaoExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RespostaConsultaListaExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.impl.TratadorNegocioService;

@Named
@SessionScoped
public class DiaExercicioBean extends EtrainingManagedBean {

	private static final long serialVersionUID = -3793222987299513289L;

	@Inject
	private TratadorNegocioService service;

	@Inject
	private SessionBean sessionBean;

	private Date diaSelecionado;
	private Date diaSelecionadoTemp;
	private List<ExercicioRealizadoSimplesVO> listaExercicioRealizado = new ArrayList<ExercicioRealizadoSimplesVO>();
	private List<ExercicioVO> listaExercicio = new ArrayList<ExercicioVO>();
	private Long idExercicioRemocao;
	private Long idExercicioInsercao;
	private Integer quantidadeAtividade;
	private String tituloAtividade;

	public String irParaTelaSelecionarDiaExercicio() {
		selecionarDia();
		return "/pages/listaDiaExercicio.jsf";
	}

	public void selecionarDia() {
		diaSelecionado = diaSelecionadoTemp;

		try {
			ConsultaListaExercicioRealizadoVO consulta = new ConsultaListaExercicioRealizadoVO();
			consulta.setData(diaSelecionado);
			consulta.setIdAluno(sessionBean.getIdAluno());

			RespostaConsultaListaExercicioRealizadoVO resposta = (RespostaConsultaListaExercicioRealizadoVO) service
					.executa(consulta);

			this.listaExercicioRealizado = resposta
					.getListaExercicioRealizado();
		} catch (ViewException e) {
			addExceptionMessage(e);
		}
	}

	public void fecharSelecaoDia() {
		diaSelecionadoTemp = diaSelecionado;
	}

	public String voltarTelaDiaExercicio() {
		return "/pages/listaDiaExercicio.jsf";
	}

	public String irParaTelaInserirExercicio() {

		try {
			RespostaConsultaListaExerciciosVO respExercicios = (RespostaConsultaListaExerciciosVO) service
					.executa(new ConsultaListaExerciciosVO());
			this.listaExercicio = respExercicios.getListaExercicios();
		} catch (ViewException e) {
			addExceptionMessage(e);
		}

		return "/pages/listaExercicio.jsf";
	}

	public void preencherExercicio(Long idExercicio, String tituloAtividade) {
		this.idExercicioInsercao = idExercicio;
		this.tituloAtividade = tituloAtividade;
	}

	public String inserirExercicio() {
		try {
			InsercaoExercicioRealizadoVO insercao = new InsercaoExercicioRealizadoVO();
			insercao.setIdAluno(sessionBean.getIdAluno());
			DiaExercicioVO dia = new DiaExercicioVO();
			dia.setData(diaSelecionado);
			insercao.setDiaExercicio(dia);
			insercao.setIdExercicio(idExercicioInsercao);
			insercao.setQuantidadeAtividade(quantidadeAtividade);

			RespostaConsultaListaExercicioRealizadoVO resposta = (RespostaConsultaListaExercicioRealizadoVO) service
					.executa(insercao);
			this.listaExercicioRealizado = resposta
					.getListaExercicioRealizado();
			this.quantidadeAtividade = null;
		} catch (ViewException e) {
			addExceptionMessage(e);
		}
		return "/pages/listaDiaExercicio.jsf";
	}

	public String removerExercicio() {
		try {
			RemocaoExercicioRealizadoVO remocao = new RemocaoExercicioRealizadoVO();
			remocao.setData(diaSelecionado);
			remocao.setIdAluno(sessionBean.getIdAluno());
			remocao.setIdExercicioRealizado(getIdExercicioRemocao());

			RespostaConsultaListaExercicioRealizadoVO resposta = (RespostaConsultaListaExercicioRealizadoVO) service
					.executa(remocao);
			this.listaExercicioRealizado = resposta
					.getListaExercicioRealizado();
		} catch (ViewException e) {
			addExceptionMessage(e);
		}
		return "/pages/listaDiaExercicio.jsf";
	}

	public String getDiaSelecionadoStr() {
		if (diaSelecionado == null)
			return null;
		return new SimpleDateFormat("dd/MM/yyyy").format(diaSelecionado);
	}

	public Date getDiaSelecionado() {
		return diaSelecionado;
	}

	public void setDiaSelecionado(Date diaSelecionado) {
		this.diaSelecionado = diaSelecionado;
		this.diaSelecionadoTemp = diaSelecionado;
	}

	public List<ExercicioRealizadoSimplesVO> getListaExercicioRealizado() {
		return listaExercicioRealizado;
	}

	public void setListaExercicioRealizado(
			List<ExercicioRealizadoSimplesVO> listaExercicioRealizado) {
		this.listaExercicioRealizado = listaExercicioRealizado;
	}

	public List<ExercicioVO> getListaExercicio() {
		return listaExercicio;
	}

	public void setListaExercicio(List<ExercicioVO> listaExercicio) {
		this.listaExercicio = listaExercicio;
	}

	public Date getDiaSelecionadoTemp() {
		return diaSelecionadoTemp;
	}

	public void setDiaSelecionadoTemp(Date diaSelecionadoTemp) {
		this.diaSelecionadoTemp = diaSelecionadoTemp;
	}

	public Long getIdExercicioRemocao() {
		return idExercicioRemocao;
	}

	public void setIdExercicioRemocao(Long idExercicioRemocao) {
		this.idExercicioRemocao = idExercicioRemocao;
	}

	public Long getIdExercicioInsercao() {
		return idExercicioInsercao;
	}

	public void setIdExercicioInsercao(Long idExercicioInsercao) {
		this.idExercicioInsercao = idExercicioInsercao;
	}

	public Integer getQuantidadeAtividade() {
		return quantidadeAtividade;
	}

	public void setQuantidadeAtividade(Integer quantidadeAtividade) {
		this.quantidadeAtividade = quantidadeAtividade;
	}

	public String getTituloAtividade() {
		return tituloAtividade;
	}

	public void setTituloAtividade(String tituloAtividade) {
		this.tituloAtividade = tituloAtividade;
	}

}
