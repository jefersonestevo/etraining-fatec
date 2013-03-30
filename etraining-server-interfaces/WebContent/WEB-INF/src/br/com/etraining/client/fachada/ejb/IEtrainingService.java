package br.com.etraining.client.fachada.ejb;

import java.io.Serializable;

import br.com.etraining.client.vo.transporte.impl.VORequest;
import br.com.etraining.client.vo.transporte.impl.VOResponse;

public interface IEtrainingService extends Serializable {

	public VOResponse executa(VORequest request);

}
