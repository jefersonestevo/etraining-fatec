package br.com.etraining.client.vo.impl.programatreinamento;

import br.com.etraining.client.vo.interfaces.IVO;

public class ConsultaProgramaTreinamentoVO implements IVO{

	private static final long serialVersionUID = -3127756919225567605L;

	private Long idAluno;

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

}
