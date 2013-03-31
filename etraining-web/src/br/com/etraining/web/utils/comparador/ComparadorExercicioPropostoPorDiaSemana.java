package br.com.etraining.web.utils.comparador;

import java.util.Comparator;

import br.com.etraining.client.vo.impl.entidades.ExercicioPropostoVO;

public class ComparadorExercicioPropostoPorDiaSemana implements
		Comparator<ExercicioPropostoVO> {

	@Override
	public int compare(ExercicioPropostoVO ex1, ExercicioPropostoVO ex2) {

		if (ex1 == null && ex2 != null)
			return -1;
		if (ex1 != null && ex2 == null)
			return 1;
		if (ex1 == null && ex2 == null)
			return 0;

		if (ex1.getDiaSemana() == null && ex2.getDiaSemana() != null)
			return -1;
		if (ex1.getDiaSemana() != null && ex2.getDiaSemana() == null)
			return 1;
		if (ex1.getDiaSemana() == null && ex2.getDiaSemana() == null)
			return 0;

		if (ex1.getDiaSemana().getId() == null
				&& ex2.getDiaSemana().getId() != null)
			return -1;
		if (ex1.getDiaSemana().getId() != null
				&& ex2.getDiaSemana().getId() == null)
			return 1;
		if (ex1.getDiaSemana().getId() == null
				&& ex2.getDiaSemana().getId() == null)
			return 0;

		return ex1.getDiaSemana().getId().compareTo(ex2.getDiaSemana().getId());
	}

}
