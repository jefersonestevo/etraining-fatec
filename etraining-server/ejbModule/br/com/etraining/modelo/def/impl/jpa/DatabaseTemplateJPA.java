package br.com.etraining.modelo.def.impl.jpa;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.entidades.IBean;
import br.com.etraining.modelo.def.interfaces.IDatabaseTemplate;
import br.com.etraining.utils.TradutorExcecaoUtils;

@Named(value = "template")
public class DatabaseTemplateJPA<E extends IBean> implements
		IDatabaseTemplate<E> {

	@PersistenceContext
	private EntityManager entityManager;

	public void inserir(E entidade) throws ETrainingException {
		try {
			getEntityManager().persist(entidade);
			getEntityManager().flush();
		} catch (RuntimeException e) {
			throw TradutorExcecaoUtils.getExcecaoTraduzida(e);
		}
	}

	public E alterar(E entidade) throws ETrainingException {
		try {
			entidade = getEntityManager().merge(entidade);
			getEntityManager().flush();
			return entidade;
		} catch (RuntimeException e) {
			throw TradutorExcecaoUtils.getExcecaoTraduzida(e);
		}
	}

	public void remover(E entidade) throws ETrainingException {
		try {
			E entidadeRemocao = getEntityManager().merge(entidade);
			getEntityManager().remove(entidadeRemocao);
			getEntityManager().flush();
		} catch (RuntimeException e) {
			throw TradutorExcecaoUtils.getExcecaoTraduzida(e);
		}
	}

	public E pesquisar(Class<E> entidade, Long id) throws ETrainingException {
		try {
			return getEntityManager().find(entidade, id);
		} catch (RuntimeException e) {
			throw TradutorExcecaoUtils.getExcecaoTraduzida(e);
		}
	}

	public List<E> pesquisarQuery(Class<E> classeEntidade, String query)
			throws ETrainingException {
		try {
			return getEntityManager().createQuery(query, classeEntidade)
					.getResultList();
		} catch (RuntimeException e) {
			throw TradutorExcecaoUtils.getExcecaoTraduzida(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<E> pesquisarQuery(Class<E> classeEntidade, String queryStr,
			Object[] params) throws ETrainingException {
		try {
			Query query = getEntityManager().createQuery(queryStr,
					classeEntidade);
			if (params != null)
				for (int i = 0; i < params.length; i++) {
					query.setParameter((i + 1), params[i]);
				}
			return query.getResultList();
		} catch (RuntimeException e) {
			System.out.println("ERRORORORORO");
			e.printStackTrace();
			throw TradutorExcecaoUtils.getExcecaoTraduzida(e);
		}
	}

	public List<E> pesquisarLista(Class<E> entidade)
			throws ETrainingException {
		try {
			CriteriaQuery<E> crit = this.createCriteria(entidade);
			TypedQuery<E> q = entityManager.createQuery(crit);
			return q.getResultList();

		} catch (RuntimeException e) {
			throw TradutorExcecaoUtils.getExcecaoTraduzida(e);
		}
	}

	private CriteriaQuery<E> createCriteria(Class<E> classEntidade) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(classEntidade);
		Root<E> root = cq.from(classEntidade);
		cq.select(root);
		return cq;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
