package br.com.etraining.modelo.dao.interfaces;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.interfaces.IDaoCRUD;
import br.com.etraining.modelo.entidades.EntMatricula;

public interface IDaoMatricula extends IDaoCRUD<EntMatricula> {

	public boolean existeMatricula(String numero) throws ETrainingException;

	public EntMatricula pesquisarPorMatricula(String numero)
			throws ETrainingException;

}
