package br.com.etraining.modelo.dao.jpa.impl;

import javax.inject.Named;

import br.com.etraining.modelo.dao.interfaces.IDaoAtividade;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntAtividade;

@Named
public class DaoAtividadeJPA extends DaoCRUDJPA<EntAtividade> implements
		IDaoAtividade {

	@Override
	protected Class<EntAtividade> getEntidadePersistente() {
		return EntAtividade.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntAtividade.NOME_ENTIDADE;
	}

}
