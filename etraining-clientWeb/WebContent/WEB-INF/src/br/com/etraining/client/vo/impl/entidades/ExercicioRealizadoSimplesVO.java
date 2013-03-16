package br.com.etraining.client.vo.impl.entidades;

import br.com.etraining.client.vo.interfaces.IVO;

public class ExercicioRealizadoSimplesVO implements IVO{

	private static final long serialVersionUID = -657382980394956055L;
	
	private Long idExercicioRealizado;
	private String tituloExercicio;
	private Double pontos;

	public Long getIdExercicioRealizado() {
		return idExercicioRealizado;
	}

	public void setIdExercicioRealizado(Long idExercicioRealizado) {
		this.idExercicioRealizado = idExercicioRealizado;
	}

	public String getTituloExercicio() {
		return tituloExercicio;
	}

	public void setTituloExercicio(String tituloExercicio) {
		this.tituloExercicio = tituloExercicio;
	}

	public Double getPontos() {
		return pontos;
	}

	public void setPontos(Double pontos) {
		this.pontos = pontos;
	}

}
