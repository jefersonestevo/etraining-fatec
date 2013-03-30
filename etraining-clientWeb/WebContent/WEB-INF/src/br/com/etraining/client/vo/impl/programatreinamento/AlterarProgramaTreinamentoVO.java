package br.com.etraining.client.vo.impl.programatreinamento;

import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.impl.entidades.ProgramaTreinamentoVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class AlterarProgramaTreinamentoVO implements IVO {

	private static final long serialVersionUID = 2656788434026312535L;

	private AlunoVO aluno;
	private ProgramaTreinamentoVO programaTreinamento;

	public AlunoVO getAluno() {
		return aluno;
	}

	public void setAluno(AlunoVO aluno) {
		this.aluno = aluno;
	}

	public ProgramaTreinamentoVO getProgramaTreinamento() {
		return programaTreinamento;
	}

	public void setProgramaTreinamento(ProgramaTreinamentoVO programaTreinamento) {
		this.programaTreinamento = programaTreinamento;
	}
}
