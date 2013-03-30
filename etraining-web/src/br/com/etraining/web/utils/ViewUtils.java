package br.com.etraining.web.utils;

import javax.inject.Named;

@Named
public class ViewUtils {

	public static final int QNTD_LINHAS = 10;
	private static final String QNTD_SUGESTAO_LINHAS = "10, 20, 30";


	public String getNomeUsuarioLogado() {
		// TODO - Pegar usuario logado...
		return null;
	}

	public int getQntdLinhas() {
		return QNTD_LINHAS;
	}

	public String getSugestaoQntdLinhas() {
		return QNTD_SUGESTAO_LINHAS;
	}

}
