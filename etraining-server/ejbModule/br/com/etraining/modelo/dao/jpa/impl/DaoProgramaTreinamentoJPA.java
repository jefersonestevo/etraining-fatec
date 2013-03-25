package br.com.etraining.modelo.dao.jpa.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

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
	public EntProgramaTreinamento pesquisarAtualPorIdAluno(Long idAluno)
			throws ETrainingException {

		StringBuilder query = new StringBuilder();
		query.append(" SELECT p FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p ");
		query.append(" WHERE p.aluno.id = ? ");
		query.append(" AND p.versao = (");
		query.append(" SELECT MAX(p2.versao) FROM ");
		query.append(EntProgramaTreinamento.class.getName() + " AS p2 ");
		query.append(" WHERE p2.aluno.id = ? ");
		query.append(" AND p2.versaoAprovada = true ");
		query.append(" AND p2.cancelado = false");
		query.append(" ) ");

		List<EntProgramaTreinamento> lista = getTemplate().pesquisarQuery(
				EntProgramaTreinamento.class, query.toString(),
				new Object[] { idAluno });

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
		query.append(" AND p.versaoAprovada = true ");
		query.append(" AND p.cancelado = false ");

		// Verifica se a data do programa de treinamento é anterior à 7 dias
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		Date dataSemanaPassada = cal.getTime();

		List<EntProgramaTreinamento> lista = getTemplate().pesquisarQuery(
				EntProgramaTreinamento.class, query.toString(),
				new Object[] { dataSemanaPassada });

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

}
