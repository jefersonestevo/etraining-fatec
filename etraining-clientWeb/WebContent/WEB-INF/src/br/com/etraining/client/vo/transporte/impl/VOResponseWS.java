package br.com.etraining.client.vo.transporte.impl;

import br.com.etraining.client.vo.transporte.AbstractVOResponse;

public class VOResponseWS extends AbstractVOResponse<String> {

	private static final long serialVersionUID = -1301722387190542664L;

	private String classe;

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

}
