package br.com.etraining.client.vo.impl.entidades;

import br.com.etraining.client.vo.interfaces.IVO;

public class CategoriaExercicioVO implements IVO {

	private static final long serialVersionUID = -1263210676484539883L;

	private Long id;
	private String titulo;

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

}
