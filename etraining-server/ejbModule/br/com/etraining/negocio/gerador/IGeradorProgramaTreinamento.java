package br.com.etraining.negocio.gerador;

import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

public interface IGeradorProgramaTreinamento {

	public EntProgramaTreinamento gerarProgramaTreinamento(EntAluno aluno,
			EntProgramaTreinamento programaTreinamentoAnterior);

}