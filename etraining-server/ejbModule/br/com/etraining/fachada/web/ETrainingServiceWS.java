package br.com.etraining.fachada.web;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.client.vo.transporte.impl.VORequestWS;
import br.com.etraining.client.vo.transporte.impl.VOResponseWS;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.negocio.bo.BOResolver;
import br.com.etraining.negocio.bo.interfaces.IBO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Stateless
@WebService(name = "ETrainingServiceWS", targetNamespace = "ETrainingServiceWS")
public class ETrainingServiceWS {

	@Inject
	private BOResolver boResolver;

	@WebMethod
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String executa(@WebParam(name = "request") String requestStr) {
		Gson gson = new GsonBuilder().create();

		VORequestWS request = gson.fromJson(requestStr, VORequestWS.class);
		VOResponseWS response = new VOResponseWS();
		try {
			Class classe = Class.forName(request.getClasse());

			IVO vo = gson.fromJson(request.getRequest(), classe);

			IBO bo = boResolver.getBOFor(classe);

			IVO voRetorno = bo.executa(vo);
			response.setResponse(gson.toJson(voRetorno));
			response.setClasse(voRetorno.getClass().getName());

		} catch (ETrainingException e) {
			response.setCodigoErro(e.getCodigoExcecao().getCodigo());
		} catch (Exception e) {
			response.setCodigoErro(CodigoExcecao.ERRO_DESCONHECIDO.getCodigo());
		}
		return gson.toJson(response);
	}

	@WebMethod(exclude = true)
	public BOResolver getBoResolver() {
		return boResolver;
	}

	@WebMethod(exclude = true)
	public void setBoResolver(BOResolver boResolver) {
		this.boResolver = boResolver;
	}

}
