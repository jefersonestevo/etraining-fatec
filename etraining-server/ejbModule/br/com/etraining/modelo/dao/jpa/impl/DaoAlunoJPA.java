package br.com.etraining.modelo.dao.jpa.impl;

import java.util.List;

import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingBusinessException;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntAluno;

@Named
public class DaoAlunoJPA extends DaoCRUDJPA<EntAluno> implements IDaoAluno {

	@Override
	protected Class<EntAluno> getEntidadePersistente() {
		return EntAluno.class;
	}

	@Override
	protected String getNomeEntidadee() {
		return EntAluno.NOME_ENTIDADE;
	}

	@Override
	public EntAluno pesquisarAlunoPorMatriculaSenha(Long matricula, String senha)
			throws ETrainingException {

		StringBuilder query = new StringBuilder();
		query.append(" SELECT a FROM ");
		query.append(EntAluno.class + " AS a ");
		query.append(" WHERE a.matricula.numeroMatricula = ? ");
		query.append(" AND a.matricula.senhaUsuario = ? ");

		List<EntAluno> lista = getTemplate().pesquisarQuery(EntAluno.class,
				query.toString(), new Object[] { matricula, senha });

		if (CollectionUtils.isNotEmpty(lista))
			return lista.get(0);
		throw new ETrainingBusinessException(
				CodigoExcecao.USUARIO_NAO_ENCONTRADO);
	}
}
