package br.com.etraining.client.vo.impl.aluno;

import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class InsereAlunoVO implements IVO {

	private static final long serialVersionUID = 942308845653967531L;

	private AlunoVO aluno;

	public AlunoVO getAluno() {
		return aluno;
	}

	public void setAluno(AlunoVO aluno) {
		this.aluno = aluno;
	}

}
