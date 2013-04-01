package br.com.etraining.negocio.bo.impl.exercicios;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.ConsultaListaExerciciosAlunoVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaListaExerciciosAlunoVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingBusinessException;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntDiaSemana;
import br.com.etraining.modelo.entidades.EntExercicioProposto;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorExercicio;

@Named("ConsultaListaExerciciosAlunoVO")
public class ConsultaListaExerciciosAlunoBO
		extends
		AbstractBO<ConsultaListaExerciciosAlunoVO, RespostaConsultaListaExerciciosAlunoVO> {

	@Inject
	private IDaoProgramaTreinamento programaTreinamentoDao;

	@Inject
	private ConversorExercicio conversorExercicio;

	@Override
	protected RespostaConsultaListaExerciciosAlunoVO executarRegrasEspecificas(
			ConsultaListaExerciciosAlunoVO request) throws ETrainingException {
		RespostaConsultaListaExerciciosAlunoVO response = new RespostaConsultaListaExerciciosAlunoVO();

		EntProgramaTreinamento programaTreinamento = programaTreinamentoDao
				.pesquisarAtualAprovadoPorIdAluno(request.getIdAluno());

		if (programaTreinamento == null || programaTreinamento.getId() == null) {
			throw new ETrainingBusinessException(
					CodigoExcecao.PROGRAMA_TREINAMENTO_INEXISTENTE);
		}

		List<ExercicioVO> listaExercicioSugerido = new ArrayList<ExercicioVO>();
		if (CollectionUtils.isNotEmpty(programaTreinamento
				.getListaExercicioProposto())) {

			Long diaSemana = EntDiaSemana.getDiaSemana(request.getData());
			for (EntExercicioProposto exercProposto : programaTreinamento
					.getListaExercicioProposto()) {

				// Só adiciona o exercicio se ele for do dia solicitado pelo
				// usuario
				if (exercProposto.getDiaSemana() != null
						&& exercProposto.getDiaSemana().getId() != null
						&& exercProposto.getDiaSemana().getId()
								.equals(diaSemana)) {
					listaExercicioSugerido.add(conversorExercicio
							.toVO(exercProposto.getExercicio()));
				}

			}
		}

		response.setIdProgramaTreinamento(programaTreinamento.getId());
		response.setListaExerciciosSugeridos(listaExercicioSugerido);

		return response;
	}

	public IDaoProgramaTreinamento getProgramaTreinamentoDao() {
		return programaTreinamentoDao;
	}

	public void setProgramaTreinamentoDao(
			IDaoProgramaTreinamento programaTreinamentoDao) {
		this.programaTreinamentoDao = programaTreinamentoDao;
	}

	public ConversorExercicio getConversorExercicio() {
		return conversorExercicio;
	}

	public void setConversorExercicio(ConversorExercicio conversorExercicio) {
		this.conversorExercicio = conversorExercicio;
	}

}
