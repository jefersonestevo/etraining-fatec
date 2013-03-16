package br.com.etraining.client.vo.impl.exerciciorealizado;

import java.util.Date;

import br.com.etraining.client.vo.interfaces.IVO;

public class RemocaoExercicioRealizadoVO implements IVO {

	private static final long serialVersionUID = 419528993840836597L;

	private Long idAluno;
	private Long idExercicioRealizado;
	private Date data;

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

	public Long getIdExercicioRealizado() {
		return idExercicioRealizado;
	}

	public void setIdExercicioRealizado(Long idExercicioRealizado) {
		this.idExercicioRealizado = idExercicioRealizado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
