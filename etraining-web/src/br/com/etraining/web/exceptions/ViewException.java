package br.com.etraining.web.exceptions;

import br.com.etraining.client.vo.transporte.CodigoExcecao;

public class ViewException extends Exception {

	private static final long serialVersionUID = 4400801832815712018L;

	public CodigoExcecao codigoExcecao;

	public ViewException(CodigoExcecao codigoExcecao) {
		this.codigoExcecao = codigoExcecao;
	}

	public CodigoExcecao getCodigoExcecao() {
		return codigoExcecao;
	}

}
