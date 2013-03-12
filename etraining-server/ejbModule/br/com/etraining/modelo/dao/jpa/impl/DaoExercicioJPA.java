package br.com.etraining.modelo.dao.jpa.impl;

import br.com.etraining.modelo.dao.interfaces.IDaoExercicio;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntExercicio;

public class DaoExercicioJPA extends DaoCRUDJPA<EntExercicio> implements
		IDaoExercicio {

	@Override
	protected Class<EntExercicio> getEntidadePersistente() {
		return EntExercicio.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntExercicio.NOME_ENTIDADE;
	}

}
