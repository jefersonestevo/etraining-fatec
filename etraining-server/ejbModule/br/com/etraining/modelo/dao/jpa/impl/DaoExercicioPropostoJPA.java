package br.com.etraining.modelo.dao.jpa.impl;

import br.com.etraining.modelo.dao.interfaces.IDaoExercicioProposto;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntExercicioProposto;

public class DaoExercicioPropostoJPA extends DaoCRUDJPA<EntExercicioProposto>
		implements IDaoExercicioProposto {

	@Override
	protected Class<EntExercicioProposto> getEntidadePersistente() {
		return EntExercicioProposto.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntExercicioProposto.NOME_ENTIDADE;
	}

}
