package br.com.etraining.client.vo.transporte;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractVORequest<OBJTRANSFERENCIA> implements
		Serializable {

	protected OBJTRANSFERENCIA request;

	public OBJTRANSFERENCIA getRequest() {
		return request;
	}

	public void setRequest(OBJTRANSFERENCIA request) {
		this.request = request;
	}
}
