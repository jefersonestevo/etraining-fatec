package br.com.etraining.client.fachada.ejb;

import br.com.etraining.client.vo.transporte.impl.VORequest;
import br.com.etraining.client.vo.transporte.impl.VOResponse;

public interface IEtrainingService {

	public VOResponse executa(VORequest request);

}
