package br.com.etraining.android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.com.etraining.android.R;

public class DialogView {

	public static void exibirMensagem(Context context, String mensagem) {
		Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
	}

	public static void exibirMensagem(Context context, int resourceId) {
		Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show();
	}

	public static ProgressDialog gerarDialogCarregando(Context context) {
		return ProgressDialog.show(context,
				context.getString(R.string.msg_carregando),
				context.getString(R.string.msg_favor_aguardar));
	}

	public static void exibirDialogConfirmacao(Context context,
			int resourceIdQuestao, final Action actionConfirmacao) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(resourceIdQuestao);
		builder.setPositiveButton(R.string.confirmar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						actionConfirmacao.execute();
					}
				});
		builder.setNegativeButton(R.string.cancelar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						return;
					}
				});

		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public static void exibirDialogConfirmacaoQuantidade(
			final Activity activity, Integer resourceIdQuestao,
			final ActionQuantidadeAtividade actionConfirmacao) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		if (resourceIdQuestao != null)
			builder.setTitle(resourceIdQuestao);

		final View dialogView = activity.getLayoutInflater().inflate(
				R.layout.dialog_quantidade_atividade, null);
		builder.setView(dialogView);

		builder.setPositiveButton(R.string.confirmar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						EditText txtSenha = (EditText) dialogView
								.findViewById(R.id.txtQuantidadeAtividade);
						actionConfirmacao.execute(Integer.parseInt(txtSenha
								.getText().toString()));
					}
				});
		builder.setNegativeButton(R.string.cancelar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public static interface Action {
		void execute();
	}

	public static interface ActionQuantidadeAtividade {
		void execute(Integer pass);
	}

}
