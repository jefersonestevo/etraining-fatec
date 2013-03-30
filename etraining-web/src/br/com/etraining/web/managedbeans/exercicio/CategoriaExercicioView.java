package br.com.etraining.web.managedbeans.exercicio;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;

public class CategoriaExercicioView {

	private Long id;
	private String titulo;
	private List<ExercicioVO> listaExercicios = new ArrayList<ExercicioVO>();

	public CategoriaExercicioView(Long id, String titulo) {
		super();
		this.id = id;
		this.titulo = titulo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ExercicioVO> getListaExercicios() {
		return listaExercicios;
	}

	public void setListaExercicios(List<ExercicioVO> listaExercicios) {
		this.listaExercicios = listaExercicios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CategoriaExercicioView other = (CategoriaExercicioView) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
