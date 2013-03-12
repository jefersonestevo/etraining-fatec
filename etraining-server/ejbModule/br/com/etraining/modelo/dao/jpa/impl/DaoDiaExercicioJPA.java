package br.com.etraining.modelo.dao.jpa.impl;

import br.com.etraining.modelo.dao.interfaces.IDaoDiaExercicio;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntDiaExercicio;

public class DaoDiaExercicioJPA extends DaoCRUDJPA<EntDiaExercicio> implements
		IDaoDiaExercicio {

	@Override
	protected Class<EntDiaExercicio> getEntidadePersistente() {
		return EntDiaExercicio.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntDiaExercicio.NOME_ENTIDADE;
	}

}
