package br.com.etraining.negocio.bo.interfaces;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.exception.ETrainingException;

public abstract class AbstractBO<REQ extends IVO, RESP extends IVO> implements
		IBO<REQ, RESP> {

	/**
	 * Metodo utilizado para executar a transacao. <br />
	 * O metodo é definido na classe abstrata para definir a transação como <br/>
	 * TransactionAttributeType.REQUIRED. <br />
	 * As subclasses deverão implementar o metodo
	 * br.com.etraining.negocio.bo.interfaces.AbstractBO.executaRegraEspecifica
	 * 
	 * @author Jeferson Estevo
	 * @since 12/03/2013
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public RESP executa(REQ request) throws ETrainingException {
		return executarRegrasEspecificas(request);
	}

	protected abstract RESP executarRegrasEspecificas(REQ request)
			throws ETrainingException;
}
