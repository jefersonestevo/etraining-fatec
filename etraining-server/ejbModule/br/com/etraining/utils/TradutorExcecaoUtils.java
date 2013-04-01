package br.com.etraining.utils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.QueryTimeoutException;

import org.apache.log4j.Logger;

import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.exception.ETrainingInfraException;

public class TradutorExcecaoUtils {

	private static Logger log = Logger.getLogger(TradutorExcecaoUtils.class);

	public static ETrainingException getExcecaoTraduzida(RuntimeException r) {
		
		log.error("Erro em acesso a base de dados: ",r);
		
		ETrainingException exception = new ETrainingInfraException(
				CodigoExcecao.ERRO_DESCONHECIDO);

		if (r instanceof EntityNotFoundException) {
			exception = new ETrainingInfraException(
					CodigoExcecao.BD_ENTIDADE_NAO_ENCONTRADA);
		} else if (r instanceof NoResultException) {
			exception = new ETrainingInfraException(
					CodigoExcecao.BD_RESULTADO_NAO_ENCONTRADO);
		} else if (r instanceof NonUniqueResultException) {
			exception = new ETrainingInfraException(
					CodigoExcecao.BD_INCONSISTENCIA_UNIQUE);
		} else if (r instanceof QueryTimeoutException) {
			exception = new ETrainingInfraException(CodigoExcecao.BD_TIMEOUT);
		} else if (r instanceof EntityExistsException) {
			exception = new ETrainingInfraException(
					CodigoExcecao.BD_ENTIDADE_EXISTE);
		} else if (r instanceof PersistenceException) {
			exception = new ETrainingInfraException(
					CodigoExcecao.BD_ERRO_ACESSO_BASE);
		}
		return exception;
	}

}
