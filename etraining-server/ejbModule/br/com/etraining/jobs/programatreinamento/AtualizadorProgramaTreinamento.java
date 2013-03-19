package br.com.etraining.jobs.programatreinamento;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.gerador.ICalculadoraDePontos;
import br.com.etraining.negocio.gerador.IGeradorProgramaTreinamento;

@Named
public class AtualizadorProgramaTreinamento {

	private static Logger log = Logger
			.getLogger(AtualizadorProgramaTreinamento.class);

	@Inject
	private IDaoAluno alunoDao;

	@Inject
	private ICalculadoraDePontos calculadoraDePontos;

	@Inject
	private IGeradorProgramaTreinamento geradorProgramaTreinamento;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void atualizarProgramaTreinamento(
			EntProgramaTreinamento progTreinamento) throws ETrainingException {
		EntAluno aluno = alunoDao.pesquisar(progTreinamento.getAluno().getId());
		Integer pontuacaoAluno = calculadoraDePontos
				.calcularNovaPontuacaoAluno(aluno, progTreinamento);
		aluno.setPontuacaoSemanalAluno(pontuacaoAluno);
		alunoDao.alterar(aluno);

		EntProgramaTreinamento progTreinamentoAtualizado = geradorProgramaTreinamento
				.gerarProgramaTreinamento(aluno, progTreinamento);
		
		log.debug("Atualizado programa de treinamento para o alunod ID: "
				+ aluno.getId() + " - Novo Programa de Treinamento ID: "
				+ ((progTreinamentoAtualizado) != null ? progTreinamentoAtualizado
				.getId() : null));
	}

	public IDaoAluno getAlunoDao() {
		return alunoDao;
	}

	public void setAlunoDao(IDaoAluno alunoDao) {
		this.alunoDao = alunoDao;
	}

	public ICalculadoraDePontos getCalculadoraDePontos() {
		return calculadoraDePontos;
	}

	public void setCalculadoraDePontos(ICalculadoraDePontos calculadoraDePontos) {
		this.calculadoraDePontos = calculadoraDePontos;
	}

	public IGeradorProgramaTreinamento getGeradorProgramaTreinamento() {
		return geradorProgramaTreinamento;
	}

	public void setGeradorProgramaTreinamento(
			IGeradorProgramaTreinamento geradorProgramaTreinamento) {
		this.geradorProgramaTreinamento = geradorProgramaTreinamento;
	}

}
