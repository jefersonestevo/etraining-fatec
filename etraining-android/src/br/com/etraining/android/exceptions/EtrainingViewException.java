package br.com.etraining.android.exceptions;

import br.com.etraining.client.vo.transporte.CodigoExcecao;

public class EtrainingViewException extends Exception {

	private static final long serialVersionUID = 8731571840850189266L;

	private CodigoExcecao codigoExcecao;

	public EtrainingViewException(Long codigoExcecao) {
		this.codigoExcecao = CodigoExcecao.getCodigoExcecao(codigoExcecao);
	}

	public EtrainingViewException(CodigoExcecao codigoExcecao) {
		this.codigoExcecao = codigoExcecao;
	}

	public CodigoExcecao getCodigoExcecao() {
		return codigoExcecao;
	}

}
