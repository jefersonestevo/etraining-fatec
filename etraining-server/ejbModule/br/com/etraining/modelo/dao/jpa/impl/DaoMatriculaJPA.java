package br.com.etraining.modelo.dao.jpa.impl;

import javax.inject.Named;

import br.com.etraining.modelo.dao.interfaces.IDaoMatricula;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntMatricula;

@Named
public class DaoMatriculaJPA extends DaoCRUDJPA<EntMatricula> implements
		IDaoMatricula {

	@Override
	protected Class<EntMatricula> getEntidadePersistente() {
		return EntMatricula.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntMatricula.NOME_ENTIDADE;
	}

}
