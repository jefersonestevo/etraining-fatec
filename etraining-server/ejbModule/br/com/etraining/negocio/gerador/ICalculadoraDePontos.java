package br.com.etraining.negocio.gerador;

import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

import br.com.etraining.modelo.entidades.EntAluno;

public interface ICalculadoraDePontos {

	public Integer calcularNovaPontuacaoAluno(EntAluno aluno,
			EntProgramaTreinamento programaTreinamento);

}
