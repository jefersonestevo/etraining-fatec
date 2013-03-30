package br.com.etraining.client.vo.impl.entidades;

import br.com.etraining.client.vo.interfaces.IVO;

public class ExercicioPropostoVO implements IVO {

	private static final long serialVersionUID = -1684718377568404043L;

	private Long id;
	private DiaSemanaVO diaSemana;
	private ExercicioVO exercicio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DiaSemanaVO getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemanaVO diaSemana) {
		this.diaSemana = diaSemana;
	}

	public ExercicioVO getExercicio() {
		return exercicio;
	}

	public void setExercicio(ExercicioVO exercicio) {
		this.exercicio = exercicio;
	}

}
