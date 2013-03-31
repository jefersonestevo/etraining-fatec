package br.com.etraining.utils;

import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.exception.ETrainingInfraException;

public class TradutorExcecaoUtils {

	public static ETrainingException getExcecaoTraduzida(RuntimeException r) {
		r.printStackTrace();
		// TODO - Configurar Corretamente o tradutor de exceção
		return new ETrainingInfraException(CodigoExcecao.ERRO_DESCONHECIDO);
	}

}
