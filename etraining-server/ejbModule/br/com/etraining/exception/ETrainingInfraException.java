package br.com.etraining.exception;

import javax.ejb.ApplicationException;

import br.com.etraining.client.vo.transporte.CodigoExcecao;

@ApplicationException(rollback = true)
public class ETrainingInfraException extends ETrainingException {

	private static final long serialVersionUID = 4969344041273834572L;

	public ETrainingInfraException(CodigoExcecao codigoExcecao) {
		super(codigoExcecao);
	}

}
