package br.com.etraining.client.vo.transporte.impl;

import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.client.vo.transporte.AbstractVORequest;

public class VORequest extends AbstractVORequest<IVO> {

	private static final long serialVersionUID = -7995148598104980829L;

	public VORequest() {
		super();
	}

	public VORequest(IVO vo) {
		super(vo);
	}

}
