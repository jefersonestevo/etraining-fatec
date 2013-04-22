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
		case USUARIO_NAO_ENCONTRADO:
			DialogView.exibirMensagem(contexto,
					R.string.error_login_usuario_nao_encontrado);
			break;
		case USUARIO_INATIVO:
			DialogView.exibirMensagem(contexto,
					R.string.error_login_usuario_inativo);
			break;
		case USUARIO_SEM_PERFIL:
			DialogView.exibirMensagem(contexto,
					R.string.error_login_usuario_sem_perfil);
			break;
		case ANDROID_APARELHO_SEM_CONEXAO:
			DialogView.exibirMensagem(contexto,
					R.string.error_android_sem_conexao);
			break;
		case ANDROID_SEM_CONEXAO_SERVIDOR:
		case ANDROID_ENTRADA_SAIDA_SERVIDOR:
		case ANDROID_TIMEOUT_SERVIDOR:
			DialogView.exibirMensagem(contexto,
					R.string.error_android_conexao_servidor);
			break;
		case ERRO_DESCONHECIDO:
		default:
			DialogView.exibirMensagem(contexto, R.string.error_view_inesperado);
			break;
		}

	}
}
