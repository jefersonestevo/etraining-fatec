package br.com.etraining.client.vo.impl.programatreinamento;

import br.com.etraining.client.vo.impl.entidades.ProgramaTreinamentoVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaProgramaTreinamentoVO implements IVO {

	private static final long serialVersionUID = 2042353938665043867L;

	private ProgramaTreinamentoVO programaTreinamento = new ProgramaTreinamentoVO();

	public ProgramaTreinamentoVO getProgramaTreinamento() {
		return programaTreinamento;
	}

	public void setProgramaTreinamento(ProgramaTreinamentoVO programaTreinamento) {
		this.programaTreinamento = programaTreinamento;
	}

}
