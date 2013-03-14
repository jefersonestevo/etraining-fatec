package br.com.etraining.modelo.dao.interfaces;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.interfaces.IDaoCRUD;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

public interface IDaoProgramaTreinamento extends
		IDaoCRUD<EntProgramaTreinamento> {

	public EntProgramaTreinamento pesquisarAtualPorIdAluno(Long idAluno)
			throws ETrainingException;

}
