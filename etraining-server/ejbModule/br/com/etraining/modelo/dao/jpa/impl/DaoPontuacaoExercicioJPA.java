package br.com.etraining.modelo.dao.jpa.impl;

import javax.inject.Named;

import br.com.etraining.modelo.dao.interfaces.IDaoPontuacaoExercicio;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntPontuacaoExercicio;

@Named
public class DaoPontuacaoExercicioJPA extends DaoCRUDJPA<EntPontuacaoExercicio>
		implements IDaoPontuacaoExercicio {

	@Override
	protected Class<EntPontuacaoExercicio> getEntidadePersistente() {
		return EntPontuacaoExercicio.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntPontuacaoExercicio.NOME_ENTIDADE;
	}

}
