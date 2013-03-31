package br.com.etraining.client.vo.impl.aluno;

import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class AlteraAlunoVO implements IVO {

	private static final long serialVersionUID = -4495382229946091802L;

	private AlunoVO aluno;

	public AlunoVO getAluno() {
		return aluno;
	}

	public void setAluno(AlunoVO aluno) {
		this.aluno = aluno;
	}

}
