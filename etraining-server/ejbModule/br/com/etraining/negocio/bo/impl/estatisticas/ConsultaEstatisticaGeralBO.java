package br.com.etraining.negocio.bo.impl.estatisticas;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.entidades.PontoGraficoVO;
import br.com.etraining.client.vo.impl.relatorios.geral.ConsultaEstatisticaConstants;
import br.com.etraining.client.vo.impl.relatorios.geral.ConsultaEstatisticaGeralVO;
import br.com.etraining.client.vo.impl.relatorios.geral.RespostaConsultaEstatisticaVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicio;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.bo.transformer.GraficoEstatisticaTransformer;
import br.com.etraining.negocio.conversor.impl.ConversorExercicio;

@Named("ConsultaEstatisticaGeralVO")
public class ConsultaEstatisticaGeralBO extends
		AbstractBO<ConsultaEstatisticaGeralVO, RespostaConsultaEstatisticaVO> {

	@Inject
	private GraficoEstatisticaTransformer graficoTransformer;

	@Inject
	private IDaoExercicio exercicioDao;

	@Inject
	private ConversorExercicio conversorExercicio;

	@Override
	protected RespostaConsultaEstatisticaVO executarRegrasEspecificas(
			ConsultaEstatisticaGeralVO request) throws ETrainingException {

		ExercicioVO exercicio = null;
		Long idExercicio = null;
		if (ConsultaEstatisticaConstants.CONSULTA_POR_EXERCICIOS.equals(request
				.getTipoGrafico())) {
			idExercicio = request.getIdExercicio();

			EntExercicio exerc = exercicioDao.pesquisar(idExercicio);
			exercicio = conversorExercicio.toVO(exerc);
		}

		List<PontoGraficoVO> pontosReais = graficoTransformer.getPontosReais(
				request.getDataInicio(), request.getDataFim(), idExercicio,
				null);
		List<PontoGraficoVO> pontosPropostos = graficoTransformer
				.getPontosPropostos(request.getDataInicio(),
						request.getDataFim(), idExercicio, null);

		RespostaConsultaEstatisticaVO resposta = new RespostaConsultaEstatisticaVO();
		resposta.setExercicio(exercicio);
		resposta.setListaPontosReais(pontosReais);
		resposta.setListaPontosPropostos(pontosPropostos);
		resposta.setTipoGrafico(request.getTipoGrafico());
		return resposta;
	}

	public GraficoEstatisticaTransformer getGraficoTransformer() {
		return graficoTransformer;
	}

	public void setGraficoTransformer(
			GraficoEstatisticaTransformer graficoTransformer) {
		this.graficoTransformer = graficoTransformer;
	}

	public IDaoExercicio getExercicioDao() {
		return exercicioDao;
	}

	public void setExercicioDao(IDaoExercicio exercicioDao) {
		this.exercicioDao = exercicioDao;
	}

	public ConversorExercicio getConversorExercicio() {
		return conversorExercicio;
	}

	public void setConversorExercicio(ConversorExercicio conversorExercicio) {
		this.conversorExercicio = conversorExercicio;
	}

}
