package br.com.etraining.client.vo.impl.realizarlogin;

import br.com.etraining.client.vo.interfaces.IVO;

public class RealizarLoginVO implements IVO {

	private static final long serialVersionUID = -8567095371269700172L;

	private Long numeroMatricula;
	private String senha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(Long numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

}
