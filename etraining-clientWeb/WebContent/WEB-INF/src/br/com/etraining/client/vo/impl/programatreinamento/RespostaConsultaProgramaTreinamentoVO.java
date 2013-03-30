package br.com.etraining.client.vo.impl.programatreinamento;

import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.impl.entidades.ProgramaTreinamentoVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaProgramaTreinamentoVO implements IVO {

	private static final long serialVersionUID = 2042353938665043867L;

	private AlunoVO aluno;
	private ProgramaTreinamentoVO programaTreinamento = new ProgramaTreinamentoVO();

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
