package br.com.etraining.jobs.dadosaluno;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.exception.ETrainingImportacaoException;
import br.com.etraining.exception.ETrainingInfraException;
import br.com.etraining.exception.ErroImportacao;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.dao.interfaces.IDaoDadosCorporais;
import br.com.etraining.modelo.dao.interfaces.IDaoMatricula;
import br.com.etraining.modelo.dao.interfaces.IDaoPerfilAcesso;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntDadosCorporais;
import br.com.etraining.modelo.entidades.EntMatricula;
import br.com.etraining.modelo.entidades.EntPerfilAcesso;
import br.com.etraining.negocio.gerador.ICalculadoraDePontos;
import br.com.etraining.negocio.gerador.IGeradorProgramaTreinamento;
import br.com.etraining.utils.io.IOUtils;

@Named
public class ImportadorDadosAluno {

	private Logger log = Logger.getLogger(ImportadorDadosAluno.class);

	@Inject
	private IDaoAluno alunoDao;

	@Inject
	private IDaoMatricula matriculaDao;

	@Inject
	private IDaoDadosCorporais dadosCorporaisDao;

	@Inject
	private IGeradorProgramaTreinamento geradorProgramaTreinamento;

	@Inject
	private ICalculadoraDePontos calculadoraPontosAluno;

