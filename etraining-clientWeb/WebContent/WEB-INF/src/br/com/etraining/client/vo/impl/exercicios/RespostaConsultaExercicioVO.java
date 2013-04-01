package br.com.etraining.client.vo.impl.exercicios;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaExercicioVO implements IVO {

	private static final long serialVersionUID = 1L;

	private ExercicioVO exercicio;

	public ExercicioVO getExercicio() {
		return exercicio;
	}

	public void setExercicio(ExercicioVO exercicio) {
		this.exercicio = exercicio;
	}

}
