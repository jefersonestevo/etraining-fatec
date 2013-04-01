package br.com.etraining.negocio.bo.transformer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.time.DateUtils;

import br.com.etraining.client.vo.impl.entidades.PontoGraficoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicioRealizado;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntExercicioProposto;
import br.com.etraining.modelo.entidades.EntExercicioRealizado;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.utils.data.DataUtils;

@Named
public class GraficoEstatisticaTransformer {

	@Inject
	private IDaoExercicioRealizado exercicioRealizadoDao;

	@Inject
	private IDaoProgramaTreinamento programaTreinamentoDao;

	public List<PontoGraficoVO> getPontosReais(Date dataInicio, Date dataFim,
			Long idExercicio, Long idAluno) throws ETrainingException {
		List<EntExercicioRealizado> listaExerciciosRealizados = exercicioRealizadoDao
				.pesquisarListaPorDataExercicioAluno(dataInicio, dataFim,
						idExercicio, idAluno);

		Map<Date, PontoGraficoVO> pontosReais = new HashMap<Date, PontoGraficoVO>();

		for (EntExercicioRealizado exercRealizado : listaExerciciosRealizados) {
			Date data = DataUtils.getProximaData(exercRealizado
					.getDiaExercicio().getDataRealizacao(), Calendar.SUNDAY);
			PontoGraficoVO ponto = pontosReais.get(data);
			if (ponto == null) {
				ponto = new PontoGraficoVO(data);
				pontosReais.put(data, ponto);
			}
			Long pontuacao = new Double(ponto.getPontos()
					+ exercRealizado.getPontos()).longValue();
			ponto.setPontos(pontuacao);
		}
		return new ArrayList<PontoGraficoVO>(pontosReais.values());
	}

	public List<PontoGraficoVO> getPontosPropostos(Date dataInicio,
			Date dataFim, Long idExercicio, Long idAluno)
			throws ETrainingException {

		List<EntProgramaTreinamento> listaProgramaTreinamento = programaTreinamentoDao
				.pesquisarLista(dataInicio, dataFim, idExercicio, idAluno);

		Map<Date, PontoGraficoVO> pontosReais = new HashMap<Date, PontoGraficoVO>();

		for (EntProgramaTreinamento programaTreinamento : listaProgramaTreinamento) {
			Date dataProg = programaTreinamento.getDataVencimento();
			// Procura o proximo domingo
			Date data = DataUtils.getProximaData(dataProg, Calendar.SUNDAY);

			// Se o programa de treinamento foi cancelado antes do domingo, não
			// o considera para contagem
			if (programaTreinamento.getDataCancelamento() != null
					&& data.after(DateUtils.truncate(
							programaTreinamento.getDataCancelamento(),
							Calendar.DATE))) {
				continue;
			}

			PontoGraficoVO ponto = pontosReais.get(data);
			if (ponto == null) {
				ponto = new PontoGraficoVO(data);
				pontosReais.put(data, ponto);
			}
			for (EntExercicioProposto exercProposto : programaTreinamento
					.getListaExercicioProposto()) {

				if (idExercicio != null) {
					if (!idExercicio.equals(exercProposto.getExercicio()
							.getId()))
						continue;
				}

				Long pontuacao = ponto.getPontos()
						+ (exercProposto.getExercicio().getPontosPorAtividade() * exercProposto
								.getQuantidadeExercicioSugerida());
				ponto.setPontos(pontuacao);
			}
		}

		return new ArrayList<PontoGraficoVO>(pontosReais.values());
	}

	public IDaoExercicioRealizado getExercicioRealizadoDao() {
		return exercicioRealizadoDao;
	}

	public void setExercicioRealizadoDao(
			IDaoExercicioRealizado exercicioRealizadoDao) {
		this.exercicioRealizadoDao = exercicioRealizadoDao;
	}

	public IDaoProgramaTreinamento getProgramaTreinamentoDao() {
		return programaTreinamentoDao;
	}

	public void setProgramaTreinamentoDao(
			IDaoProgramaTreinamento programaTreinamentoDao) {
		this.programaTreinamentoDao = programaTreinamentoDao;
	}

}
