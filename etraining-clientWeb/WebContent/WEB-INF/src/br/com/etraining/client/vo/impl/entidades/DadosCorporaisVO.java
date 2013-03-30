package br.com.etraining.client.vo.impl.entidades;

import br.com.etraining.client.vo.interfaces.IVO;

public class DadosCorporaisVO implements IVO {

	private static final long serialVersionUID = -3428701659885820926L;

	private Long id;
	private Double peso;
	private Double altura;
	private Double indiceGorduraCorporal;
	private Integer batimentosPorMinuto;
	private Double tempoEsteira;
	private Double pressaoArterialMaxima;
	private Double pressaoArterialMinima;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getIndiceGorduraCorporal() {
		return indiceGorduraCorporal;
	}

	public void setIndiceGorduraCorporal(Double indiceGorduraCorporal) {
		this.indiceGorduraCorporal = indiceGorduraCorporal;
	}

	public Integer getBatimentosPorMinuto() {
		return batimentosPorMinuto;
	}

	public void setBatimentosPorMinuto(Integer batimentosPorMinuto) {
		this.batimentosPorMinuto = batimentosPorMinuto;
	}

	public Double getTempoEsteira() {
		return tempoEsteira;
	}

	public void setTempoEsteira(Double tempoEsteira) {
		this.tempoEsteira = tempoEsteira;
	}

	public Double getPressaoArterialMaxima() {
		return pressaoArterialMaxima;
	}

	public void setPressaoArterialMaxima(Double pressaoArterialMaxima) {
		this.pressaoArterialMaxima = pressaoArterialMaxima;
	}

	public Double getPressaoArterialMinima() {
		return pressaoArterialMinima;
	}

	public void setPressaoArterialMinima(Double pressaoArterialMinima) {
		this.pressaoArterialMinima = pressaoArterialMinima;
	}

}
