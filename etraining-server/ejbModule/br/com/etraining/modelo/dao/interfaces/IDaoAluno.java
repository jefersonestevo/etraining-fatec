package br.com.etraining.modelo.dao.interfaces;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.interfaces.IDaoCRUD;
import br.com.etraining.modelo.entidades.EntAluno;

public interface IDaoAluno extends IDaoCRUD<EntAluno> {

	public EntAluno pesquisarAlunoPorMatriculaSenha(Long matricula, String senha)
			throws ETrainingException;

}
