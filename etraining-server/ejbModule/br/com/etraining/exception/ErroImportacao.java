package br.com.etraining.exception;

import java.io.Serializable;

import br.com.etraining.client.vo.transporte.CodigoExcecao;

public class ErroImportacao implements Serializable {

	private static final long serialVersionUID = 7936599994987655029L;

	private CodigoExcecao codigoExcecao;
	private Integer linha;
	private String message;

	public ErroImportacao(CodigoExcecao codigoExcecao) {
		super();
		this.codigoExcecao = codigoExcecao;
	}

	public ErroImportacao(CodigoExcecao codigoExcecao, String message) {
		super();
		this.codigoExcecao = codigoExcecao;
		this.message = message;
	}

	public ErroImportacao(CodigoExcecao codigoExcecao, String message,
			Integer linha) {
		super();
		this.codigoExcecao = codigoExcecao;
		this.linha = linha;
		this.message = message;
	}

	public Integer getLinha() {
		return linha;
	}

	public void setLinha(Integer linha) {
		this.linha = linha;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CodigoExcecao getCodigoExcecao() {
		return codigoExcecao;
	}

}
