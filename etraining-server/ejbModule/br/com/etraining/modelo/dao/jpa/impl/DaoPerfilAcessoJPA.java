package br.com.etraining.modelo.dao.jpa.impl;

import javax.inject.Named;

import br.com.etraining.modelo.dao.interfaces.IDaoPerfilAcesso;
import br.com.etraining.modelo.def.impl.jpa.DaoPesquisaJPA;
import br.com.etraining.modelo.entidades.EntPerfilAcesso;

@Named
public class DaoPerfilAcessoJPA extends DaoPesquisaJPA<EntPerfilAcesso>
		implements IDaoPerfilAcesso {

	@Override
	protected Class<EntPerfilAcesso> getEntidadePersistente() {
		return EntPerfilAcesso.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntPerfilAcesso.NOME_ENTIDADE;
	}

}
