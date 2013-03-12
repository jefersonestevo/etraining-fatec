package br.com.etraining.modelo.def.interfaces;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.entidades.IBean;

public interface IDatabaseTemplate<E extends IBean> {

	public void inserir(E entidade) throws ETrainingException;

	public E alterar(E entidade) throws ETrainingException;

	public void remover(E entidade) throws ETrainingException;

	public E pesquisar(Class<E> entidade, Long id) throws ETrainingException;

	public List<E> pesquisarQuery(Class<E> classeEntidade, String query)
			throws ETrainingException;

	public List<E> pesquisarQuery(Class<E> classeEntidade, String queryStr,
			Object[] params) throws ETrainingException;

	public List<E> pesquisarLista(Class<E> entidade)
			throws ETrainingException;

	public EntityManager getEntityManager();

	public void setEntityManager(EntityManager entityManager);

}
