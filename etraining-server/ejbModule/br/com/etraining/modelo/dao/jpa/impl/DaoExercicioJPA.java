package br.com.etraining.modelo.dao.jpa.impl;

import java.util.List;

import javax.inject.Named;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicio;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.utils.StringFakeUtils;

@Named
public class DaoExercicioJPA extends DaoCRUDJPA<EntExercicio> implements
		IDaoExercicio {

	@Override
	protected Class<EntExercicio> getEntidadePersistente() {
		return EntExercicio.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntExercicio.NOME_ENTIDADE;
	}

	public EntExercicio pesquisar(Long id) throws ETrainingException {
		EntExercicio exercicio = getTemplate().pesquisar(
				getEntidadePersistente(), id);

		StringFakeUtils.replaceCaracteres(exercicio, "titulo");
		if (exercicio.getCategoriaExercicio() != null) {
			StringFakeUtils.replaceCaracteres(
					exercicio.getCategoriaExercicio(), "titulo");
		}

		return exercicio;
	}

	public List<EntExercicio> pesquisarLista() throws ETrainingException {
		List<EntExercicio> lista = getTemplate().pesquisarLista(
				getEntidadePersistente());

		for (EntExercicio exercicio : lista) {
			StringFakeUtils.replaceCaracteres(exercicio, "titulo");
			if (exercicio.getCategoriaExercicio() != null) {
				StringFakeUtils.replaceCaracteres(
						exercicio.getCategoriaExercicio(), "titulo");
			}
		}

		return lista;
	}

}
