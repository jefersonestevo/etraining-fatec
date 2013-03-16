package br.com.etraining.jobs.dadosaluno;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.jobs.EtrainingJob;
import br.com.etraining.modelo.dao.interfaces.IDaoParametros;
import br.com.etraining.modelo.entidades.EntParametros;

@Singleton
public class JobAtualizacaoDadosAluno implements EtrainingJob {

	@Inject
	private IDaoParametros parametrosDao;

	@Inject
	private ImportadorDadosAluno importadorDadosAluno;

	@PostConstruct
	@Schedule(dayOfWeek = "*", hour = "*", minute = "*", second = "*/60", persistent = false)
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
								e.printStackTrace();
							}
						}
					}
				}

			}

		} catch (ETrainingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
