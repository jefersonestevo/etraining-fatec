package br.com.etraining.client.fachada.ejb;

import br.com.etraining.client.vo.transporte.VORequest;
import br.com.etraining.client.vo.transporte.VOResponse;

public interface IEtrainingService {

	public VOResponse executa(VORequest request);

}
