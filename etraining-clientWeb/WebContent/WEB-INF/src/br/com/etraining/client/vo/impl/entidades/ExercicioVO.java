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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((idCategoriaExercicio == null) ? 0 : idCategoriaExercicio
						.hashCode());
		result = prime
				* result
				+ ((pontosPorAtividade == null) ? 0 : pontosPorAtividade
						.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		ExercicioVO other = (ExercicioVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCategoriaExercicio == null) {
			if (other.idCategoriaExercicio != null)
				return false;
		} else if (!idCategoriaExercicio.equals(other.idCategoriaExercicio))
			return false;
		if (pontosPorAtividade == null) {
			if (other.pontosPorAtividade != null)
				return false;
		} else if (!pontosPorAtividade.equals(other.pontosPorAtividade))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

}
