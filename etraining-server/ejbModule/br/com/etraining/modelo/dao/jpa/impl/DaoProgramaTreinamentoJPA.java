package br.com.etraining.modelo.dao.jpa.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.dom.StatusProgramaTreinamento;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

@Named
public class DaoProgramaTreinamentoJPA extends
		DaoCRUDJPA<EntProgramaTreinamento> implements IDaoProgramaTreinamento {

	@Override
	protected Class<EntProgramaTreinamento> getEntidadePersistente() {
		return EntProgramaTreinamento.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntProgramaTreinamento.NOME_ENTIDADE;
	}

	@Override
	public EntProgramaTreinamento pesquisarUltimoPorIdAluno(Long idAluno)
			throws ETrainingException {

		StringBuilder query = new StringBuilder();
		query.append(" SELECT p FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p ");
		query.append(" WHERE p.aluno.id = ? ");
		query.append(" AND p.versao = (");
		query.append(" SELECT MAX(p2.versao) FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p2 ");
		query.append(" WHERE p2.aluno.id = ? ");
		query.append(" AND ");
		query.append(" ( p2.status = ? ");
		query.append(" OR p2.status = ? ) ");
		query.append(" ) ");

		List<EntProgramaTreinamento> lista = getTemplate().pesquisarQuery(
				EntProgramaTreinamento.class,
				query.toString(),
				new Object[] { idAluno, idAluno,
						StatusProgramaTreinamento.AGUARDANDO_APROVACAO,
						StatusProgramaTreinamento.APROVADO });

		if (CollectionUtils.isNotEmpty(lista))
			return lista.get(0);
		return null;
	}

	@Override
	public EntProgramaTreinamento pesquisarAtualAprovadoPorIdAluno(Long idAluno)
			throws ETrainingException {

		StringBuilder query = new StringBuilder();
		query.append(" SELECT p FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p ");
		query.append(" WHERE p.aluno.id = ? ");
		query.append(" AND p.versao = (");
		query.append(" SELECT MAX(p2.versao) FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p2 ");
		query.append(" WHERE p2.aluno.id = ? ");
		query.append(" AND p2.status = ? ");
		query.append(" ) ");

		List<EntProgramaTreinamento> lista = getTemplate().pesquisarQuery(
				EntProgramaTreinamento.class,
				query.toString(),
				new Object[] { idAluno, idAluno,
						StatusProgramaTreinamento.APROVADO });

		if (CollectionUtils.isNotEmpty(lista))
			return lista.get(0);
		return null;
	}

	@Override
	public List<EntProgramaTreinamento> pesquisarPendentesAtualizacao()
			throws ETrainingException {

		StringBuilder query = new StringBuilder();
		query.append(" SELECT p FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p ");
		query.append(" WHERE ");
		query.append(" p.dataVencimento <= ? ");
		query.append(" AND p.status = ? ");

		// Verifica se a data do programa de treinamento é anterior à 7 dias
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		Date dataSemanaPassada = cal.getTime();

		List<EntProgramaTreinamento> lista = getTemplate().pesquisarQuery(
				EntProgramaTreinamento.class,
				query.toString(),
				new Object[] { dataSemanaPassada,
						StatusProgramaTreinamento.APROVADO });

		Map<Long, EntProgramaTreinamento> mapProgramaPorAluno = new HashMap<Long, EntProgramaTreinamento>();
		for (EntProgramaTreinamento prog : lista) {
			if (prog == null || prog.getAluno() == null
					|| prog.getAluno().getId() == null) {
				continue;
			}

			EntProgramaTreinamento progAtualAluno = mapProgramaPorAluno
					.get(prog.getAluno().getId());
			if (progAtualAluno == null) {
				mapProgramaPorAluno.put(prog.getAluno().getId(), prog);
			} else if (progAtualAluno.getVersao() < prog.getVersao()) {
				mapProgramaPorAluno.put(prog.getAluno().getId(), prog);
			}
		}

		return new ArrayList<EntProgramaTreinamento>(
				mapProgramaPorAluno.values());
	}

	@Override
	public List<EntProgramaTreinamento> pesquisarVersoesPosteriores(
			Long idAluno, Integer versaoAtual) throws ETrainingException {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT p FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p ");
		query.append(" WHERE ");
		query.append(" p.aluno.id = ? ");
		query.append(" AND p.versao >= ? ");

		return getTemplate().pesquisarQuery(EntProgramaTreinamento.class,
				query.toString(), new Object[] { idAluno, versaoAtual });
	}

	@Override
	public EntProgramaTreinamento pesquisarPendenteAprovacaoPorIdAluno(
			Long idAluno) throws ETrainingException {

		StringBuilder query = new StringBuilder();
		query.append(" SELECT p FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p ");
		query.append(" WHERE p.aluno.id = ? ");
		query.append(" AND p.status = ? ");

		List<EntProgramaTreinamento> lista = getTemplate().pesquisarQuery(
				EntProgramaTreinamento.class,
				query.toString(),
				new Object[] { idAluno,
						StatusProgramaTreinamento.AGUARDANDO_APROVACAO });

		if (CollectionUtils.isNotEmpty(lista))
			return lista.get(0);
		return null;
	}

	@Override
	public List<EntProgramaTreinamento> pesquisarListaAprovadosCancelados(
			Date dataInicial, Date dataFinal, Long idExercicio, Long idAluno)
			throws ETrainingException {
		StringBuilder queryFrom = new StringBuilder();
		StringBuilder queryJoin = new StringBuilder();
		StringBuilder queryWhere = new StringBuilder();
		List<Object> listaParametros = new ArrayList<Object>();

		queryFrom.append(" SELECT DISTINCT p FROM ");
		queryFrom.append(EntProgramaTreinamento.class.getName() + " AS p ");
		queryWhere.append(" WHERE ");
		// Busca os programas que estao aprovados ou cancelados
		queryWhere.append(" (p.status = ? ");
		listaParametros.add(StatusProgramaTreinamento.APROVADO);
		queryWhere.append(" OR p.status = ?) ");
		listaParametros.add(StatusProgramaTreinamento.CANCELADO);

		// Como o programa de treinamento pode ser cancelado sem ser aprovado,
		// busca somente os que possuem data de aprovação
		queryWhere.append(" AND p.dataAprovacao IS NOT NULL ");

		if (dataInicial != null) {
			queryWhere.append(" AND ");
			queryWhere.append(" p.dataAprovacao >= ? ");
			listaParametros.add(dataInicial);
		}

		if (dataFinal != null) {
			queryWhere.append(" AND ");
			queryWhere.append(" p.dataAprovacao <= ? ");
			listaParametros.add(dataFinal);
		}

		if (idExercicio != null) {
			queryJoin.append(" JOIN p.listaExercicioProposto AS exercProp ");
			
			queryWhere.append(" AND ");
			queryWhere.append(" exercProp.exercicio.id = ? ");
			listaParametros.add(idExercicio);
		}

		if (idAluno != null) {
			queryWhere.append(" AND ");
			queryWhere.append(" p.aluno.id = ? ");
			listaParametros.add(idAluno);
		}

		String queryFinal = queryFrom.toString() + queryJoin.toString()
				+ queryWhere.toString();
		return getTemplate().pesquisarQuery(EntProgramaTreinamento.class,
				queryFinal, listaParametros.toArray());

	}

	@Override
	public List<EntProgramaTreinamento> pesquisarListaProgramaPendenteAprovacaoPorAluno(
			Long idAluno) throws ETrainingException {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT p FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p ");
		query.append(" WHERE p.aluno.id = ? ");
		query.append(" AND p.status = ? ");

		return getTemplate().pesquisarQuery(
				EntProgramaTreinamento.class,
				query.toString(),
				new Object[] { idAluno,
						StatusProgramaTreinamento.AGUARDANDO_APROVACAO });
	}

	@Override
	public List<EntProgramaTreinamento> pesquisarListaProgramaAprovadoPorAluno(
			Long idAluno) throws ETrainingException {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT p FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p ");
		query.append(" WHERE p.aluno.id = ? ");
		query.append(" AND p.status = ? ");

		return getTemplate().pesquisarQuery(EntProgramaTreinamento.class,
				query.toString(),
				new Object[] { idAluno, StatusProgramaTreinamento.APROVADO });
	}
}
