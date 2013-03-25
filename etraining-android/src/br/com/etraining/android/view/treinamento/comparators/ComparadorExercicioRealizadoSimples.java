package br.com.etraining.android.view.treinamento.comparators;

import java.util.Comparator;

import br.com.etraining.client.vo.impl.entidades.ExercicioRealizadoSimplesVO;

public class ComparadorExercicioRealizadoSimples implements
		Comparator<ExercicioRealizadoSimplesVO> {

	@Override
	public int compare(ExercicioRealizadoSimplesVO ex1,
			ExercicioRealizadoSimplesVO ex2) {
		return ex1.getTituloExercicio().compareTo(ex2.getTituloExercicio());
	}

}
