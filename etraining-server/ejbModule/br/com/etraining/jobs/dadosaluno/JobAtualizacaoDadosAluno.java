package br.com.etraining.jobs.dadosaluno;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.exception.ETrainingImportacaoException;
import br.com.etraining.exception.ErroImportacao;
import br.com.etraining.jobs.EtrainingJob;
import br.com.etraining.modelo.dao.interfaces.IDaoParametros;
import br.com.etraining.modelo.entidades.EntParametros;
import br.com.etraining.utils.io.FileUtils;
import br.com.etraining.utils.io.IOUtils;

@Singleton
public class JobAtualizacaoDadosAluno implements EtrainingJob {

	private Logger log = Logger.getLogger(JobAtualizacaoDadosAluno.class);

	private static final String SUFIXO_ARQUIVO_ENTRADA = ".IN";
	private static final String SUFIXO_ARQUIVO_PROCESSANDO = ".PROC";
	private static final String SUFIXO_ARQUIVO_PROCESSADO_COM_SUCESSO = ".OK";
	private static final String SUFIXO_ARQUIVO_COM_ERRO = ".ERROR";

	@Inject
	private IDaoParametros parametrosDao;

	@Inject
	private ImportadorDadosAluno importadorDadosAluno;

	@PostConstruct
	@Schedule(dayOfWeek = "*", hour = "*", minute = "*/5", second = "*", persistent = false)
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
										arquivo.getName(),
										SUFIXO_ARQUIVO_ENTRADA)) {

									String nomeOriginalArquivo = arquivo
											.getAbsolutePath();
									log.debug("Leitura do arquivo: \""
											+ nomeOriginalArquivo
											+ "\" para importação de usuarios");

									// Renomeia o arquivo para .PROC enquanto
									// estiver realizando a importacao para
									// evitar que outra instancia do job rode e
									// tente utilizar o mesmo arquivo para
									// importação
									arquivo = FileUtils
											.renomearArquivoComSufixo(arquivo,
													SUFIXO_ARQUIVO_PROCESSANDO);
									importadorDadosAluno
											.importarDadosAluno(arquivo);

									// Renomeia o arquivo para .OK para que ele
									// não seja mais utilizado em nenhum
									// processo de importação e avisar o usuário
									// que a importação do arquivo foi realizada
									// com sucesso.
									arquivo = FileUtils
											.renomearArquivoComSufixo(arquivo,
													SUFIXO_ARQUIVO_PROCESSADO_COM_SUCESSO);
									log.debug("Fim da leitura do arquivo: \""
											+ nomeOriginalArquivo
											+ "\" para importação de usuarios");

								}
							} catch (ETrainingException e) {
								arquivo = FileUtils.renomearArquivoComSufixo(
										arquivo, SUFIXO_ARQUIVO_COM_ERRO);
								if (e instanceof ETrainingImportacaoException) {
									adicionarArquivoErro(
											(ETrainingImportacaoException) e,
											param.getValor(), arquivo.getName());
								}
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

	private void adicionarArquivoErro(ETrainingImportacaoException erro,
			String caminhoArquivo, String nomeArquivo) {

		PrintWriter writer = null;

		try {
			File file = new File(caminhoArquivo + File.separator + nomeArquivo
					+ ".log");
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

			writer = new PrintWriter(file);

			writer.println("Erro de importação do arquivo "
					+ caminhoArquivo
					+ File.separator
					+ nomeArquivo
					+ " ocorrida em "
					+ new SimpleDateFormat("dd/MM/yyyy HH:mm")
							.format(new Date()));
			int i = 0;
			for (ErroImportacao err : erro.getListaErros()) {
				writer.print(err.getCodigoExcecao().getCodigo());
				if (err.getLinha() != null) {
					writer.print(" - LINHA: " + err.getLinha());
				}
				writer.println(" - ERRO: " + err.getMessage());
				if (i % 10 == 0) {
					writer.flush();
				}
				i++;
			}
			writer.flush();
		} catch (Exception e) {
			log.error("Erro ao tentar criar arquivo de log de erro", e);
		} finally {
			IOUtils.closeQuietly(writer);
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
