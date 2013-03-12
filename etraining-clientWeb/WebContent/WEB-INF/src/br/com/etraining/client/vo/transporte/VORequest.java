package br.com.etraining.client.vo.transporte;

import java.io.Serializable;

import br.com.etraining.client.vo.interfaces.IVO;

public class VORequest implements Serializable {

	private static final long serialVersionUID = -7995148598104980829L;

	private IVO request;

	public IVO getRequest() {
		return request;
	}

	public void setRequest(IVO request) {
		this.request = request;
	}

}
