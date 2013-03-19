package br.com.etraining.negocio.gerador;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

public interface IGeradorProgramaTreinamento {

	/**
	 * Gera um novo programa de treinamento para o aluno informado e insere este
	 * programa de treinamento na base. <br />
	 * Caso seja informado o programa de treinamento anterior, utilizará este
	 * para gerar uma nova versão do programa de treinamento e deverá cancelar o
	 * programa de treinamento anterior.
	 * 
	 * @param aluno
	 *            - Aluno para geração do programa de treinamento
	 * @param programaTreinamentoAnterior
	 *            - Será utilizado para gerar a versão do programa de
	 *            treinamento atual e cancelado após a criação do novo programa
	 *            de treinamento.
	 * @return - Novo programa de treinamento atualizado
	 * @throws ETrainingException
	 */
	public EntProgramaTreinamento gerarProgramaTreinamento(EntAluno aluno,
			EntProgramaTreinamento programaTreinamentoAnterior)
			throws ETrainingException;

}