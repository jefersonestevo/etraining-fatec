package br.com.etraining.negocio.bo.transformer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
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
				.pesquisarListaAprovadosCancelados(dataInicio, dataFim,
						idExercicio, idAluno);

		if (CollectionUtils.isEmpty(listaProgramaTreinamento)) {
			return null;
		}

		Map<Date, PontoGraficoVO> pontosReais = new HashMap<Date, PontoGraficoVO>();

		Map<Long, List<EntProgramaTreinamento>> mapProgramaTreinamentoPorUsuario = new HashMap<Long, List<EntProgramaTreinamento>>();

		for (EntProgramaTreinamento programa : listaProgramaTreinamento) {
			List<EntProgramaTreinamento> listaPT = mapProgramaTreinamentoPorUsuario
					.get(programa.getAluno().getId());
			if (listaPT == null) {
				listaPT = new ArrayList<EntProgramaTreinamento>();
				mapProgramaTreinamentoPorUsuario.put(programa.getAluno()
						.getId(), listaPT);
			}
			listaPT.add(programa);
		}

		// Itera nos programas de treinamento para cada usuario
		for (Entry<Long, List<EntProgramaTreinamento>> entry : mapProgramaTreinamentoPorUsuario
				.entrySet()) {
			List<EntProgramaTreinamento> listaPT = entry.getValue();
			// Ordena os programas de treinamento pela data de aprovação, para
			// realizar a contagem de forma crescente no tempo
			Collections.sort(listaPT,
					new ComparadorProgramaTreinamentoDataAprovacao());

			// Itera sobre os programas de treinamento do usuário
			for (EntProgramaTreinamento prog : listaPT) {
				Date dataFinal = prog.getDataCancelamento();
				if (dataFinal == null)
					dataFinal = prog.getDataVencimento();
				if (dataFinal.after(new Date()))
					dataFinal = new Date();

				Date dataInicial = DateUtils.truncate(prog.getDataAprovacao(),
						Calendar.DATE);
				dataFinal = DateUtils.truncate(dataFinal, Calendar.DATE);

				// Percorre todas as datas entre a data de aprovação e a data de
				// cancelamento (ou atual) do programa de treinamento para
				// contabilizar os pontos
				while (!dataFinal.after(dataInicial)) {
					Date data = DataUtils.getProximaData(dataFinal,
							Calendar.SUNDAY);
					PontoGraficoVO ponto = pontosReais.get(data);
					if (ponto == null) {
						ponto = new PontoGraficoVO(data);
						pontosReais.put(data, ponto);
					}
					// Para cada data percorrida, verifica seu dia da semana e
					// quantos pontos estavam propostos para aquele dia da
					// semana no programa de treinamento
					Long pontuacao = ponto.getPontos()
							+ getPontuacao(dataFinal,
									prog.getListaExercicioProposto(),
									idExercicio);
					ponto.setPontos(pontuacao);

					dataFinal = DateUtils.addDays(dataFinal, 1);
				}

			}
		}

		return new ArrayList<PontoGraficoVO>(pontosReais.values());
	}

	private Long getPontuacao(Date data,
			List<EntExercicioProposto> listaExercicioProposto, Long idExercicio) {
		if (data == null)
			return 0l;

		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		Long indice = new Long(cal.get(Calendar.DAY_OF_WEEK));

		Long pontuacao = 0l;
		for (EntExercicioProposto prop : listaExercicioProposto) {
			if (indice.equals(prop.getDiaSemana().getId())) {
				// realizar a contagem do exercicio proposto por padrão
				boolean contar = true;

				// Se possuir exercício e o exercicio do exercicio proposto não
				// for o informado, não o conta.
				if (idExercicio != null
						&& !idExercicio.equals(prop.getExercicio().getId())) {
					contar = false;
				}

				if (contar) {
					pontuacao += new Long(prop.getQuantidadeExercicioSugerida()
							* prop.getExercicio().getPontosPorAtividade());
				}
			}
		}

		return pontuacao;
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
