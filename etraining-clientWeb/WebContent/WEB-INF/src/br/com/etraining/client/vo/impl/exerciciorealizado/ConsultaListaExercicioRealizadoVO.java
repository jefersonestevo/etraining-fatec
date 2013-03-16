package br.com.etraining.client.vo.impl.exerciciorealizado;

import java.util.Date;

import br.com.etraining.client.vo.interfaces.IVO;

public class ConsultaListaExercicioRealizadoVO implements IVO {

	private static final long serialVersionUID = -8376017526832201817L;

	private Long idAluno;
	private Date data;

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
