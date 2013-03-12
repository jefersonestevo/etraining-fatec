package br.com.etraining.modelo.dao.jpa.impl;

import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

public class DaoProgramaTreinamentoJPA extends
		DaoCRUDJPA<EntProgramaTreinamento> implements IDaoProgramaTreinamento {

	@Override
	protected Class<EntProgramaTreinamento> getEntidadePersistente() {
		return EntProgramaTreinamento.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntProgramaTreinamento.NOME_ENTIDADE;
	}

}
