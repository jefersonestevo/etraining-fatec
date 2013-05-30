package br.com.etraining.client.dom;

public enum PerfilAcesso {
	ALUNO(1l, "ALUNO"), //
	PROFESSOR(2l, "PROFESSOR"), //
	ADM(3l, "ADM");

	private Long id;
	private String nome;

	private PerfilAcesso(Long id, String nome) {
		this.nome = nome;
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public Long getId() {
		return id;
	}

}
