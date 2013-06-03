package br.com.etraining.modelo.dao.jpa.impl;

import java.util.List;

import javax.inject.Named;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoCategoriaExercicio;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntCategoriaExercicio;
import br.com.etraining.utils.StringFakeUtils;

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

	public EntCategoriaExercicio pesquisar(Long id) throws ETrainingException {
		EntCategoriaExercicio categoria = getTemplate().pesquisar(
				getEntidadePersistente(), id);

		StringFakeUtils.replaceCaracteres(categoria, "titulo");

		return categoria;
	}

	public List<EntCategoriaExercicio> pesquisarLista()
			throws ETrainingException {
		List<EntCategoriaExercicio> lista = getTemplate().pesquisarLista(
				getEntidadePersistente());

		for (EntCategoriaExercicio categoria : lista) {
			StringFakeUtils.replaceCaracteres(categoria, "titulo");
		}

		return lista;
	}

}
