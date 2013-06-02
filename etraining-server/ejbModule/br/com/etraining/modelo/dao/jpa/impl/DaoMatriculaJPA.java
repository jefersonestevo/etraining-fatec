package br.com.etraining.modelo.dao.jpa.impl;

import java.util.List;

import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.exception.ETrainingException;
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

	@Override
	public boolean existeMatricula(String numero) throws ETrainingException {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT m FROM ");
		query.append(EntMatricula.class.getName() + " AS m ");
		query.append(" WHERE m.numeroMatricula = ? ");

		List<EntMatricula> lista = getTemplate().pesquisarQuery(
				EntMatricula.class, query.toString(), new Object[] { numero });
		return CollectionUtils.isNotEmpty(lista);
	}

}
