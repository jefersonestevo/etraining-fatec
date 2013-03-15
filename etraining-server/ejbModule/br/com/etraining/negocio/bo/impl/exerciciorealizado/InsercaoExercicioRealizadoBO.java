package br.com.etraining.negocio.bo.impl.exerciciorealizado;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.exerciciorealizado.InsercaoExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RespostaConsultaListaExercicioRealizadoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;

@Named("InsercaoExercicioRealizadoVO")
public class InsercaoExercicioRealizadoBO
		extends
		AbstractBO<InsercaoExercicioRealizadoVO, RespostaConsultaListaExercicioRealizadoVO> {

	@Override
	protected RespostaConsultaListaExercicioRealizadoVO executarRegrasEspecificas(
			InsercaoExercicioRealizadoVO request) throws ETrainingException {
		// TODO - Implementar InsercaoExercicioRealizadoBO

		return null;
	}

}
