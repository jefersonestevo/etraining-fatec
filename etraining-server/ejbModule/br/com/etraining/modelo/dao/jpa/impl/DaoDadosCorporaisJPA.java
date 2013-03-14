package br.com.etraining.modelo.dao.jpa.impl;

import javax.inject.Named;

import br.com.etraining.modelo.dao.interfaces.IDaoDadosCorporais;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntDadosCorporais;

@Named
public class DaoDadosCorporaisJPA extends DaoCRUDJPA<EntDadosCorporais>
		implements IDaoDadosCorporais {

	@Override
	protected Class<EntDadosCorporais> getEntidadePersistente() {
		return EntDadosCorporais.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntDadosCorporais.NOME_ENTIDADE;
	}

}
