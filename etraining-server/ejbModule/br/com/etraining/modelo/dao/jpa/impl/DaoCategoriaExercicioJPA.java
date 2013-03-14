package br.com.etraining.modelo.dao.jpa.impl;

import javax.inject.Named;

import br.com.etraining.modelo.dao.interfaces.IDaoCategoriaExercicio;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntCategoriaExercicio;

@Named
public class DaoCategoriaExercicioJPA extends DaoCRUDJPA<EntCategoriaExercicio>
		implements IDaoCategoriaExercicio {

	@Override
	protected Class<EntCategoriaExercicio> getEntidadePersistente() {
		return EntCategoriaExercicio.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntCategoriaExercicio.NOME_ENTIDADE;
	}

}
