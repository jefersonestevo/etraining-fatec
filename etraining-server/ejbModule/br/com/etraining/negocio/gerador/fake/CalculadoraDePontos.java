package br.com.etraining.negocio.gerador.fake;

import javax.inject.Named;

import br.com.etraining.client.dom.Sexo;
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

		int qntdDias = aluno.getMatricula().getListaDiasTreinamento().size();

		EntDadosCorporais dadosCorporais = aluno.getDadosCorporais();
		double multiplicadorFinal = Sexo.M.equals(aluno.getSexo()) ? 1.1 : 1;

		int pontuacao = 100;

		pontuacao += getPontosIMC(dadosCorporais);
		pontuacao += getPontosIndiceGorduraCorporal(dadosCorporais);
		pontuacao += getPontosTempoEsteira(dadosCorporais);
		pontuacao += getPontosPressaoArterial(dadosCorporais);

		if (programaTreinamento != null) {
			int pontosProgTreinamento = getPontosProgramaTreinamento(programaTreinamento);
			pontosProgTreinamento = new Double(pontosProgTreinamento * 0.9)
					.intValue();

			// Calcula a pontuacao media do programa de treinamento por dia
			pontosProgTreinamento = pontosProgTreinamento / qntdDias;
			if (pontuacao < pontosProgTreinamento) {
				// Se a quantidade de pontuacao atual for menor que a média do
				// prog anterior, aumenta ela em 10%
				multiplicadorFinal += 0.1;
			}
		}

		return new Double(pontuacao * multiplicadorFinal).intValue() * qntdDias;
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
		if (dadosCorporais.getPeso() == null
				|| dadosCorporais.getAltura() == null)
			return EnumCalculoPontos.IMC.getMedio();

		double imc = dadosCorporais.getPeso()
				/ (dadosCorporais.getAltura() * dadosCorporais.getAltura());

		if (imc < 25) {
			return EnumCalculoPontos.IMC.getMedio();
		} else if (imc < 30) {
			return EnumCalculoPontos.IMC.getMinimo();
		} else if (imc < 35) {
			return EnumCalculoPontos.IMC.getMaximo();
		} else {
			return EnumCalculoPontos.IMC.getMinimo();
		}
	}

	private int getPontosIndiceGorduraCorporal(EntDadosCorporais dadosCorporais) {
		if (dadosCorporais.getIndiceGorduraCorporal() == null)
			return EnumCalculoPontos.IND_GORD_CORPORAL.getMedio();

		double indice = dadosCorporais.getIndiceGorduraCorporal();
		indice = indice * 100;

		if (indice < 25) {
			return EnumCalculoPontos.IND_GORD_CORPORAL.getMedio();
		} else if (indice < 50) {
			return EnumCalculoPontos.IND_GORD_CORPORAL.getMaximo();
		} else if (indice < 75) {
			return EnumCalculoPontos.IND_GORD_CORPORAL.getMedio();
		} else {
			return EnumCalculoPontos.IND_GORD_CORPORAL.getMinimo();
		}
	}

	private int getPontosTempoEsteira(EntDadosCorporais dadosCorporais) {
		if (dadosCorporais.getTempoEsteira() == null)
			return EnumCalculoPontos.TEMPO_ESTEIRA.getMedio();
		double tempo = dadosCorporais.getTempoEsteira();

		if (tempo < 25) {
			return EnumCalculoPontos.TEMPO_ESTEIRA.getMinimo();
		} else if (tempo < 50) {
			return EnumCalculoPontos.TEMPO_ESTEIRA.getMedio();
		} else {
			return EnumCalculoPontos.TEMPO_ESTEIRA.getMaximo();
		}
	}

	private int getPontosPressaoArterial(EntDadosCorporais dadosCorporais) {
		if (dadosCorporais.getPressaoArterialMaxima() == null
				|| dadosCorporais.getPressaoArterialMinima() == null)
			return EnumCalculoPontos.PRESSAO_ARTERIAL.getMedio();

		int valor = 0;
		double minima = dadosCorporais.getPressaoArterialMinima();

		if (minima < 6) {
			valor = EnumCalculoPontos.PRESSAO_ARTERIAL.getMinimo();
		} else if (minima < 8) {
			valor = EnumCalculoPontos.PRESSAO_ARTERIAL.getMedio();
		} else {
			valor = EnumCalculoPontos.PRESSAO_ARTERIAL.getMaximo();
		}

		double maxima = dadosCorporais.getPressaoArterialMaxima();

		if (maxima < 9) {
			valor += EnumCalculoPontos.PRESSAO_ARTERIAL.getMinimo();
		} else if (maxima < 11) {
			valor += EnumCalculoPontos.PRESSAO_ARTERIAL.getMedio();
		} else {
			valor += EnumCalculoPontos.PRESSAO_ARTERIAL.getMaximo();
		}

		return valor / 2;
	}
}
