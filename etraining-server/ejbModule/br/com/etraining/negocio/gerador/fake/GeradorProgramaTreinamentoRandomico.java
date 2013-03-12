package br.com.etraining.negocio.gerador.fake;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicio;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntDiaSemana;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.modelo.entidades.EntExercicioProposto;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.gerador.IGeradorProgramaTreinamento;

@Named
public class GeradorProgramaTreinamentoRandomico implements
		IGeradorProgramaTreinamento {

	@Inject
	private IDaoProgramaTreinamento daoProgramaTreinamento;

	@Inject
	private IDaoExercicio daoExercicio;

	@Override
	public EntProgramaTreinamento gerarProgramaTreinamento(EntAluno aluno) {
		EntProgramaTreinamento progTreinamento = null;
		try {
			progTreinamento = new EntProgramaTreinamento();
			progTreinamento.setAluno(aluno);
			progTreinamento.setVersao(1);
			progTreinamento.setVersaoAprovada(false);
			progTreinamento
					.setListaExercicioProposto(new ArrayList<EntExercicioProposto>());

			Integer qntdMaxPontos = aluno.getPontuacaoSemanalAluno();
			List<EntDiaSemana> listaDiasSemana = aluno.getMatricula()
					.getListaDiasTreinamento();

			int qntdPontosPorDia = qntdMaxPontos / listaDiasSemana.size();
			List<EntExercicio> listaExercicios = daoExercicio.pesquisarLista();
			int mediaPontosExercicios = calcularMediaPontuacaoExercicio(listaExercicios);

			for (EntDiaSemana diaSemana : listaDiasSemana) {
				int pontosGastosDia = 0;
				// Deve utilizar pelo menos 90% dos pontos do dia
				int pontosMinimosAceitaveis = Integer.valueOf(Double
						.toString(qntdPontosPorDia * 0.9));
				int pontosMaximosAceitaveis = qntdPontosPorDia;

				while (pontosGastosDia < pontosMinimosAceitaveis) {
					if ((pontosGastosDia + mediaPontosExercicios) > pontosMaximosAceitaveis) {
						break;
					}

					EntExercicioProposto exercicioProposto = new EntExercicioProposto();
					exercicioProposto.setDiaSemana(diaSemana);
					exercicioProposto.setProgramaTreinamento(progTreinamento);

					int indexLista = (int) Math.random()
							* listaExercicios.size();
					EntExercicio exerc = listaExercicios.get(indexLista);

					int quantidadePontosAtualizada = pontosGastosDia
							+ exerc.getPontosPorAtividade();
					if (quantidadePontosAtualizada > pontosMaximosAceitaveis) {
						continue;
					}

					exercicioProposto.setExercicio(exerc);
					pontosGastosDia = quantidadePontosAtualizada;

					// TODO - Incluir atividade no exercicio proposto

					progTreinamento.getListaExercicioProposto().add(
							exercicioProposto);
				}
			}

			daoProgramaTreinamento.inserir(progTreinamento);
		} catch (ETrainingException e) {
			progTreinamento = null;
		}
		return progTreinamento;
	}

	private Integer calcularMediaPontuacaoExercicio(
			List<EntExercicio> listaExercicios) {
		int quantidade = 0;
		int qntdPontos = 0;
		for (EntExercicio exerc : listaExercicios) {
			qntdPontos += exerc.getPontosPorAtividade();
			quantidade++;
		}

		if (quantidade > 0)
			return qntdPontos / quantidade;
		else
			return 0;
	}

	public IDaoProgramaTreinamento getDaoProgramaTreinamento() {
		return daoProgramaTreinamento;
	}

	public void setDaoProgramaTreinamento(
			IDaoProgramaTreinamento daoProgramaTreinamento) {
		this.daoProgramaTreinamento = daoProgramaTreinamento;
	}

	public IDaoExercicio getDaoExercicio() {
		return daoExercicio;
	}

	public void setDaoExercicio(IDaoExercicio daoExercicio) {
		this.daoExercicio = daoExercicio;
	}

}
