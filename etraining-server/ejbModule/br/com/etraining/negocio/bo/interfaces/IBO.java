package br.com.etraining.negocio.bo.interfaces;

import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.exception.ETrainingException;

public interface IBO<REQ extends IVO, RESP extends IVO> {

	public RESP executa(REQ request) throws ETrainingException;

}
