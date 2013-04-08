package br.com.etraining.negocio.fake;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;

import br.com.etraining.client.dom.StatusProgramaTreinamento;
import br.com.etraining.client.fachada.ejb.PopuladorBase;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.dao.interfaces.IDaoDiaExercicio;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicio;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntDiaExercicio;
import br.com.etraining.modelo.entidades.EntDiaSemana;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.modelo.entidades.EntExercicioProposto;
import br.com.etraining.modelo.entidades.EntExercicioRealizado;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.utils.data.DataUtils;

@Stateless
public class GeradorDadosPopularBaseEJB implements PopuladorBase {

	private static final long serialVersionUID = -3184313706104257885L;

	private static final int QNTD_DIA_SEMANA = 7;

	@Inject
	private IDaoAluno alunoDAO;

	@Inject
	private IDaoDiaExercicio diaExercicioDAO;

	@Inject
	private IDaoExercicio exercicioDAO;

	@Inject
	private IDaoProgramaTreinamento programaTreinamentoDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean gerarDadosPopularBase() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateUtils.truncate(new Date(), Calendar.DATE));
			cal.add(Calendar.DATE, -((QNTD_DIA_SEMANA * 10) - 1));
			Date dataInicio = cal.getTime();
			Date dataFim = DateUtils.truncate(new Date(), Calendar.DATE);

			List<EntAluno> listaAlunos = alunoDAO.pesquisarLista();
			List<EntExercicio> listaExercicio = exercicioDAO.pesquisarLista();
			Date dataAtual = DateUtils.truncate(new Date(), Calendar.DATE);

			for (EntAluno aluno : listaAlunos) {
				Date dataInicioAluno = dataInicio;
				int versao = 1;

				EntProgramaTreinamento progTreinamentoAtual = programaTreinamentoDAO
						.pesquisarUltimoPorIdAluno(aluno.getId());

				while (!dataInicioAluno.after(dataFim)) {
					Date dataTerminoProgTreinamento = DataUtils.getProximaData(
							dataInicioAluno, Calendar.SATURDAY);
					Date dataInicioProgTreinamento = DateUtils.addDays(
							dataTerminoProgTreinamento, -QNTD_DIA_SEMANA);

					if (dataAtual.before(dataTerminoProgTreinamento)) {
						dataTerminoProgTreinamento = dataAtual;
					}

					if (dataAtual.before(dataInicioProgTreinamento)) {
						break;
					}

					List<EntProgramaTreinamento> listaProgTreinamento = programaTreinamentoDAO
							.pesquisarListaProgramaAprovadoPorAluno(aluno
									.getId());
					boolean criarProg = true;
					for (EntProgramaTreinamento prog : listaProgTreinamento) {
						if (prog.getDataAprovacao().equals(
								dataInicioProgTreinamento)) {
							criarProg = false;
							break;
						}
					}

					if (criarProg) {
						this.criarProgTreinamento(aluno,
								dataInicioProgTreinamento,
								dataTerminoProgTreinamento, versao,
								listaExercicio);
						versao++;
					}

					Calendar calInicio = Calendar.getInstance();
					calInicio.setTime(dataInicioAluno);
					boolean contemDiaAluno = false;
					int diaSemanaAtual = calInicio.get(Calendar.DAY_OF_WEEK);
					for (EntDiaSemana diaSemana : aluno.getMatricula()
							.getListaDiasTreinamento()) {
						if (diaSemanaAtual == diaSemana.getId()) {
							contemDiaAluno = true;
							break;
						}
					}

					// Só gera exercicios realizados para os dias da semana que
					// o aluno possui cadastrados
					if (contemDiaAluno) {
						EntDiaExercicio diaExercicio = new EntDiaExercicio();
						diaExercicio.setAluno(aluno);
						diaExercicio.setDataRealizacao(dataInicioAluno);
						diaExercicio
								.setListaExercicioRealizado(new ArrayList<EntExercicioRealizado>());

						for (int j = 0; j < 3; j++) {
							EntExercicioRealizado exercRealizado = new EntExercicioRealizado();
							exercRealizado.setDiaExercicio(diaExercicio);

							int index = new Double(Math.random()
									* listaExercicio.size()).intValue();
							EntExercicio exercicio = listaExercicio.get(index);
							exercRealizado.setExercicio(exercicio);

							if (new Long(50l) < exercicio
									.getPontosPorAtividade()) {
								exercRealizado.setQuantidadeAtividade(1);
							} else {
								exercRealizado
										.setQuantidadeAtividade(new Double(Math
												.random() * 3).intValue() + 1);
							}

							Double pontos = new Integer(
									exercicio.getPontosPorAtividade()
											* exercRealizado
													.getQuantidadeAtividade())
									.doubleValue();
							exercRealizado.setPontos(pontos);

							diaExercicio.getListaExercicioRealizado().add(
									exercRealizado);
						}

						diaExercicioDAO.inserir(diaExercicio);
					}
					dataInicioAluno = DateUtils.addDays(dataInicioAluno, 1);
				}

				progTreinamentoAtual.setVersao(versao);
				programaTreinamentoDAO.alterar(progTreinamentoAtual);

			}
		} catch (ETrainingException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao popular base", e);
		}

		return false;
	}

	private void criarProgTreinamento(EntAluno aluno, Date dataInicial,
			Date dataFinal, int versao, List<EntExercicio> listaExercicio)
			throws ETrainingException {
		EntProgramaTreinamento progTreinamento = new EntProgramaTreinamento();
		progTreinamento.setAluno(aluno);
		progTreinamento.setVersao(versao);
		progTreinamento.setStatus(StatusProgramaTreinamento.APROVADO);
		progTreinamento
				.setListaExercicioProposto(new ArrayList<EntExercicioProposto>());
		progTreinamento.setDataAprovacao(dataInicial);
		progTreinamento.setDataVencimento(dataFinal);
		progTreinamento.setDataCancelamento(dataFinal);
		progTreinamento.setListaExercicioProposto(getListaExercProposto(aluno,
				progTreinamento));
		programaTreinamentoDAO.inserir(progTreinamento);
	}

	private List<EntExercicioProposto> getListaExercProposto(EntAluno aluno,
			EntProgramaTreinamento progTreinamento) throws ETrainingException {
		List<EntExercicioProposto> listaExercicioProposto = new ArrayList<EntExercicioProposto>();

		Integer qntdMaxPontos = aluno.getPontuacaoSemanalAluno();
		List<EntDiaSemana> listaDiasSemana = aluno.getMatricula()
				.getListaDiasTreinamento();

		int qntdPontosPorDia = qntdMaxPontos / listaDiasSemana.size();
		List<EntExercicio> listaExercicios = exercicioDAO.pesquisarLista();

		if (CollectionUtils.isNotEmpty(listaExercicios)) {
			int mediaPontosExercicios = calcularMediaPontuacaoExercicio(listaExercicios);

			for (EntDiaSemana diaSemana : listaDiasSemana) {
				Set<Long> mapExercicioAdicionado = new HashSet<Long>();

				int pontosGastosDia = 0;
				// Deve utilizar pelo menos 90% dos pontos do dia
				int pontosMinimosAceitaveis = new Double(qntdPontosPorDia * 0.9)
						.intValue();
				int pontosMaximosAceitaveis = qntdPontosPorDia;

				// Variável par controlar a tentativa de inserção do
				// exercicio por dia. Caso seja realizado 5 tentativas de
				// adicionar o mesmo exercício no dia, para o while para
				// evitar um possível loop infinito
				int tentativasInsercaoExercicio = 0;
				while ((pontosGastosDia < pontosMinimosAceitaveis)
						&& (tentativasInsercaoExercicio < 5)) {
					if ((pontosGastosDia + mediaPontosExercicios) > pontosMaximosAceitaveis) {
						break;
					}

					int indexLista = new Double(Math.random()
							* listaExercicios.size()).intValue();
					EntExercicio exerc = listaExercicios.get(indexLista);

					// Não permite que seja adicionar o mesmo exercício duas
					// vezes no mesmo dia
					if (mapExercicioAdicionado.contains(exerc.getId())) {
						tentativasInsercaoExercicio++;
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
					exercicioProposto.setProgramaTreinamento(progTreinamento);
					exercicioProposto.setExercicio(exerc);
					exercicioProposto
							.setQuantidadeExercicioSugerida(qntdExercSugerida);
					pontosGastosDia = quantidadePontosAtualizada;

					listaExercicioProposto.add(exercicioProposto);
				}
			}
		}

		return listaExercicioProposto;
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

	public IDaoAluno getAlunoDAO() {
		return alunoDAO;
	}

	public void setAlunoDAO(IDaoAluno alunoDAO) {
		this.alunoDAO = alunoDAO;
	}

	public IDaoDiaExercicio getDiaExercicioDAO() {
		return diaExercicioDAO;
	}

	public void setDiaExercicioDAO(IDaoDiaExercicio diaExercicioDAO) {
		this.diaExercicioDAO = diaExercicioDAO;
	}

	public IDaoExercicio getExercicioDAO() {
		return exercicioDAO;
	}

	public void setExercicioDAO(IDaoExercicio exercicioDAO) {
		this.exercicioDAO = exercicioDAO;
	}
}
