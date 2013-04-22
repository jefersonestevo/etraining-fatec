package br.com.etraining.android.view.treinamento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.com.etraining.android.R;
import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.android.exceptions.TradutorExcecaoView;
import br.com.etraining.android.service.EtrainingAndroidService;
import br.com.etraining.android.service.ExercicioHolder;
import br.com.etraining.android.utils.DialogView;
import br.com.etraining.android.utils.DialogView.Action;
import br.com.etraining.android.utils.DialogView.ActionQuantidadeAtividade;
import br.com.etraining.android.utils.EtrainingAndroidConstants;
import br.com.etraining.android.utils.ExtraConstants;
import br.com.etraining.android.utils.fragment.DatePickerFragment;
import br.com.etraining.android.utils.fragment.DatePickerFragment.DatePickerListener;
import br.com.etraining.android.utils.pref.EtrainingPref_;
import br.com.etraining.android.view.interfaces.TreinamentoStrategy;
import br.com.etraining.android.view.login.LoginActivity_;
import br.com.etraining.android.view.treinamento.adapters.ExercicioAdapter;
import br.com.etraining.android.view.treinamento.adapters.TreinamentoAdapter;
import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.DiaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioRealizadoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.ConsultaListaExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.InsercaoExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RemocaoExercicioRealizadoVO;
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
		OnItemLongClickListener, OnItemClickListener, DatePickerListener {

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

	@ViewById(R.id.btn_primario)
	Button btnPrimario;

	BaseAdapter listAdapter;

	@InstanceState
	ArrayList<ExercicioVO> listaExercicio = new ArrayList<ExercicioVO>();

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

		if (progressDialog == null)
			progressDialog = ProgressDialog.show(this,
					getString(R.string.msg_carregando),
					getString(R.string.msg_favor_aguardar));

		if (pref.dataSelecionada().get() == 0l) {
			pref.dataSelecionada().put(new Date().getTime());
		}
		strategy.inicializarCampos();
		if (listaItens != null) {
			listaItens.setOnItemClickListener(this);
			listaItens.setOnItemLongClickListener(this);
		}
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

	@Click(R.id.btn_voltar)
	public void pressBotaoVoltar() {
		strategy.botaoVoltar();
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

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataSelecionada.setText(sdf.format(pref.dataSelecionada().get()));
		}

		@Override
		public void botaoPrincipal() {
			strategy = getStrategy(EtrainingAndroidConstants.ACAO_LISTAR_EXERCICIOS);
			TreinamentoActivity.this.inicializar();
		}

		@Override
		public void botaoSecundario() {
			DialogFragment dateFragment = new DatePickerFragment(
					TreinamentoActivity.this);
			dateFragment.show(getFragmentManager(),
					"treinamentoDatePickerFragment");
		}

		@Override
		public void botaoVoltar() {
			Intent intent = new Intent(TreinamentoActivity.this,
					LoginActivity_.class);
			intent.putExtra(ExtraConstants.DESLOGAR_USUARIO, true);
			startActivity(intent);
			finish();
		}

		@Override
		public void onItemListaPress(int position) {
			// Nada a fazer
		}

		@Override
		public boolean onItemListaLongPress(final int position) {
			DialogView.exibirDialogConfirmacao(TreinamentoActivity.this,
					R.string.msg_confirmacao_remocao_exercicio_realizado,
					new Action() {

						@Override
						public void execute() {

							if (progressDialog != null)
								progressDialog.show();

							ExercicioRealizadoSimplesVO exerc = (ExercicioRealizadoSimplesVO) listAdapter
									.getItem(position);
							removerExercicioRealizado(exerc);
						}
					});
			return true;
		}

	}

	protected class ListarExerciciosStrategy implements TreinamentoStrategy {

		@Override
		public int getContentView() {
			return R.layout.listagem_exercicio_view;
		}

		@Override
		public void inicializarCampos() {
			if (progressDialog != null)
				progressDialog.show();
			carregarListaExercicio(pref.categoriaSelecionada().get());
		}

		@Override
		public void botaoPrincipal() {
			try {
				List<CategoriaExercicioVO> listaCategoriaExercicio = exerciciosHolder
						.getListaCategoriaExercicio();

				AlertDialog.Builder builder = new AlertDialog.Builder(
						TreinamentoActivity.this);

				final View dialogView = TreinamentoActivity.this
						.getLayoutInflater().inflate(
								R.layout.dialog_sel_categoria_exercicio, null);
				builder.setView(dialogView);

				final ListView listCategoriaAlimento = (ListView) dialogView
						.findViewById(R.id.listaCategoriaExercicio);

				final ArrayAdapter<CategoriaExercicioVO> arrayAdapter = new ArrayAdapter<CategoriaExercicioVO>(
						TreinamentoActivity.this,
						android.R.layout.simple_list_item_1,
						listaCategoriaExercicio);
				listCategoriaAlimento.setAdapter(arrayAdapter);

				builder.setNegativeButton(R.string.cancelar,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						});

				final AlertDialog dialog = builder.create();

				listCategoriaAlimento
						.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								CategoriaExercicioVO categoria = arrayAdapter
										.getItem(position);
								Long valorId = 0l;
								if (categoria != null
										&& categoria.getId() != null) {
									valorId = categoria.getId();
								}
								pref.categoriaSelecionada().put(valorId);
								if (progressDialog != null)
									progressDialog.show();
								inicializar();
								dialog.dismiss();
							}
						});

				dialog.show();
			} catch (EtrainingViewException e) {
				TradutorExcecaoView.gerarMensagemExcecao(
						TreinamentoActivity.this, e);
			}
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
		public void onItemListaPress(final int position) {
			DialogView.exibirDialogConfirmacaoQuantidade(
					TreinamentoActivity.this,
					R.string.header_insercao_quantidade,
					new ActionQuantidadeAtividade() {
						@Override
						public void execute(Integer qntd) {
							if (progressDialog != null)
								progressDialog.show();
							ExercicioVO exerc = (ExercicioVO) listAdapter
									.getItem(position);
							inserirExercicioRealizado(exerc, qntd);
						}
					});

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

		if (progressDialog != null)
			progressDialog.hide();
	}

	@Background
	protected void carregarListaExercicio(Long categoriaSelecionada) {
		EtrainingViewException exception = null;
		List<ExercicioVO> listaExercicio = null;
		String tituloBotao = null;
		try {
			listaExercicio = exerciciosHolder
					.getListaExercicios(categoriaSelecionada);
			tituloBotao = exerciciosHolder
					.getTituloCategoriaExercicio(categoriaSelecionada);
		} catch (Exception e) {
			exception = new EtrainingViewException(
					CodigoExcecao.ERRO_DESCONHECIDO);
			tituloBotao = exerciciosHolder.getTituloPadraoCategoriaExercicio();
		}

		finalizarCarregamentoListaExercicio(listaExercicio, tituloBotao,
				exception);
	}

	@UiThread
	public void finalizarCarregamentoListaExercicio(
			List<ExercicioVO> exercicios, String tituloBotao,
			EtrainingViewException exception) {
		if (exception != null) {
			TradutorExcecaoView.gerarMensagemExcecao(this, exception);
		} else {
			listaExercicio = new ArrayList<ExercicioVO>(exercicios);
			listAdapter = new ExercicioAdapter(this, listaExercicio);
			listaItens.setAdapter(listAdapter);
		}

		if (tituloBotao != null)
			btnPrimario.setText(tituloBotao);
		else
			btnPrimario.setText(exerciciosHolder
					.getTituloPadraoCategoriaExercicio());

		if (progressDialog != null)
			progressDialog.hide();
	}

	@Override
	public Date getData() {
		return new Date(pref.dataSelecionada().get());
	}

	@Override
	public void alterarData(int year, int month, int day) {
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DATE, day);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		pref.dataSelecionada().put(c.getTime().getTime());
		inicializar();
	}

	@Background
	protected void removerExercicioRealizado(ExercicioRealizadoSimplesVO exerc) {
		EtrainingViewException exception = null;
		RemocaoExercicioRealizadoVO remocao = new RemocaoExercicioRealizadoVO();
		try {
			remocao.setData(new Date(pref.dataSelecionada().get()));
			remocao.setIdAluno(pref.idAluno().get());
			remocao.setIdExercicioRealizado(exerc.getIdExercicioRealizado());
			service.executa(remocao);
		} catch (Exception e) {
			exception = new EtrainingViewException(
					CodigoExcecao.ERRO_DESCONHECIDO);
		}

		finalizarRemocaoExercicioRealizado(exception);
	}

	@UiThread
	public void finalizarRemocaoExercicioRealizado(
			EtrainingViewException exception) {
		if (exception != null) {
			TradutorExcecaoView.gerarMensagemExcecao(this, exception);
		} else {
			inicializar();
		}
	}

	@Background
	protected void inserirExercicioRealizado(ExercicioVO exerc,
			Integer quantidade) {
		EtrainingViewException exception = null;
		InsercaoExercicioRealizadoVO insercao = new InsercaoExercicioRealizadoVO();
		try {
			insercao.setIdAluno(pref.idAluno().get());
			insercao.setQuantidadeAtividade(quantidade);
			insercao.setIdExercicio(exerc.getId());
			DiaExercicioVO diaExercicio = new DiaExercicioVO();
			diaExercicio.setData(new Date(pref.dataSelecionada().get()));
			insercao.setDiaExercicio(diaExercicio);
			service.executa(insercao);
		} catch (Exception e) {
			exception = new EtrainingViewException(
					CodigoExcecao.ERRO_DESCONHECIDO);
		}

		finalizarInsercaoExercicioRealizado(exception);
	}

	@UiThread
	public void finalizarInsercaoExercicioRealizado(
			EtrainingViewException exception) {
		if (exception != null) {
			TradutorExcecaoView.gerarMensagemExcecao(this, exception);
		} else {
			this.strategy = getStrategy(EtrainingAndroidConstants.ACAO_LISTAR_TREINAMENTO);
			inicializar();
		}
	}

}
