package br.com.etraining.modelo.dao.interfaces;

import java.util.Date;
import java.util.List;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.interfaces.IDaoCRUD;
import br.com.etraining.modelo.entidades.EntExercicioRealizado;

public interface IDaoExercicioRealizado extends IDaoCRUD<EntExercicioRealizado> {

	public List<EntExercicioRealizado> pesquisarListaPorUsuarioData(
			Long idUsuario, Date data) throws ETrainingException;

	public List<EntExercicioRealizado> pesquisarListaPorDataExercicioAluno(
			Date dataInicial, Date dataFinal, Long idExercicio, Long idAluno)
			throws ETrainingException;
}
