package br.com.etraining.client.vo.transporte;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractVOResponse<OBJRESPONSE> implements Serializable {

	protected OBJRESPONSE response;
	protected Long codigoErro;

	public OBJRESPONSE getResponse() {
		return response;
	}

	public void setResponse(OBJRESPONSE response) {
		this.response = response;
	}

	public Long getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(Long codigoErro) {
		this.codigoErro = codigoErro;
	}
}
