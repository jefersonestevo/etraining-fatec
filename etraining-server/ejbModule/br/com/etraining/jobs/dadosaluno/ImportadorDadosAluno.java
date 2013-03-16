package br.com.etraining.jobs.dadosaluno;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.exception.ETrainingInfraException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.dao.interfaces.IDaoDadosCorporais;
import br.com.etraining.modelo.dao.interfaces.IDaoMatricula;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntDadosCorporais;
import br.com.etraining.modelo.entidades.EntMatricula;
import br.com.etraining.negocio.gerador.ICalculadoraDePontos;
import br.com.etraining.negocio.gerador.IGeradorProgramaTreinamento;

@Named
public class ImportadorDadosAluno {

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
			while ((linha = br.readLine()) != null) {
				String[] dadosAluno = linha.split(";");
				validaDadosEntrada(dadosAluno);
				listaDadosAluno.add(new DadosAluno(dadosAluno));
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
				matriculaDao.inserir(matricula);
				dadosCorporais.setAluno(aluno);
				dadosCorporaisDao.inserir(dadosCorporais);

				aluno.setMatricula(matricula);
				aluno.setDadosCorporais(dadosCorporais);
				geradorProgramaTreinamento
						.gerarProgramaTreinamento(aluno, null);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
				}
			}
		}

	}

	private void validaDadosEntrada(String[] dadosAlunos)
			throws ETrainingException {
		for (EnumPosicionalDadosAluno en : EnumPosicionalDadosAluno.values()) {
			String valorDadoAtual = dadosAlunos[en.ordinal()];

			if (en.isObrigatorio() && StringUtils.isEmpty(valorDadoAtual)) {
				throw new ETrainingInfraException(
						CodigoExcecao.ERRO_ARQUIVO_ALUNO_CAMPO_OBRIGATORIO);
			}

			if (valorDadoAtual.trim().length() > en.getTamanhoMaximo()) {
				throw new ETrainingInfraException(
						CodigoExcecao.ERRO_ARQUIVO_ALUNO_CAMPO_MAIOR_MAXIMO);
			}

			if (en.isSomenteNumerico()
					&& !NumberUtils.isNumber(valorDadoAtual.replaceAll(",", "")
							.trim())) {
				throw new ETrainingInfraException(
						CodigoExcecao.ERRO_ARQUIVO_ALUNO_CAMPO_NAO_NUMERICO);
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

}
