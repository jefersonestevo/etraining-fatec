package br.com.etraining.modelo.dao.jpa.impl;

import javax.inject.Named;

import br.com.etraining.modelo.dao.interfaces.IDaoDiaExercicio;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntDiaExercicio;

@Named
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
