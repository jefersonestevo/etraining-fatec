package br.com.etraining.modelo.dao.jpa.impl;

import javax.inject.Named;

import br.com.etraining.modelo.dao.interfaces.IDaoDiaSemana;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntDiaSemana;

@Named
public class DaoDiaSemanaJPA extends DaoCRUDJPA<EntDiaSemana> implements
		IDaoDiaSemana {

	@Override
	protected Class<EntDiaSemana> getEntidadePersistente() {
		return EntDiaSemana.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntDiaSemana.NOME_ENTIDADE;
	}

}
