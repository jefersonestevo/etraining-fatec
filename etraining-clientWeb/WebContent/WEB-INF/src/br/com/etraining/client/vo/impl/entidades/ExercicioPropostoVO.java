package br.com.etraining.client.vo.impl.entidades;

import br.com.etraining.client.vo.interfaces.IVO;

public class ExercicioPropostoVO implements IVO {

	private static final long serialVersionUID = -1684718377568404043L;

	private Long id;
	private DiaSemanaVO diaSemana;
	private ExercicioVO exercicio;
	private Integer quantidadeExercicioSugerida;

	public ExercicioPropostoVO() {
	}

	public ExercicioPropostoVO(Long id) {
		this.id = id;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((diaSemana == null) ? 0 : diaSemana.hashCode());
		result = prime * result
				+ ((exercicio == null) ? 0 : exercicio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExercicioPropostoVO other = (ExercicioPropostoVO) obj;
		if (diaSemana == null) {
			if (other.diaSemana != null)
				return false;
		} else if (!diaSemana.equals(other.diaSemana))
			return false;
		if (exercicio == null) {
			if (other.exercicio != null)
				return false;
		} else if (!exercicio.equals(other.exercicio))
			return false;
		return true;
	}

	public Integer getQuantidadeExercicioSugerida() {
		return quantidadeExercicioSugerida;
	}

	public void setQuantidadeExercicioSugerida(
			Integer quantidadeExercicioSugerida) {
		this.quantidadeExercicioSugerida = quantidadeExercicioSugerida;
	}

}
