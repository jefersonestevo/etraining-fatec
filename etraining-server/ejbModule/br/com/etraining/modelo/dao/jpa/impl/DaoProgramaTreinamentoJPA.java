package br.com.etraining.modelo.dao.jpa.impl;

import java.util.List;

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
		query.append(EntProgramaTreinamento.class + " AS p ");
		query.append(" WHERE p.aluno.id = ? ");
		query.append(" AND p.versao = (");
		query.append(" SELECT MAX(p2.versao) FROM ");
		query.append(EntProgramaTreinamento.class + " AS p2 ");
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

}
