package br.com.etraining.client.vo.impl.aluno;

import br.com.etraining.client.vo.interfaces.IVO;

public class ConsultaAlunoVO implements IVO {

	private static final long serialVersionUID = 7967064838839318489L;

	private Long idAluno;
	private String matricula;

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
