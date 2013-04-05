package br.com.etraining.modelo.dao.interfaces;

import java.util.Date;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.interfaces.IDaoCRUD;
import br.com.etraining.modelo.entidades.EntDiaExercicio;

public interface IDaoDiaExercicio extends IDaoCRUD<EntDiaExercicio> {

	public EntDiaExercicio pesquisar(Long idUsuario, Date data)
			throws ETrainingException;

}
