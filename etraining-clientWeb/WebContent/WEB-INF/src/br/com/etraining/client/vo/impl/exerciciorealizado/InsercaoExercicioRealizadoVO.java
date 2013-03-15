package br.com.etraining.client.vo.impl.exerciciorealizado;

import br.com.etraining.client.vo.impl.entidades.DiaExercicioVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class InsercaoExercicioRealizadoVO implements IVO {

	private static final long serialVersionUID = 419528993840836597L;

	private Long idAluno;
	private Long idExercicio;
	private Long idAtividade;
	private Integer quantidadeAtividade;
	private DiaExercicioVO diaExercicio;

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

	public Long getIdExercicio() {
		return idExercicio;
	}

	public void setIdExercicio(Long idExercicio) {
		this.idExercicio = idExercicio;
	}

	public Long getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(Long idAtividade) {
		this.idAtividade = idAtividade;
	}

	public Integer getQuantidadeAtividade() {
		return quantidadeAtividade;
	}

	public void setQuantidadeAtividade(Integer quantidadeAtividade) {
		this.quantidadeAtividade = quantidadeAtividade;
	}

	public DiaExercicioVO getDiaExercicio() {
		return diaExercicio;
	}

	public void setDiaExercicio(DiaExercicioVO diaExercicio) {
		this.diaExercicio = diaExercicio;
	}

}
