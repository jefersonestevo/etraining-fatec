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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.exception.ETrainingInfraException;
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
					validaDadosEntrada(dadosAluno);
					listaDadosAluno.add(new DadosAluno(dadosAluno));
					num_linha++;
				} catch (ETrainingInfraException e) {
					throw new ETrainingInfraException(e.getCodigoExcecao(),
							"LINHA: " + num_linha + " - " + e.getMessage());
				}
			}

			List<EntPerfilAcesso> listaPerfilAcesso = perfilAcessoDao
					.pesquisarLista();
			Map<String, EntPerfilAcesso> mapPerfilAcesso = new HashMap<String, EntPerfilAcesso>();
			for (EntPerfilAcesso p : listaPerfilAcesso) {
				mapPerfilAcesso.put(StringUtils.upperCase(p.getNome()), p);
			}

			for (DadosAluno dadosAluno : listaDadosAluno) {
				EntAluno aluno = dadosAluno.getAluno();
				EntMatricula matricula = dadosAluno.getMatricula();
				EntDadosCorporais dadosCorporais = dadosAluno
						.getDadosCorporais();

				Integer novaPontuacaoAluno = calculadoraPontosAluno
						.calcularNovaPontuacaoAluno(aluno, null);
				aluno.setPontuacaoSemanalAluno(novaPontuacaoAluno);

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
				geradorProgramaTreinamento
						.gerarProgramaTreinamento(aluno, null);
			}

		} catch (FileNotFoundException e) {
			log.error("Arquivo para importacao não encontrado.", e);
			throw new ETrainingInfraException(
					CodigoExcecao.ERRO_LEITURA_ARQUIVO_IMPORTACAO_ALUNOS);
		} catch (IOException e) {
			log.error("Erro de IO para importacao de dados dos alunos", e);
			throw new ETrainingInfraException(
					CodigoExcecao.ERRO_LEITURA_ARQUIVO_IMPORTACAO_ALUNOS);
		} catch (Exception e) {
			if (e instanceof ETrainingException) {
				throw ((ETrainingInfraException) e);
			}
			log.error("Erro inesperado na importacao de dados dos alunos", e);
			throw new ETrainingInfraException(CodigoExcecao.ERRO_DESCONHECIDO);
		} finally {
			IOUtils.closeQuietly(fr);
			IOUtils.closeQuietly(br);
		}

	}

	private void validaDadosEntrada(String[] dadosAlunos)
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
