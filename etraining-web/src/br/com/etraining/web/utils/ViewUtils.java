package br.com.etraining.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Named;

@Named
public class ViewUtils {

	private static final SimpleDateFormat formatoAno = new SimpleDateFormat(
			"yyyy");

	public String getAnoAtual() {
		return formatoAno.format(new Date());
	}

	public String getNomeUsuarioLogado() {
		// TODO - Pegar usuario logado...
		return null;
	}

}
