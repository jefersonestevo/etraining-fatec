package br.com.etraining.exception;

import br.com.etraining.client.vo.transporte.CodigoExcecao;

public class ETrainingBusinessException extends ETrainingException {

	private static final long serialVersionUID = 1213424352354234L;

	public ETrainingBusinessException(CodigoExcecao codigoExcecao) {
		super(codigoExcecao);
	}

}
