package br.com.etraining.modelo.dao.jpa.impl;

import javax.inject.Named;

import br.com.etraining.modelo.dao.interfaces.IDaoParametros;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntParametros;

@Named
public class DaoParametrosJPA extends DaoCRUDJPA<EntParametros> implements
		IDaoParametros {

	@Override
	protected Class<EntParametros> getEntidadePersistente() {
		return EntParametros.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntParametros.NOME_ENTIDADE;
	}

}
