package br.com.etraining.client.vo.transporte.impl;

import br.com.etraining.client.vo.transporte.AbstractVORequest;

public class VORequestWS extends AbstractVORequest<String> {

	private static final long serialVersionUID = -529668329246284180L;

	private String classe;

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

}
