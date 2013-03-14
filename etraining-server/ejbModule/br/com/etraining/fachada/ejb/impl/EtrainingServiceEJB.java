package br.com.etraining.fachada.ejb.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import br.com.etraining.client.fachada.ejb.IEtrainingService;
import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.client.vo.transporte.impl.VORequest;
import br.com.etraining.client.vo.transporte.impl.VOResponse;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.negocio.bo.interfaces.IBO;
import br.com.etraining.utils.CDIUtils;

@Stateless(mappedName = "IEtrainingService")
@Remote(IEtrainingService.class)
public class EtrainingServiceEJB implements IEtrainingService {

	@Inject
	private BeanManager beanManager;

	@Override
	@SuppressWarnings(value = { "unchecked", "rawtypes" })
	public VOResponse executa(VORequest request) {
		VOResponse response = new VOResponse();
		try {

			String nomeBO = request.getRequest().getClass().getSimpleName();

			IBO bo = (IBO) CDIUtils.getBean(nomeBO, beanManager);

			IVO voRetorno = bo.executa(request.getRequest());
			response.setResponse(voRetorno);

		} catch (ETrainingException e) {
			response.setCodigoErro(e.getCodigoExcecao().getCodigo());
		} catch (Exception e) {
			response.setCodigoErro(CodigoExcecao.ERRO_DESCONHECIDO.getCodigo());
		}

		return response;
	}

	public BeanManager getBeanManager() {
		return beanManager;
	}

	public void setBeanManager(BeanManager beanManager) {
		this.beanManager = beanManager;
	}

}
