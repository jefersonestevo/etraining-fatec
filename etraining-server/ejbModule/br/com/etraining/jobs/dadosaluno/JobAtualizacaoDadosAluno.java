package br.com.etraining.jobs.dadosaluno;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.jobs.EtrainingJob;
import br.com.etraining.modelo.dao.interfaces.IDaoParametros;
import br.com.etraining.modelo.entidades.EntParametros;

@Singleton
public class JobAtualizacaoDadosAluno implements EtrainingJob {

	private Logger log = Logger.getLogger(JobAtualizacaoDadosAluno.class);

	@Inject
	private IDaoParametros parametrosDao;

	@Inject
	private ImportadorDadosAluno importadorDadosAluno;

	@PostConstruct
	@Schedule(dayOfWeek = "*", hour = "*", minute = "*", second = "*/60", persistent = false)
	public void executar() {

		try {
			System.out.println("TESTE");
			EntParametros param = parametrosDao
					.pesquisar(EntParametros.ID_DIRETORIO_CARGA_ALUNOS);

			if (param != null && StringUtils.isNotBlank(param.getValor())) {
				String diretorioArquivos = param.getValor();

				File file = new File(diretorioArquivos);

				if (file.exists()) {
					if (file.listFiles() != null) {
						for (int i = 0; i < file.listFiles().length; i++) {
							File arquivo = file.listFiles()[i];
							try {
								// Realiza leitura somente de arquivos .in
								if (StringUtils.endsWithIgnoreCase(
										arquivo.getName(), ".in")) {

									log.debug("Leitura do arquivo: "
											+ arquivo.getAbsolutePath()
											+ " -  para importação de usuarios");

									String novoNomeArquivo = arquivo.getName()
											.substring(
													0,
													arquivo.getName()
															.lastIndexOf("."))
											+ ".PROC";
									arquivo.renameTo(new File(arquivo
											.getAbsolutePath(), novoNomeArquivo));
									importadorDadosAluno
											.importarDadosAluno(arquivo);

									novoNomeArquivo = arquivo.getName()
											.replaceAll(".PROC", ".OK");
									arquivo.renameTo(new File(arquivo
											.getAbsolutePath(), novoNomeArquivo));
								}
							} catch (ETrainingException e) {
								String novoNomeArquivo = arquivo.getName()
										.substring(
												0,
												arquivo.getName().lastIndexOf(
														"."))
										+ ".ERROR";
								arquivo.renameTo(new File(novoNomeArquivo));
								log.error("Erro na importação do arquivo "
										+ novoNomeArquivo, e);
							}
						}
					}
				}

			}

		} catch (ETrainingException e) {
			log.error(
					"Erro ao realizar importação do arquivo de carga de alunos",
					e);
		} catch (Exception e) {
			log.error(
					"Erro inesperado ao realizar importação do arquivo de carga de alunos",
					e);
		}

	}

	public IDaoParametros getParametrosDao() {
		return parametrosDao;
	}

	public void setParametrosDao(IDaoParametros parametrosDao) {
		this.parametrosDao = parametrosDao;
	}

	public ImportadorDadosAluno getImportadorDadosAluno() {
		return importadorDadosAluno;
	}

	public void setImportadorDadosAluno(
			ImportadorDadosAluno importadorDadosAluno) {
		this.importadorDadosAluno = importadorDadosAluno;
	}

}
