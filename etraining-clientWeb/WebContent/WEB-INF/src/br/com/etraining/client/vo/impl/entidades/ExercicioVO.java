package br.com.etraining.client.vo.impl.entidades;

import br.com.etraining.client.vo.interfaces.IVO;

public class ExercicioVO implements IVO {

	private static final long serialVersionUID = 5784743787596362938L;

	private Long id;
	private String titulo;
	private Integer pontosPorAtividade;
	private Long idCategoriaExercicio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getPontosPorAtividade() {
		return pontosPorAtividade;
	}

	public void setPontosPorAtividade(Integer pontosPorAtividade) {
		this.pontosPorAtividade = pontosPorAtividade;
	}

	public Long getIdCategoriaExercicio() {
		return idCategoriaExercicio;
	}

	public void setIdCategoriaExercicio(Long idCategoriaExercicio) {
		this.idCategoriaExercicio = idCategoriaExercicio;
	}

}
