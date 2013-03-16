package br.com.etraining.negocio.bo.impl.exerciciorealizado;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.exerciciorealizado.ConsultaListaExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RemocaoExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RespostaConsultaListaExercicioRealizadoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicioRealizado;
import br.com.etraining.modelo.entidades.EntExercicioRealizado;
import br.com.etraining.negocio.bo.BOResolver;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.bo.interfaces.IBO;

@Named("RemocaoExercicioRealizadoVO")
public class RemocaoExercicioRealizadoBO
		extends
		AbstractBO<RemocaoExercicioRealizadoVO, RespostaConsultaListaExercicioRealizadoVO> {

	@Inject
	private IDaoExercicioRealizado exercicioRealizadoDao;

	@Inject
	private BOResolver boResolver;

	@SuppressWarnings("unchecked")
	@Override
	protected RespostaConsultaListaExercicioRealizadoVO executarRegrasEspecificas(
			RemocaoExercicioRealizadoVO request) throws ETrainingException {

		EntExercicioRealizado exercicioRealizado = exercicioRealizadoDao
				.pesquisar(request.getIdExercicioRealizado());
		exercicioRealizadoDao.remover(exercicioRealizado);

		ConsultaListaExercicioRealizadoVO consulta = new ConsultaListaExercicioRealizadoVO();
		consulta.setData(request.getData());
		consulta.setIdAluno(request.getIdAluno());

		IBO<ConsultaListaExercicioRealizadoVO, RespostaConsultaListaExercicioRealizadoVO> boDelegate = (IBO<ConsultaListaExercicioRealizadoVO, RespostaConsultaListaExercicioRealizadoVO>) boResolver
				.getBOFor(consulta.getClass());
		return boDelegate.executa(consulta);
	}

	public IDaoExercicioRealizado getExercicioRealizadoDao() {
		return exercicioRealizadoDao;
	}

	public void setExercicioRealizadoDao(
			IDaoExercicioRealizado exercicioRealizadoDao) {
		this.exercicioRealizadoDao = exercicioRealizadoDao;
	}

	public BOResolver getBoResolver() {
		return boResolver;
	}

	public void setBoResolver(BOResolver boResolver) {
		this.boResolver = boResolver;
	}

}
