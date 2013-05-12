package br.com.etraining.negocio.gerador.fake;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import br.com.etraining.client.dom.StatusProgramaTreinamento;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingBusinessException;
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

	private static Logger log = Logger
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
			Integer versao = 1;
			if (programaTreinamentoAnterior != null
					&& programaTreinamentoAnterior.getVersao() != null) {
				versao = programaTreinamentoAnterior.getVersao();
			}

			List<EntProgramaTreinamento> listaVersoesPosteriores = daoProgramaTreinamento
					.pesquisarVersoesPosteriores(al.getId(), versao);

			if (CollectionUtils.isNotEmpty(listaVersoesPosteriores)) {
				throw new ETrainingBusinessException(
						CodigoExcecao.VERSAO_POSTERIOR_PROG_TREINAMENTO_EXISTENTE);
			}

			EntAluno aluno = alunoDao.pesquisar(al.getId());
			log.debug("Inicio da geração do programa de treinamento para o aluno ID: "
					+ aluno.getId());

			progTreinamento = new EntProgramaTreinamento();
			progTreinamento.setAluno(aluno);
			progTreinamento.setVersao(versao);
			progTreinamento
					.setStatus(StatusProgramaTreinamento.AGUARDANDO_APROVACAO);
			progTreinamento
					.setListaExercicioProposto(new ArrayList<EntExercicioProposto>());

			Date dataAtual = null;
			if (programaTreinamentoAnterior != null
					&& programaTreinamentoAnterior.getDataVencimento() != null) {
				dataAtual = programaTreinamentoAnterior.getDataVencimento();

				Calendar cal = Calendar.getInstance();
				cal.setTime(dataAtual);
				cal.add(Calendar.DATE, 7);
				dataAtual = cal.getTime();
			}
			progTreinamento.setDataVencimento(dataAtual);

			Integer qntdMaxPontos = aluno.getPontuacaoSemanalAluno();
			List<EntDiaSemana> listaDiasSemana = aluno.getMatricula()
					.getListaDiasTreinamento();

			int qntdPontosPorDia = qntdMaxPontos / listaDiasSemana.size();
			List<EntExercicio> listaExercicios = daoExercicio.pesquisarLista();

			if (CollectionUtils.isNotEmpty(listaExercicios)) {
				int mediaPontosExercicios = calcularMediaPontuacaoExercicio(listaExercicios);

				for (EntDiaSemana diaSemana : listaDiasSemana) {
					Set<Long> mapExercicioAdicionado = new HashSet<Long>();

					int pontosGastosDia = 0;
					// Deve utilizar pelo menos 90% dos pontos do dia
					int pontosMinimosAceitaveis = new Double(
							qntdPontosPorDia * 0.9).intValue();
					int pontosMaximosAceitaveis = qntdPontosPorDia;

					// Variável par controlar a tentativa de inserção do
					// exercicio por dia. Caso seja realizado 5 tentativas de
					// adicionar o mesmo exercício no dia, para o while para
					// evitar um possível loop infinito
					// int tentativasInsercaoExercicio = 0;
					for (int i = 0; i < 3; i++) {
						if ((pontosGastosDia + mediaPontosExercicios) > pontosMaximosAceitaveis) {
							break;
						}

						int indexLista = new Double(Math.random()
								* listaExercicios.size()).intValue();
						EntExercicio exerc = listaExercicios.get(indexLista);

						// Não permite que seja adicionar o mesmo exercício duas
						// vezes no mesmo dia
						if (mapExercicioAdicionado.contains(exerc.getId())) {
							// tentativasInsercaoExercicio++;
							continue;
						}
						mapExercicioAdicionado.add(exerc.getId());

						int qntdExercSugerida = 1;
						// Se os pontos do exercicio forem menores que 33% da
						// media dos exercicios, cria uma quantidade sugerida de
						// 1 a 3 para o exercício.
						if (exerc.getPontosPorAtividade() < (mediaPontosExercicios / 3)) {
							qntdExercSugerida = new Double(Math.random() * 3)
									.intValue() + 1;
						}

						int pontosSugeridos = exerc.getPontosPorAtividade()
								* qntdExercSugerida;
						int quantidadePontosAtualizada = pontosGastosDia
								+ pontosSugeridos;
						if (quantidadePontosAtualizada > pontosMaximosAceitaveis) {
							continue;
						}

						EntExercicioProposto exercicioProposto = new EntExercicioProposto();
						exercicioProposto.setDiaSemana(diaSemana);
						exercicioProposto
								.setProgramaTreinamento(progTreinamento);
						exercicioProposto.setExercicio(exerc);
						exercicioProposto
								.setQuantidadeExercicioSugerida(qntdExercSugerida);
						pontosGastosDia = quantidadePontosAtualizada;

						progTreinamento.getListaExercicioProposto().add(
								exercicioProposto);
					}
				}

				daoProgramaTreinamento.inserir(progTreinamento);

				if (programaTreinamentoAnterior != null
						&& programaTreinamentoAnterior.getDataVencimento() != null) {
					// Após gerar o novo programa de treinamento, cancelar todos
					// os que estiverem aguardando aprovacao
					List<EntProgramaTreinamento> listaPendentesAprovacao = daoProgramaTreinamento
							.pesquisarListaProgramaPendenteAprovacaoPorAluno(programaTreinamentoAnterior
									.getAluno().getId());
					for (EntProgramaTreinamento prog : listaPendentesAprovacao) {
						prog.setDataCancelamento(new Date());
						prog.setStatus(StatusProgramaTreinamento.CANCELADO);
						daoProgramaTreinamento.alterar(prog);
					}

				}

				log.debug("Termino da geração do programa de treinamento para o aluno ID: "
						+ aluno.getId());
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
