package br.com.etraining.android.exceptions;

import android.content.Context;
import br.com.etraining.android.R;
import br.com.etraining.android.utils.DialogView;
import br.com.etraining.client.vo.transporte.CodigoExcecao;

public class TradutorExcecaoView {

	public static void gerarMensagemExcecao(Context contexto,
			EtrainingViewException exception) {

		CodigoExcecao codigo = exception.getCodigoExcecao();

		switch (codigo) {
		case ERRO_DESCONHECIDO:
		default:
			DialogView.exibirMensagem(contexto, R.string.error_view_inesperado);
			break;
		}

	}
}
