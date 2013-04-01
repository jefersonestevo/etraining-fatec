package br.com.etraining.modelo.dao.jpa.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicioRealizado;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntExercicioRealizado;
import br.com.etraining.utils.data.DataUtils;

@Named
public class DaoExercicioRealizadoJPA extends DaoCRUDJPA<EntExercicioRealizado>
		implements IDaoExercicioRealizado {

	@Override
	protected Class<EntExercicioRealizado> getEntidadePersistente() {
		return EntExercicioRealizado.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntExercicioRealizado.NOME_ENTIDADE;
	}

	@Override
	public List<EntExercicioRealizado> pesquisarListaPorUsuarioData(
			Long idUsuario, Date data) throws ETrainingException {

		StringBuilder query = new StringBuilder();
		query.append(" SELECT er FROM ");
		query.append(EntExercicioRealizado.class.getName() + " AS er ");
		query.append(" WHERE er.diaExercicio.aluno.id = ? ");
		query.append(" AND er.diaExercicio.dataRealizacao >= ? ");
		query.append(" AND er.diaExercicio.dataRealizacao <= ? ");

		Date dataInicial = DataUtils.getDataInicialDia(data);
		Date dataFinal = DataUtils.getDataFinalDia(data);

		return getTemplate().pesquisarQuery(EntExercicioRealizado.class,
				query.toString(),
				new Object[] { idUsuario, dataInicial, dataFinal });
	}

	@Override
	public List<EntExercicioRealizado> pesquisarListaPorDataExercicioAluno(
			Date dataInicial, Date dataFinal, Long idExercicio, Long idAluno)
			throws ETrainingException {

		StringBuilder query = new StringBuilder();
		List<Object> listaParametros = new ArrayList<Object>();

		query.append(" SELECT er FROM ");
		query.append(EntExercicioRealizado.class.getName() + " AS er ");

		boolean where = false;
		if (dataInicial != null) {
			if (!where) {
				query.append(" WHERE ");
				where = true;
			} else
				query.append(" AND ");

			query.append(" er.diaExercicio.dataRealizacao >= ? ");
			listaParametros.add(dataInicial);
		}

		if (dataFinal != null) {
			if (!where) {
				query.append(" WHERE ");
				where = true;
			} else
				query.append(" AND ");

			query.append(" er.diaExercicio.dataRealizacao <= ? ");
			listaParametros.add(dataFinal);
		}

		if (idExercicio != null) {
			if (!where) {
				query.append(" WHERE ");
				where = true;
			} else
				query.append(" AND ");

			query.append(" er.exercicio.id = ? ");
			listaParametros.add(idExercicio);
		}

		if (idAluno != null) {
			if (!where) {
				query.append(" WHERE ");
				where = true;
			} else
				query.append(" AND ");

			query.append(" er.diaExercicio.aluno.id = ? ");
			listaParametros.add(idAluno);
		}

		return getTemplate().pesquisarQuery(EntExercicioRealizado.class,
				query.toString(), listaParametros.toArray());
	}
}
