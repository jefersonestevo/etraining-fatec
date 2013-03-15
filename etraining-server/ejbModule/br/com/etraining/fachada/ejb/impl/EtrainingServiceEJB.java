package br.com.etraining.fachada.ejb.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.etraining.client.fachada.ejb.IEtrainingService;
import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.client.vo.transporte.impl.VORequest;
import br.com.etraining.client.vo.transporte.impl.VOResponse;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.negocio.bo.BOResolver;
import br.com.etraining.negocio.bo.interfaces.IBO;

@Stateless(mappedName = "IEtrainingService")
@Remote(IEtrainingService.class)
public class EtrainingServiceEJB implements IEtrainingService {

	@Inject
	private BOResolver resolver;

	@Override
	@SuppressWarnings(value = { "unchecked", "rawtypes" })
	public VOResponse executa(VORequest request) {
		VOResponse response = new VOResponse();
		try {

			IBO bo = resolver.getBOFor(request.getRequest().getClass());

			IVO voRetorno = bo.executa(request.getRequest());
			response.setResponse(voRetorno);

		} catch (ETrainingException e) {
			response.setCodigoErro(e.getCodigoExcecao().getCodigo());
		} catch (Exception e) {
			response.setCodigoErro(CodigoExcecao.ERRO_DESCONHECIDO.getCodigo());
		}

		return response;
	}

	public BOResolver getResolver() {
		return resolver;
	}

	public void setResolver(BOResolver resolver) {
		this.resolver = resolver;
	}

}
