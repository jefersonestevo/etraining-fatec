package br.com.etraining.client.vo.transporte;

import java.io.Serializable;

import br.com.etraining.client.vo.interfaces.IVO;

public class VOResponse implements Serializable {

	private static final long serialVersionUID = 2592217343144311455L;

	private IVO response;
	private Long codigoErro;

	public IVO getResponse() {
		return response;
	}

	public void setResponse(IVO response) {
		this.response = response;
	}

	public Long getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(Long codigoErro) {
		this.codigoErro = codigoErro;
	}

}
