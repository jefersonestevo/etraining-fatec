package br.com.etraining.exception;

import br.com.etraining.client.vo.transporte.CodigoExcecao;

public abstract class ETrainingException extends Exception {

	private static final long serialVersionUID = -1072795280240633000L;

	protected CodigoExcecao codigoExcecao;

	public ETrainingException(CodigoExcecao codigoExcecao) {
		this.codigoExcecao = codigoExcecao;
	}

	public CodigoExcecao getCodigoExcecao() {
		return codigoExcecao;
	}

}
