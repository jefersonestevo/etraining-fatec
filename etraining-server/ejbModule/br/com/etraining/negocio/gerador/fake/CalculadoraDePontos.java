package br.com.etraining.negocio.gerador.fake;

import javax.inject.Named;

import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.gerador.ICalculadoraDePontos;

@Named
public class CalculadoraDePontos implements ICalculadoraDePontos {

	@Override
	public Integer calcularNovaPontuacaoAluno(EntAluno aluno,
			EntProgramaTreinamento programaTreinamento) {
		// TODO - Implementar Calculo de pontos do aluno corretamente
		return 400;
	}

}
