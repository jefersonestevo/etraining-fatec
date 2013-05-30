package br.com.etraining.modelo.dao.jpa.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingBusinessException;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.def.impl.jpa.DaoCRUDJPA;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntPerfilAcesso;

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
	public EntAluno pesquisarAlunoPorMatriculaSenha(String matricula,
			String senha) throws ETrainingException {

		StringBuilder query = new StringBuilder();
		query.append(" SELECT a FROM ");
		query.append(EntAluno.class.getName() + " AS a ");
		query.append(" WHERE a.matricula.numeroMatricula = ? ");
		query.append(" AND a.matricula.senhaUsuario = ? ");

		List<EntAluno> lista = getTemplate().pesquisarQuery(EntAluno.class,
				query.toString(), new Object[] { matricula, senha });

		if (CollectionUtils.isNotEmpty(lista))
			return lista.get(0);
		throw new ETrainingBusinessException(
				CodigoExcecao.USUARIO_NAO_ENCONTRADO);
	}

	@Override
	public List<EntAluno> pesquisarPorMatriculaNome(String matricula,
			String nome, Long idPerfilAcesso) throws ETrainingException {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT a FROM ");
		query.append(EntAluno.class.getName() + " AS a ");

		boolean where = false;
		if (StringUtils.isNotBlank(matricula)) {
			if (!where) {
				query.append(" WHERE ");
				where = true;
			} else
				query.append(" OR ");
			query.append(" upper(a.matricula.numeroMatricula) LIKE '%"
					+ StringUtils.upperCase(matricula) + "%' ");
		}

		if (StringUtils.isNotBlank(nome)) {
			if (!where) {
				query.append(" WHERE ");
				where = true;
			} else
				query.append(" OR ");
			query.append(" upper(a.nome) LIKE '%" + StringUtils.upperCase(nome)
					+ "%' ");
		}

		List<EntAluno> lista = getTemplate().pesquisarQuery(EntAluno.class,
				query.toString());
		List<EntAluno> listaNova = new ArrayList<EntAluno>();

		if (idPerfilAcesso != null) {
			for (EntAluno aluno : lista) {
				boolean hasPerfil = false;
				for (EntPerfilAcesso perf : aluno.getMatricula()
						.getListaPerfilAcesso()) {
					if (perf.getId().equals(idPerfilAcesso)) {
						hasPerfil = true;
						break;
					}
				}
				if (hasPerfil)
					listaNova.add(aluno);
			}
		} else {
			listaNova.addAll(lista);
		}

		return listaNova;
	}

	@Override
	public EntAluno pesquisarPorMatricula(String matricula)
			throws ETrainingException {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT a FROM ");
		query.append(EntAluno.class.getName() + " AS a ");
		query.append(" WHERE ");
		query.append(" a.matricula.numeroMatricula = ? ");

		List<EntAluno> lista = getTemplate().pesquisarQuery(EntAluno.class,
				query.toString(), new Object[] { matricula });
		if (CollectionUtils.isNotEmpty(lista))
			return lista.get(0);
		return null;
	}
}
