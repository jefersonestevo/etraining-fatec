package br.com.etraining.web.fachada.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.fachada.ejb.IEtrainingService;
import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.client.vo.transporte.impl.VORequest;
import br.com.etraining.client.vo.transporte.impl.VOResponse;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.ITratadorNegocioService;

@Named
public class TratadorNegocioService implements ITratadorNegocioService {

	private static final long serialVersionUID = -4838224680755413276L;

	@Inject
	private IEtrainingService serviceDelegate;

	public IVO executa(IVO request) throws ViewException {
		VORequest req = new VORequest();
		req.setRequest(request);
		VOResponse resp = serviceDelegate.executa(req);

		if (resp.getCodigoErro() != null) {
			throw new ViewException(CodigoExcecao.getCodigoExcecao(resp
					.getCodigoErro()));
		}

		return resp.getResponse();
	}

	public IEtrainingService getServiceDelegate() {
		return serviceDelegate;
	}

	public void setServiceDelegate(IEtrainingService serviceDelegate) {
		this.serviceDelegate = serviceDelegate;
	}

}
