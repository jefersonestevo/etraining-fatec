package br.com.etraining.android.view.treinamento;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.etraining.android.R;
import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.android.exceptions.TradutorExcecaoView;
import br.com.etraining.android.service.EtrainingAndroidService;
import br.com.etraining.android.service.ExercicioHolder;
import br.com.etraining.android.utils.EtrainingAndroidConstants;
import br.com.etraining.android.utils.ExtraConstants;
import br.com.etraining.android.utils.pref.EtrainingPref_;
import br.com.etraining.android.view.interfaces.TreinamentoStrategy;
import br.com.etraining.android.view.treinamento.adapters.ExercicioAdapter;
import br.com.etraining.android.view.treinamento.adapters.TreinamentoAdapter;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.ConsultaListaExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RespostaConsultaListaExercicioRealizadoVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.InstanceState;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

@EActivity
public class TreinamentoActivity extends Activity implements
		OnItemLongClickListener, OnItemClickListener {

	ProgressDialog progressDialog;

	private TreinamentoStrategy strategy;

	@Extra(ExtraConstants.ACAO_TREINAMENTO_STRATEGY)
	@InstanceState
	Integer acaoStrategy;

	@Pref
	EtrainingPref_ pref;

	@Bean
	EtrainingAndroidService service;

	@Bean
	ExercicioHolder exerciciosHolder;

	@ViewById(R.id.lista_items)
	ListView listaItens;

	@ViewById(R.id.lbl_data_selecionada)
	TextView dataSelecionada;

	BaseAdapter listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (acaoStrategy == null) {
			acaoStrategy = EtrainingAndroidConstants.ACAO_LISTAR_TREINAMENTO;
		}

		strategy = getStrategy(acaoStrategy);
		inicializar();
	}

	private void inicializar() {
		setContentView(strategy.getContentView());

		progressDialog = ProgressDialog.show(this,
				getString(R.string.msg_carregando),
				getString(R.string.msg_favor_aguardar));
		progressDialog.show();
		if (pref.dataSelecionada().get() == 0l) {
			pref.dataSelecionada().put(new Date().getTime());
		}
		strategy.inicializarCampos();
		if (listaItens != null) {
			listaItens.setOnItemClickListener(this);
			listaItens.setOnItemLongClickListener(this);
		}
		progressDialog.hide();
	}

	private TreinamentoStrategy getStrategy(Integer acaoStrategy) {
		if (acaoStrategy != null) {
			switch (acaoStrategy) {
			case EtrainingAndroidConstants.ACAO_LISTAR_TREINAMENTO:
				return new ListarTreinamentoStrategy();
			case EtrainingAndroidConstants.ACAO_LISTAR_EXERCICIOS:
				return new ListarExerciciosStrategy();
			}
		}
		return null;
	}

	@Click(R.id.btn_primario)
	public void pressBotaoPrincipal() {
		strategy.botaoPrincipal();
	}

	@Click(R.id.btn_secundario)
	public void pressBotaoSecundario() {
		strategy.botaoSecundario();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		strategy.onItemListaPress(position);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		return strategy.onItemListaLongPress(position);
	}

	protected class ListarTreinamentoStrategy implements TreinamentoStrategy {

		@Override
		public int getContentView() {
			return R.layout.home_view;
		}

		@Override
		public void inicializarCampos() {
			carregarListaExercicioRealizado();
		}

		@Override
		public void botaoPrincipal() {
			strategy = getStrategy(EtrainingAndroidConstants.ACAO_LISTAR_EXERCICIOS);
			TreinamentoActivity.this.inicializar();
		}

		@Override
		public void botaoSecundario() {
			// TODO - Abrir modal para selecao de data
		}

		@Override
		public void botaoVoltar() {
			// TODO - Deslogar da aplicacao

		}

		@Override
		public void onItemListaPress(int position) {
			// Nada a fazer
		}

		@Override
		public boolean onItemListaLongPress(int position) {
			// TODO - Abrir modal para remover exercicio realizado

			return false;
		}

	}

	protected class ListarExerciciosStrategy implements TreinamentoStrategy {

		@Override
		public int getContentView() {
			return R.layout.listagem_exercicio_view;
		}

		@Override
		public void inicializarCampos() {
			carregarListaExercicio();
		}

		@Override
		public void botaoPrincipal() {
			strategy = getStrategy(EtrainingAndroidConstants.ACAO_LISTAR_TREINAMENTO);
			TreinamentoActivity.this.inicializar();
		}

		@Override
		public void botaoSecundario() {
			// Nada a fazer
		}

		@Override
		public void botaoVoltar() {
			strategy = getStrategy(EtrainingAndroidConstants.ACAO_LISTAR_TREINAMENTO);
			TreinamentoActivity.this.inicializar();
		}

		@Override
		public void onItemListaPress(int position) {
			// TODO - Abrir modal para selecao de atividade e insercao do
			// exercicio realizado

		}

		@Override
		public boolean onItemListaLongPress(int position) {
			// Nada a fazer
			return false;
		}

	}

	@Background
	protected void carregarListaExercicioRealizado() {

		ConsultaListaExercicioRealizadoVO consulta = new ConsultaListaExercicioRealizadoVO();
		consulta.setData(new Date(pref.dataSelecionada().get()));
		consulta.setIdAluno(pref.idAluno().get());

		EtrainingViewException exception = null;

		RespostaConsultaListaExercicioRealizadoVO response = null;
		try {
			response = (RespostaConsultaListaExercicioRealizadoVO) service
					.executa(consulta);
		} catch (EtrainingViewException e) {
			exception = e;
		} catch (Exception e) {
			exception = new EtrainingViewException(
					CodigoExcecao.ERRO_DESCONHECIDO);
		}

		finalizarCarregamentoListaExercicioRealizado(response, exception);
	}

	@UiThread
	public void finalizarCarregamentoListaExercicioRealizado(
			RespostaConsultaListaExercicioRealizadoVO response,
			EtrainingViewException exception) {
		if (exception != null) {
			TradutorExcecaoView.gerarMensagemExcecao(this, exception);
		} else {
			listAdapter = new TreinamentoAdapter(this,
					response.getListaExercicioRealizado());
			listaItens.setAdapter(listAdapter);
		}
	}

	@Background
	protected void carregarListaExercicio() {
		EtrainingViewException exception = null;
		List<ExercicioVO> listaExercicio = null;
		try {
			listaExercicio = exerciciosHolder.getListaExercicios();
		} catch (Exception e) {
			exception = new EtrainingViewException(
					CodigoExcecao.ERRO_DESCONHECIDO);
		}

		finalizarCarregamentoListaExercicio(listaExercicio, exception);
	}

	@UiThread
	public void finalizarCarregamentoListaExercicio(
			List<ExercicioVO> listaExercicio, EtrainingViewException exception) {
		if (exception != null) {
			TradutorExcecaoView.gerarMensagemExcecao(this, exception);
		} else {
			listAdapter = new ExercicioAdapter(this, listaExercicio);
			listaItens.setAdapter(listAdapter);
		}
	}

}
