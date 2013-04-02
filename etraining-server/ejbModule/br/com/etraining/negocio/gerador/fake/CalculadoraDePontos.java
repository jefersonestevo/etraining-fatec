package br.com.etraining.negocio.gerador.fake;

import javax.inject.Named;

import br.com.etraining.client.dom.Sexo;
import br.com.etraining.client.vo.impl.entidades.DadosCorporaisVO;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntDadosCorporais;
import br.com.etraining.modelo.entidades.EntExercicioProposto;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.gerador.ICalculadoraDePontos;

@Named
public class CalculadoraDePontos implements ICalculadoraDePontos {

	@Override
	public Integer calcularNovaPontuacaoAluno(EntAluno aluno,
			EntProgramaTreinamento programaTreinamento) {
		EntDadosCorporais dadosCorporais = aluno.getDadosCorporais();
		double multiplicadorFinal = Sexo.M.equals(aluno.getSexo()) ? 1 : 0.9;

		int pontuacao = 0;

		pontuacao += getPontosIMC(dadosCorporais);
		pontuacao += getPontosIndiceGorduraCorporal(dadosCorporais);
		pontuacao += getPontosTempoEsteira(dadosCorporais);

		if (programaTreinamento != null) {
			int pontosProgTreinamento = getPontosProgramaTreinamento(programaTreinamento);
			pontosProgTreinamento = new Double(pontosProgTreinamento * 0.9)
					.intValue();

			if (pontuacao < pontosProgTreinamento) {
				multiplicadorFinal += 0.1;
			}
		}
		// TODO - Implementar Calculo de pontos do aluno corretamente
		return new Double(pontuacao * multiplicadorFinal).intValue();
	}

	private int getPontosProgramaTreinamento(EntProgramaTreinamento prog) {
		int pontos = 0;
		for (EntExercicioProposto prop : prog.getListaExercicioProposto()) {
			pontos += (prop.getQuantidadeExercicioSugerida() * prop
					.getExercicio().getPontosPorAtividade());
		}
		return pontos;
	}

	private int getPontosIMC(EntDadosCorporais dadosCorporais) {
		double imc = dadosCorporais.getPeso()
				/ (dadosCorporais.getAltura() * dadosCorporais.getAltura());

		if (imc < 25) {
			return 80;
		} else if (imc < 30) {
			return 120;
		} else if (imc < 35) {
			return 80;
		} else {
			return 60;
		}
	}

	private int getPontosIndiceGorduraCorporal(EntDadosCorporais dadosCorporais) {
		double indice = dadosCorporais.getIndiceGorduraCorporal();
		indice = indice * 100;

		if (indice < 25) {
			return 80;
		} else if (indice < 50) {
			return 120;
		} else if (indice < 75) {
			return 100;
		} else {
			return 80;
		}
	}

	private int getPontosTempoEsteira(EntDadosCorporais dadosCorporais) {
		double tempo = dadosCorporais.getTempoEsteira();

		if (tempo < 25) {
			return 80;
		} else if (tempo < 50) {
			return 100;
		} else if (tempo < 75) {
			return 120;
		} else {
			return 140;
		}
	}
}
