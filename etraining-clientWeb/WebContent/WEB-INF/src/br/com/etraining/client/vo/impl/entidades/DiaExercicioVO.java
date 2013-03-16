package br.com.etraining.client.vo.impl.entidades;

import java.util.Date;

import br.com.etraining.client.vo.interfaces.IVO;

public class DiaExercicioVO implements IVO {

	private static final long serialVersionUID = -5356662099036900016L;
	
	private Long id;
	private Date data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
