package br.com.etraining.android.view.treinamento.comparators;

import java.util.Comparator;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;

public class ComparadorExercicio implements Comparator<ExercicioVO> {

	@Override
	public int compare(ExercicioVO ex1, ExercicioVO ex2) {
		return ex1.getTitulo().compareTo(ex2.getTitulo());
	}

}
