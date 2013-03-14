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

@WebService
@Stateless
public class ETrainingServiceWS {

	@Inject
	private BeanManager beanManager;

	@WebMethod
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VOResponseWS executa(VORequestWS request) {
		VOResponseWS response = new VOResponseWS();
		try {
			Class classe = Class.forName(request.getClasse());

			// TODO - Pegar json e montar IVO com a classe e a string do request WS
			IVO vo = null;

			String nomeBO = classe.getSimpleName();
			IBO bo = (IBO) CDIUtils.getBean(nomeBO, beanManager);

			IVO voRetorno = bo.executa(vo);
			// TODO - Transformar resposta WS em String
			// response.setResponse(voRetorno);
			response.setClasse(voRetorno.getClass().getName());

		} catch (ETrainingException e) {
			response.setCodigoErro(e.getCodigoExcecao().getCodigo());
		} catch (Exception e) {
			response.setCodigoErro(CodigoExcecao.ERRO_DESCONHECIDO.getCodigo());
		}
		return response;
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