	@Inject
	private IDaoPerfilAcesso perfilAcessoDao;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void importarDadosAluno(File arquivoAlunos)
			throws ETrainingException {

		ETrainingImportacaoException erro = new ETrainingImportacaoException(
				CodigoExcecao.ERRO_IMPORTACAO_DADOS_ALUNO);

		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(arquivoAlunos);
			br = new BufferedReader(fr);

			// Pula linha do cabecalho
			br.readLine();
			String linha;

			List<DadosAluno> listaDadosAluno = new ArrayList<DadosAluno>();
			int num_linha = 2;
			while ((linha = br.readLine()) != null) {
				try {
					String[] dadosAluno = linha.split(";");
					validaDadosEntrada(dadosAluno, num_linha);
					listaDadosAluno.add(new DadosAluno(dadosAluno, num_linha));
					num_linha++;
				} catch (ETrainingInfraException e) {
					erro.adicionarErro(new ErroImportacao(e.getCodigoExcecao(),
							e.getMessage(), num_linha));
					num_linha++;
				}
			}

			if (CollectionUtils.isNotEmpty(erro.getListaErros())) {
				throw erro;
			}

			List<EntPerfilAcesso> listaPerfilAcesso = perfilAcessoDao
					.pesquisarLista();
			Map<String, EntPerfilAcesso> mapPerfilAcesso = new HashMap<String, EntPerfilAcesso>();
			for (EntPerfilAcesso p : listaPerfilAcesso) {
				mapPerfilAcesso.put(StringUtils.upperCase(p.getNome()), p);
			}

			for (DadosAluno dadosAluno : listaDadosAluno) {
				try {

					EntAluno alunoMesmaMatricula = alunoDao
							.pesquisarPorMatricula(dadosAluno.getMatricula()
									.getNumeroMatricula());
					if (alunoMesmaMatricula != null) {
						throw new ETrainingInfraException(
								CodigoExcecao.MATRICULA_EXISTENTE,
								"A matricula "
										+ dadosAluno.getMatricula()
												.getNumeroMatricula()
										+ " já existe.");
					}

					EntAluno aluno = dadosAluno.getAluno();
					EntMatricula matricula = dadosAluno.getMatricula();
					EntDadosCorporais dadosCorporais = dadosAluno
							.getDadosCorporais();

					aluno.setDadosCorporais(dadosCorporais);
					aluno.setMatricula(matricula);
					Integer novaPontuacaoAluno = calculadoraPontosAluno
							.calcularNovaPontuacaoAluno(aluno, null);
					aluno.setPontuacaoSemanalAluno(novaPontuacaoAluno);
					aluno.setDadosCorporais(null);
					aluno.setMatricula(null);

					alunoDao.inserir(aluno);
					matricula.setAluno(aluno);

					// Preenche os perfis de acesso
					matricula
							.setListaPerfilAcesso(new ArrayList<EntPerfilAcesso>());
					for (String perfilStr : dadosAluno.getListaPerfilAcesso()) {
						EntPerfilAcesso p = mapPerfilAcesso.get(StringUtils
								.upperCase(perfilStr));
						if (p != null)
							matricula.getListaPerfilAcesso().add(p);
					}

					matriculaDao.inserir(matricula);
					dadosCorporais.setAluno(aluno);
					dadosCorporaisDao.inserir(dadosCorporais);

					aluno.setMatricula(matricula);
					aluno.setDadosCorporais(dadosCorporais);
					geradorProgramaTreinamento.gerarProgramaTreinamento(aluno,
							null);
				} catch (ETrainingException e) {
					erro.adicionarErro(new ErroImportacao(e.getCodigoExcecao(),
							e.getMessage(), dadosAluno.getLinha()));
					throw erro;
				}
			}

		} catch (FileNotFoundException e) {
			log.error("Arquivo para importacao não encontrado.", e);
			erro.adicionarErro(new ErroImportacao(
					CodigoExcecao.ERRO_LEITURA_ARQUIVO_IMPORTACAO_ALUNOS,
					"Arquivo para importação dos alunos não encontrado  - "
							+ arquivoAlunos.getPath()));
			throw erro;
		} catch (IOException e) {
			log.error("Erro de IO para importacao de dados dos alunos", e);
			erro.adicionarErro(new ErroImportacao(
					CodigoExcecao.ERRO_LEITURA_ARQUIVO_IMPORTACAO_ALUNOS,
					"Erro de I/O no arquivo de importação de Alunos  - "
							+ arquivoAlunos.getPath()));
			throw erro;
		} catch (Exception e) {
			if (e instanceof ETrainingException) {
				throw (ETrainingException) e;
			}
			log.error("Erro inesperado na importacao de dados dos alunos", e);
			erro.adicionarErro(new ErroImportacao(
					CodigoExcecao.ERRO_DESCONHECIDO,
					"Erro inesperado na importação de dados dos alunos - "
							+ arquivoAlunos.getPath()));
			throw erro;
		} finally {
			IOUtils.closeQuietly(fr);
			IOUtils.closeQuietly(br);
		}

	}

	private void validaDadosEntrada(String[] dadosAlunos, Integer numLinha)
			throws ETrainingException {
		for (EnumPosicionalDadosAluno en : EnumPosicionalDadosAluno.values()) {
			String valorDadoAtual = dadosAlunos[en.ordinal()];

			if (en.isObrigatorio() && StringUtils.isEmpty(valorDadoAtual)) {
				throw new ETrainingInfraException(
						CodigoExcecao.ERRO_ARQUIVO_ALUNO_CAMPO_OBRIGATORIO,
						"CAMPO: " + en.name()
								+ " - CAMPO OBRIGATORIO NAO INFORMADO");
			}

			if (valorDadoAtual.trim().length() > en.getTamanhoMaximo()) {
				throw new ETrainingInfraException(
						CodigoExcecao.ERRO_ARQUIVO_ALUNO_CAMPO_MAIOR_MAXIMO,
						"CAMPO: " + en.name()
								+ " - TAMANHO SUPERIOR AO MAXIMO PERMITIDO");
			}

			if (en.isSomenteNumerico()
					&& !NumberUtils.isNumber(valorDadoAtual.replaceAll(",", "")
							.trim())) {
				throw new ETrainingInfraException(
						CodigoExcecao.ERRO_ARQUIVO_ALUNO_CAMPO_NAO_NUMERICO,
						"CAMPO: " + en.name() + " - SOMENTE PERMITIDO NUMEROS");
			}
		}
	}

	public IDaoAluno getAlunoDao() {
		return alunoDao;
	}

	public void setAlunoDao(IDaoAluno alunoDao) {
		this.alunoDao = alunoDao;
	}

	public IDaoMatricula getMatriculaDao() {
		return matriculaDao;
	}

	public void setMatriculaDao(IDaoMatricula matriculaDao) {
		this.matriculaDao = matriculaDao;
	}

	public IDaoDadosCorporais getDadosCorporaisDao() {
		return dadosCorporaisDao;
	}

	public void setDadosCorporaisDao(IDaoDadosCorporais dadosCorporaisDao) {
		this.dadosCorporaisDao = dadosCorporaisDao;
	}

	public ICalculadoraDePontos getCalculadoraPontosAluno() {
		return calculadoraPontosAluno;
	}

	public void setCalculadoraPontosAluno(
			ICalculadoraDePontos calculadoraPontosAluno) {
		this.calculadoraPontosAluno = calculadoraPontosAluno;
	}

	public IDaoPerfilAcesso getPerfilAcessoDao() {
		return perfilAcessoDao;
	}

	public void setPerfilAcessoDao(IDaoPerfilAcesso perfilAcessoDao) {
		this.perfilAcessoDao = perfilAcessoDao;
	}

	public IGeradorProgramaTreinamento getGeradorProgramaTreinamento() {
		return geradorProgramaTreinamento;
	}

	public void setGeradorProgramaTreinamento(
			IGeradorProgramaTreinamento geradorProgramaTreinamento) {
		this.geradorProgramaTreinamento = geradorProgramaTreinamento;
	}

}
