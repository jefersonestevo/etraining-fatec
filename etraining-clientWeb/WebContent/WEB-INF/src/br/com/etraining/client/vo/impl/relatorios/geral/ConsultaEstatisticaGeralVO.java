package br.com.etraining.client.vo.impl.relatorios.geral;

import java.util.Date;

import br.com.etraining.client.vo.interfaces.IVO;

public class ConsultaEstatisticaGeralVO implements IVO {

	private static final long serialVersionUID = -688632742490483029L;

	private Long idExercicio;
	private Integer tipoConsulta = ConsultaEstatisticaConstants.CONSULTA_POR_PONTOS;
	private Date dataInicio;
	private Date dataFim;

	public Long getIdExercicio() {
		return idExercicio;
	}

	public void setIdExercicio(Long idExercicio) {
		this.idExercicio = idExercicio;
	}

	public Integer getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(Integer tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
