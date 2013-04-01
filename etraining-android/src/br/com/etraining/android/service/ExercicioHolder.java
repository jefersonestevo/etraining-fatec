package br.com.etraining.android.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.client.vo.impl.categoriaexercicio.ConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.categoriaexercicio.RespostaConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaListaExerciciosVO;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;

@EBean(scope = Scope.Singleton)
public class ExercicioHolder {

	@Bean
	EtrainingAndroidService service;

	private Date dataUltimaAtualizacao = null;
	private boolean itensAtualizados = false;
	private boolean itensAtualizando = false;
	private List<ExercicioVO> listaExercicios = new ArrayList<ExercicioVO>();
	private List<CategoriaExercicioVO> listaCategoriaExercicio = new ArrayList<CategoriaExercicioVO>();

	public synchronized List<ExercicioVO> getListaExercicios() {
		validarPreenchimento();
		return listaExercicios;
	}

	public synchronized List<CategoriaExercicioVO> getListaCategoriaExercicio() {
		validarPreenchimento();
		return listaCategoriaExercicio;
	}

	private synchronized void validarPreenchimento() {
		Calendar cal = Calendar.getInstance();
		if (dataUltimaAtualizacao != null) {
			cal.setTime(dataUltimaAtualizacao);
			cal.add(Calendar.HOUR, 3);
		}

		if ((dataUltimaAtualizacao == null || cal.getTime().before(
				dataUltimaAtualizacao))
				&& !itensAtualizando) {
			itensAtualizados = false;
			itensAtualizando = true;
			atualizarListaItens();
		}

		if (!itensAtualizados) {
			validarPreenchimento();
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
			this.listaCategoriaExercicio = respCategoriaExercicio
					.getListaCategoriaExercicio();
		} catch (EtrainingViewException e) {
		} finally {
			itensAtualizados = true;
			itensAtualizando = false;
			dataUltimaAtualizacao = new Date();
		}
	}
}
