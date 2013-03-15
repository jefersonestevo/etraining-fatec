package br.com.etraining.fachada.web;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.client.vo.transporte.impl.VORequestWS;
import br.com.etraining.client.vo.transporte.impl.VOResponseWS;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.negocio.bo.interfaces.IBO;
import br.com.etraining.utils.CDIUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebService
@Stateless
public class ETrainingServiceWS {

	@Inject
	private BeanManager beanManager;

	@WebMethod
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String executa(String requestStr) {
		Gson gson = new GsonBuilder().create();

		VORequestWS request = gson.fromJson(requestStr, VORequestWS.class);
		VOResponseWS response = new VOResponseWS();
		try {
			Class classe = Class.forName(request.getClasse());

			IVO vo = gson.fromJson(request.getRequest(), classe);

			String nomeBO = classe.getSimpleName();
			IBO bo = (IBO) CDIUtils.getBean(nomeBO, beanManager);

			IVO voRetorno = bo.executa(vo);
			response.setResponse(gson.toJson(voRetorno));

		} catch (ETrainingException e) {
			response.setCodigoErro(e.getCodigoExcecao().getCodigo());
		} catch (Exception e) {
			response.setCodigoErro(CodigoExcecao.ERRO_DESCONHECIDO.getCodigo());
		}
		return gson.toJson(response);
	}

	@WebMethod(exclude = true)
	public BeanManager getBeanManager() {
		return beanManager;
	}

	@WebMethod(exclude = true)
	public void setBeanManager(BeanManager beanManager) {
		this.beanManager = beanManager;
	}

}
