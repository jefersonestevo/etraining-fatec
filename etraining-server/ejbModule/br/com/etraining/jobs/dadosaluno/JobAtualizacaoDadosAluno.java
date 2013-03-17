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
import br.com.etraining.utils.io.FileUtils;

@Singleton
public class JobAtualizacaoDadosAluno implements EtrainingJob {

	private Logger log = Logger.getLogger(JobAtualizacaoDadosAluno.class);

	@Inject
	private IDaoParametros parametrosDao;

	@Inject
	private ImportadorDadosAluno importadorDadosAluno;

	@PostConstruct
	@Schedule(dayOfWeek = "*", hour = "*", minute = "*/1", second = "*", persistent = false)
	public void executar() {

		try {
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

									String nomeOriginalArquivo = arquivo
											.getAbsolutePath();
									log.debug("Leitura do arquivo: \""
											+ nomeOriginalArquivo
											+ "\" para importação de usuarios");

									arquivo = FileUtils
											.renomearArquivoComSufixo(arquivo,
													".PROC");
									importadorDadosAluno
											.importarDadosAluno(arquivo);

									arquivo = FileUtils
											.renomearArquivoComSufixo(arquivo,
													".OK");
									log.debug("Fim da leitura do arquivo: \""
											+ nomeOriginalArquivo
											+ "\" para importação de usuarios");

								}
							} catch (ETrainingException e) {
								FileUtils.renomearArquivoComSufixo(arquivo,
										".ERROR");
								log.error("Erro na importação do arquivo "
										+ arquivo.getAbsolutePath(), e);
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
