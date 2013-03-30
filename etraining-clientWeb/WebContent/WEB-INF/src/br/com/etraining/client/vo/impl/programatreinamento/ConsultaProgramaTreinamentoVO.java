package br.com.etraining.client.vo.impl.programatreinamento;

import br.com.etraining.client.vo.interfaces.IVO;

public class ConsultaProgramaTreinamentoVO implements IVO{

	private static final long serialVersionUID = -3127756919225567605L;

	private Long idUsuario;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

}
