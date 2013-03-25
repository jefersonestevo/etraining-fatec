package br.com.etraining.android.view.login;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.TextView;
import br.com.etraining.android.R;
import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.android.exceptions.TradutorExcecaoView;
import br.com.etraining.android.service.EtrainingAndroidService;
import br.com.etraining.android.utils.DialogView;
import br.com.etraining.android.utils.EtrainingAndroidConstants;
import br.com.etraining.android.utils.ExtraConstants;
import br.com.etraining.android.utils.pref.EtrainingPref_;
import br.com.etraining.android.view.treinamento.TreinamentoActivity_;
import br.com.etraining.client.vo.impl.realizarlogin.RealizarLoginVO;
import br.com.etraining.client.vo.impl.realizarlogin.RespostaRealizarLoginVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

@EActivity
public class LoginActivity extends Activity {

	ProgressDialog progressDialog;

	@ViewById(R.id.txt_login_login)
	TextView txtLogin;

	@ViewById(R.id.txt_login_senha)
	TextView txtSenha;

	@Bean
	EtrainingAndroidService service;

	@Extra(ExtraConstants.DESLOGAR_USUARIO)
	Boolean deslogarUsuario;

	@Pref
	EtrainingPref_ pref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_view);

		if (pref.idAluno().get() != 0
				&& StringUtils.isNotBlank(pref.numeroMatricula().get())) {
			irParaTelaListagemExerciciosRealizados();
			return;
		} else {
			deslogarUsuario = true;
		}

		if (deslogarUsuario != null && deslogarUsuario) {
			pref.idAluno().put(0);
			pref.numeroMatricula().put("");
		}
	}

	@AfterViews
	public void inicializar() {
		txtSenha.setTypeface(Typeface.DEFAULT);
		txtSenha.setTransformationMethod(new PasswordTransformationMethod());
	}

	@Click(R.id.btn_logar)
	public void logar() {
		RealizarLoginVO realizarLoginVO = new RealizarLoginVO();
		realizarLoginVO.setNumeroMatricula(Long.getLong(txtLogin.getText()
				.toString()));
		realizarLoginVO.setSenha(txtSenha.getText().toString());

		if (realizarLoginVO.getNumeroMatricula() == null
				|| StringUtils.isBlank(realizarLoginVO.getSenha())) {
			DialogView.exibirMensagem(this,
					R.string.error_login_view_criar_usuario);
		} else {
			progressDialog = ProgressDialog.show(this,
					getString(R.string.login_view_logando),
					getString(R.string.login_view_validando_login));
			progressDialog.show();
			this.realizarLogin(realizarLoginVO);
		}
	}

	@Background
	public void realizarLogin(RealizarLoginVO realizarLoginVO) {
		EtrainingViewException exception = null;
		RespostaRealizarLoginVO response = null;
		try {
			response = (RespostaRealizarLoginVO) service
					.executa(realizarLoginVO);
			if (response == null)
				exception = new EtrainingViewException(
						CodigoExcecao.USUARIO_NAO_ENCONTRADO);
		} catch (EtrainingViewException e) {
			exception = e;
		} catch (Exception e) {
			exception = new EtrainingViewException(
					CodigoExcecao.ERRO_DESCONHECIDO);
		}

		this.finalizarLoginAluno(response, exception);
	}

	@UiThread
	public void finalizarLoginAluno(RespostaRealizarLoginVO response,
			EtrainingViewException exception) {
		progressDialog.hide();
		if (exception != null) {
			TradutorExcecaoView.gerarMensagemExcecao(this, exception);
		} else {
			pref.idAluno().put(response.getIdAluno());
			pref.numeroMatricula().put(response.getNumeroMatricula());
			pref.dataSelecionada().put(new Date().getTime());
			irParaTelaListagemExerciciosRealizados();
		}
	}

	public void irParaTelaListagemExerciciosRealizados() {
		Intent intent = new Intent(this, TreinamentoActivity_.class);
		intent.putExtra(ExtraConstants.ACAO_TREINAMENTO_STRATEGY,
				EtrainingAndroidConstants.ACAO_LISTAR_TREINAMENTO);
		startActivity(intent);
		finish();
	}

}
