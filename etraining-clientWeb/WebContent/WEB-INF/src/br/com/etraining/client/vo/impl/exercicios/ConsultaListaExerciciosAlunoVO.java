package br.com.etraining.client.vo.impl.exercicios;

import java.util.Date;

import br.com.etraining.client.vo.interfaces.IVO;

public class ConsultaListaExerciciosAlunoVO implements IVO {

	private static final long serialVersionUID = -2973839990422043975L;

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
