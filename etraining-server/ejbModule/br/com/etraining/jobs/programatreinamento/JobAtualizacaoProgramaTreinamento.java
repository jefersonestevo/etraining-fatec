package br.com.etraining.jobs.programatreinamento;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.jobs.EtrainingJob;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

@Singleton
public class JobAtualizacaoProgramaTreinamento implements EtrainingJob {

	private static Logger log = Logger
			.getLogger(JobAtualizacaoProgramaTreinamento.class);

	@Inject
	private IDaoProgramaTreinamento programaTreinamentoDao;

	@Inject
	private AtualizadorProgramaTreinamento atualizadorProgramaTreinamento;

	@PostConstruct
	@Schedule(hour = "12", persistent = false)
	public void executar() {

		try {
			List<EntProgramaTreinamento> listaProgramaTreinamentoAtualizacao = programaTreinamentoDao
					.pesquisarPendentesAtualizacao();

			if (CollectionUtils.isNotEmpty(listaProgramaTreinamentoAtualizacao)) {
				for (EntProgramaTreinamento progTreinamento : listaProgramaTreinamentoAtualizacao) {
					if (progTreinamento == null)
						continue;

					try {
						atualizadorProgramaTreinamento
								.atualizarProgramaTreinamento(progTreinamento);
					} catch (Exception e) {
						log.error(
								"Erro ao realizar atualização do programa de treinamento ID: "
										+ progTreinamento.getId(), e);
						continue;
					}
				}
			}

		} catch (ETrainingException e) {
			log.error(
					"Erro na atualização da lista de programas de treinamento.",
					e);
		} catch (Exception e) {
			log.error(
					"Erro inesperado na atualização da lista de programas de treinamento.",
					e);
		}

	}

	public IDaoProgramaTreinamento getProgramaTreinamentoDao() {
		return programaTreinamentoDao;
	}

	public void setProgramaTreinamentoDao(
			IDaoProgramaTreinamento programaTreinamentoDao) {
		this.programaTreinamentoDao = programaTreinamentoDao;
	}

	public AtualizadorProgramaTreinamento getAtualizadorProgramaTreinamento() {
		return atualizadorProgramaTreinamento;
	}

	public void setAtualizadorProgramaTreinamento(
			AtualizadorProgramaTreinamento atualizadorProgramaTreinamento) {
		this.atualizadorProgramaTreinamento = atualizadorProgramaTreinamento;
	}

}
