package br.com.etraining.negocio.bo.impl.exerciciorealizado;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.impl.entidades.ExercicioRealizadoSimplesVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.ConsultaListaExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RespostaConsultaListaExercicioRealizadoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicioRealizado;
import br.com.etraining.modelo.entidades.EntExercicioRealizado;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorExercicioRealizadoSimples;

@Named("ConsultaListaExercicioRealizadoVO")
public class ConsultaExercicioRealizadoBO
		extends
		AbstractBO<ConsultaListaExercicioRealizadoVO, RespostaConsultaListaExercicioRealizadoVO> {

	@Inject
	private IDaoExercicioRealizado exercicioRealizadoDao;

	@Inject
	private ConversorExercicioRealizadoSimples conversorExercicioRealizadoSimples;

	@Override
	protected RespostaConsultaListaExercicioRealizadoVO executarRegrasEspecificas(
			ConsultaListaExercicioRealizadoVO request)
			throws ETrainingException {
		RespostaConsultaListaExercicioRealizadoVO response = new RespostaConsultaListaExercicioRealizadoVO();

		List<EntExercicioRealizado> listaExercicioRealizadoBanco = exercicioRealizadoDao
				.pesquisarListaPorUsuarioData(request.getIdAluno(),
						request.getData());

		List<ExercicioRealizadoSimplesVO> listaExercicioRealizadoSimples = new ArrayList<ExercicioRealizadoSimplesVO>();
		if (CollectionUtils.isNotEmpty(listaExercicioRealizadoBanco)) {
			for (EntExercicioRealizado exercicio : listaExercicioRealizadoBanco) {
				listaExercicioRealizadoSimples
						.add(conversorExercicioRealizadoSimples.toVO(exercicio));
			}
		}

		response.setListaExercicioRealizado(listaExercicioRealizadoSimples);

		return response;
	}

	public IDaoExercicioRealizado getExercicioRealizadoDao() {
		return exercicioRealizadoDao;
	}

	public void setExercicioRealizadoDao(
			IDaoExercicioRealizado exercicioRealizadoDao) {
		this.exercicioRealizadoDao = exercicioRealizadoDao;
	}

	public ConversorExercicioRealizadoSimples getConversorExercicioRealizadoSimples() {
		return conversorExercicioRealizadoSimples;
	}

	public void setConversorExercicioRealizadoSimples(
			ConversorExercicioRealizadoSimples conversorExercicioRealizadoSimples) {
		this.conversorExercicioRealizadoSimples = conversorExercicioRealizadoSimples;
	}

}
