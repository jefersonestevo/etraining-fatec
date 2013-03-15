package br.com.etraining.modelo.dao.jpa.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicioRealizado;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntExercicioRealizado;

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
		// TODO - Implementar metodo pesquisarListaPorUsuarioData do metodo
		// DaoExercicioRealizadoJPA
		return null;
	}

}
