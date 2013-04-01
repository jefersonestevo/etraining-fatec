package br.com.etraining.client.dom;

public enum StatusProgramaTreinamento {
	AGUARDANDO_APROVACAO(0l), //
	APROVADO(1l), //
	CANCELADO(2l), //
	;
	private Long id;

	private StatusProgramaTreinamento(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
