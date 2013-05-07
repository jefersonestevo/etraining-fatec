package br.com.etraining.web.fachada;

import java.io.Serializable;

import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.web.exceptions.ViewException;

public interface ITratadorNegocioService extends Serializable {

	public IVO executa(IVO request) throws ViewException;

}
