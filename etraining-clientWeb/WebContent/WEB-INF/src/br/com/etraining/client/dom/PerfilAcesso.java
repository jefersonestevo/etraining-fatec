package br.com.etraining.client.dom;

public enum PerfilAcesso {
	ALUNO("ALUNO"), //
	PROFESSOR("PROFESSOR"), //
	ADM("ADM");
	private String nome;

	private PerfilAcesso(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
