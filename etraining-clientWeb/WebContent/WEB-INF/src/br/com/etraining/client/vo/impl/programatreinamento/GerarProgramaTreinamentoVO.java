package br.com.etraining.client.vo.impl.programatreinamento;

import br.com.etraining.client.vo.impl.entidades.ProgramaTreinamentoVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class GerarProgramaTreinamentoVO implements IVO {

	private static final long serialVersionUID = 26544324026312535L;

	private ProgramaTreinamentoVO programaTreinamentoAnterior;

	public ProgramaTreinamentoVO getProgramaTreinamentoAnterior() {
		return programaTreinamentoAnterior;
	}

	public void setProgramaTreinamentoAnterior(
			ProgramaTreinamentoVO programaTreinamentoAnterior) {
		this.programaTreinamentoAnterior = programaTreinamentoAnterior;
	}

}
