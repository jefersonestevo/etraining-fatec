package br.com.etraining.web.fachada;

import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.web.exceptions.ViewException;

public interface ITratadorNegocioService {

	public IVO executa(IVO request) throws ViewException;
	
}
