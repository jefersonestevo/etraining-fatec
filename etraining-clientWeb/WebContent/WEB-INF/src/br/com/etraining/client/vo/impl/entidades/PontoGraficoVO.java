package br.com.etraining.client.vo.impl.entidades;

import java.util.Date;

import br.com.etraining.client.vo.interfaces.IVO;

public class PontoGraficoVO implements IVO {

	private static final long serialVersionUID = -1126992093868671138L;

	private Date data;
	private Long pontos;

	public Long getPontos() {
		return pontos;
	}

	public void setPontos(Long pontos) {
		this.pontos = pontos;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
