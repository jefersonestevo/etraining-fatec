package br.com.etraining.client.vo.impl.enums;

public enum DiaSemana {
	DOMINGO(1, "Domingo"), //
	SEGUNDA(2, "Segunda"), //
	TERCA(3, "Terca"), //
	QUARTA(4, "Quarta"), //
	QUINTA(5, "Quinta"), //
	SEXTA(6, "Sexta"), //
	SABADO(7, "Sabado"), //
	;
	private int id;
	private String chave;

	private DiaSemana(int id, String chave) {
		this.id = id;
		this.chave = chave;
	}

	public int getId() {
		return id;
	}

	public String getChave() {
		return chave;
	}
}
