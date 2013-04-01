package br.com.etraining.client.vo.impl.programatreinamento;

import br.com.etraining.client.vo.impl.entidades.ProgramaTreinamentoVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class AprovarProgramaTreinamentoVO implements IVO {

	private static final long serialVersionUID = -5043580930889788152L;
	
	private ProgramaTreinamentoVO programaTreinamento;

	public ProgramaTreinamentoVO getProgramaTreinamento() {
		return programaTreinamento;
	}

	public void setProgramaTreinamento(ProgramaTreinamentoVO programaTreinamento) {
		this.programaTreinamento = programaTreinamento;
	}

}
