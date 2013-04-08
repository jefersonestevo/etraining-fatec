package br.com.etraining.negocio.gerador.fake;

public enum EnumCalculoPontos {
	IMC(5, 10, 15), //
	IND_GORD_CORPORAL(5, 10, 15), //
	TEMPO_ESTEIRA(5, 10, 15), //
	PRESSAO_ARTERIAL(5, 10, 15), //
	;
	private int minimo;
	private int medio;
	private int maximo;

	private EnumCalculoPontos(int minimo, int medio, int maximo) {
		this.minimo = minimo;
		this.medio = medio;
		this.maximo = maximo;
	}

	public int getMinimo() {
		return minimo;
	}

	public int getMedio() {
		return medio;
	}

	public int getMaximo() {
		return maximo;
	}

}
