package br.com.etraining.negocio.gerador.fake;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
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

	private Logger log = Logger
			.getLogger(GeradorProgramaTreinamentoRandomico.class);

	@Inject
	private IDaoProgramaTreinamento daoProgramaTreinamento;

	@Inject
	private IDaoExercicio daoExercicio;

	@Inject
	private IDaoAluno alunoDao;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EntProgramaTreinamento gerarProgramaTreinamento(EntAluno al,
			EntProgramaTreinamento programaTreinamentoAnterior)
			throws ETrainingException {
		EntProgramaTreinamento progTreinamento = null;
		try {
			EntAluno aluno = alunoDao.pesquisar(al.getId());
			log.debug("Inicio da geração do programa de treinamento para o aluno ID: "
					+ aluno.getId());

			progTreinamento = new EntProgramaTreinamento();
			progTreinamento.setAluno(aluno);

			int versao = 1;
			if (programaTreinamentoAnterior != null
					&& programaTreinamentoAnterior.getVersao() != null) {
				versao = programaTreinamentoAnterior.getVersao() + 1;
			}
			progTreinamento.setVersao(versao);
			progTreinamento.setVersaoAprovada(false);
			progTreinamento
					.setListaExercicioProposto(new ArrayList<EntExercicioProposto>());

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, 7);
			progTreinamento.setDataVencimento(cal.getTime());

			Integer qntdMaxPontos = aluno.getPontuacaoSemanalAluno();
			List<EntDiaSemana> listaDiasSemana = aluno.getMatricula()
					.getListaDiasTreinamento();

			int qntdPontosPorDia = qntdMaxPontos / listaDiasSemana.size();
			List<EntExercicio> listaExercicios = daoExercicio.pesquisarLista();

			if (CollectionUtils.isNotEmpty(listaExercicios)) {
				int mediaPontosExercicios = calcularMediaPontuacaoExercicio(listaExercicios);

				for (EntDiaSemana diaSemana : listaDiasSemana) {
					int pontosGastosDia = 0;
					// Deve utilizar pelo menos 90% dos pontos do dia
					int pontosMinimosAceitaveis = new Double(
							qntdPontosPorDia * 0.9).intValue();
					int pontosMaximosAceitaveis = qntdPontosPorDia;

					while (pontosGastosDia < pontosMinimosAceitaveis) {
						if ((pontosGastosDia + mediaPontosExercicios) > pontosMaximosAceitaveis) {
							break;
						}

						EntExercicioProposto exercicioProposto = new EntExercicioProposto();
						exercicioProposto.setDiaSemana(diaSemana);
						exercicioProposto
								.setProgramaTreinamento(progTreinamento);

						int indexLista = new Double(Math.random()
								* listaExercicios.size()).intValue();
						EntExercicio exerc = listaExercicios.get(indexLista);

						int quantidadePontosAtualizada = pontosGastosDia
								+ exerc.getPontosPorAtividade();
						if (quantidadePontosAtualizada > pontosMaximosAceitaveis) {
							continue;
						}

						exercicioProposto.setExercicio(exerc);
						pontosGastosDia = quantidadePontosAtualizada;

						progTreinamento.getListaExercicioProposto().add(
								exercicioProposto);
					}
				}

				daoProgramaTreinamento.inserir(progTreinamento);

				log.debug("Termino da geração do programa de treinamento para o aluno ID: "
						+ aluno.getId());

				if (programaTreinamentoAnterior != null
						&& programaTreinamentoAnterior.getId() != null) {
					EntProgramaTreinamento progAnterior = daoProgramaTreinamento
							.pesquisar(programaTreinamentoAnterior.getId());
					progAnterior.setCancelado(true);
					daoProgramaTreinamento.alterar(progAnterior);

					log.debug("Programa de treinamento com ID: "
							+ progTreinamento.getId() + ", do aluno ID: "
							+ aluno.getId() + " foi cancelado. ");
				}
			}
		} catch (ETrainingException e) {
			log.error("Falha ao gerar programa de treinamento para aluno "
					+ (al != null ? al.getId() : null), e);
			progTreinamento = null;
			throw e;
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

	public IDaoAluno getAlunoDao() {
		return alunoDao;
	}

	public void setAlunoDao(IDaoAluno alunoDao) {
		this.alunoDao = alunoDao;
	}

}
