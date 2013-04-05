package br.com.etraining.modelo.dao.jpa.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.exception.ETrainingException;
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

	@Override
	public EntDiaExercicio pesquisar(Long idUsuario, Date data)
			throws ETrainingException {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT de FROM ");
		query.append(EntDiaExercicio.class.getName() + " AS de ");
		query.append(" WHERE de.aluno.id = ? ");
		query.append(" AND de.dataRealizacao = ? ");

		List<EntDiaExercicio> lista = getTemplate().pesquisarQuery(
				EntDiaExercicio.class, query.toString(),
				new Object[] { idUsuario, data });

		if (CollectionUtils.isNotEmpty(lista))
			return lista.get(0);
		return null;
	}

}
