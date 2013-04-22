package br.com.etraining.android.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import br.com.etraining.android.R;
import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.client.vo.impl.categoriaexercicio.ConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.categoriaexercicio.RespostaConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;

@EBean(scope = Scope.Singleton)
public class ExercicioHolder {

	private static final CategoriaExercicioVO CATEGORIA_ZERO = new CategoriaExercicioVO();

	@Bean
	EtrainingAndroidService service;

	private Context context;

	public ExercicioHolder(Context context) {
		CATEGORIA_ZERO.setId(0l);
		CATEGORIA_ZERO.setTitulo(context
				.getString(R.string.cancelar_selecao_categoria));
		this.context = context;
	}

	private Date dataUltimaAtualizacao = null;
	private boolean itensAtualizados = false;
	private boolean itensAtualizando = false;
	private boolean ocorreuErro = false;
	private List<ExercicioVO> listaExercicios = new ArrayList<ExercicioVO>();
	private List<CategoriaExercicioVO> listaCategoriaExercicio = new ArrayList<CategoriaExercicioVO>();

	public synchronized String getTituloPadraoCategoriaExercicio() {
		return context.getString(R.string.btn_selecionar_categoria);
	}

	public synchronized String getTituloCategoriaExercicio(
			Long categoriaSelecionada) throws EtrainingViewException {

		if (!CATEGORIA_ZERO.getId().equals(categoriaSelecionada)) {
			List<CategoriaExercicioVO> listaCategoriaExercicio = getListaCategoriaExercicio();
			for (CategoriaExercicioVO cat : listaCategoriaExercicio) {
				if (categoriaSelecionada.equals(cat.getId())) {
					return cat.getTitulo();
				}
			}
		}

		return getTituloPadraoCategoriaExercicio();
	}

	public synchronized List<ExercicioVO> getListaExercicios(
			Long categoriaSelecionada) throws EtrainingViewException {
		validarPreenchimento();

		List<ExercicioVO> listaExerc = new ArrayList<ExercicioVO>();
		if (categoriaSelecionada != null && categoriaSelecionada != 0l) {
			for (ExercicioVO exerc : listaExercicios) {
				if (categoriaSelecionada
						.equals(exerc.getIdCategoriaExercicio())) {
					listaExerc.add(exerc);
				}
			}
		} else {
			listaExerc = new ArrayList<ExercicioVO>(listaExercicios);
		}
		return listaExerc;
	}

	public synchronized List<CategoriaExercicioVO> getListaCategoriaExercicio()
			throws EtrainingViewException {
		validarPreenchimento();
		return listaCategoriaExercicio;
	}

	private synchronized void validarPreenchimento()
			throws EtrainingViewException {
		Calendar cal = Calendar.getInstance();
		if (dataUltimaAtualizacao != null) {
			cal.setTime(dataUltimaAtualizacao);
			cal.add(Calendar.HOUR, 3);
		}

		if (ocorreuErro && itensAtualizando) {
			itensAtualizando = false;
			throw new EtrainingViewException(CodigoExcecao.ERRO_DESCONHECIDO);
		}

		if ((dataUltimaAtualizacao == null || cal.getTime().before(
				dataUltimaAtualizacao))
				&& !itensAtualizando) {
			itensAtualizados = false;
			itensAtualizando = true;
			ocorreuErro = false;
			atualizarListaItens();
		}

		if (!itensAtualizados) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			validarPreenchimento();
		} else {
			itensAtualizando = false;
		}
	}

	@Background
	void atualizarListaItens() {

		ConsultaListaExerciciosVO consultaExercicios = new ConsultaListaExerciciosVO();
		ConsultaListaCategoriaExercicioVO consultaCategoriaExercicios = new ConsultaListaCategoriaExercicioVO();
		try {
			RespostaConsultaListaExerciciosVO respExercicios = (RespostaConsultaListaExerciciosVO) service
					.executa(consultaExercicios);
			this.listaExercicios = respExercicios.getListaExercicios();

			RespostaConsultaListaCategoriaExercicioVO respCategoriaExercicio = (RespostaConsultaListaCategoriaExercicioVO) service
					.executa(consultaCategoriaExercicios);
			this.listaCategoriaExercicio = new ArrayList<CategoriaExercicioVO>();
			this.listaCategoriaExercicio.add(CATEGORIA_ZERO);
			this.listaCategoriaExercicio.addAll(respCategoriaExercicio
					.getListaCategoriaExercicio());

			itensAtualizados = true;
			dataUltimaAtualizacao = new Date();
		} catch (Exception e) {
			ocorreuErro = true;
			itensAtualizados = false;
			dataUltimaAtualizacao = null;
		}
	}
}
