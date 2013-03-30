package br.com.etraining.client.vo.impl.relatorios.geral;

import java.util.Date;

import br.com.etraining.client.vo.interfaces.IVO;

public class ConsultaEstatisticaIndividualVO implements IVO {

	private static final long serialVersionUID = -688632742490483029L;

	private Long idUsuario;
	private Date dataInicio;
	private Date dataFim;

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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

}
