package br.com.etraining.modelo.def.impl.jpa;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.entidades.IBean;
import br.com.etraining.modelo.def.interfaces.IDaoCRUD;
import br.com.etraining.modelo.def.interfaces.IDatabaseTemplate;

public abstract class DaoCRUDJPA<E extends IBean> implements IDaoCRUD<E> {

	@Inject
	private IDatabaseTemplate<E> template;

	protected abstract Class<E> getEntidadePersistente();

	protected abstract String getNomeEntidadee();

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void inserir(E entidade) throws ETrainingException {
		getTemplate().inserir(entidade);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public E alterar(E entidade) throws ETrainingException {
		return getTemplate().alterar(entidade);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(E entidade) throws ETrainingException {
		getTemplate().remover(entidade);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public E pesquisar(Long id) throws ETrainingException {
		return getTemplate().pesquisar(getEntidadePersistente(), id);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<E> pesquisarLista() throws ETrainingException {
		return getTemplate().pesquisarLista(getEntidadePersistente());
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return getTemplate().getEntityManager().getCriteriaBuilder();
	}

	protected TypedQuery<E> createQuery(CriteriaQuery<E> cq) {
		return getTemplate().getEntityManager().createQuery(cq);
	}

	public IDatabaseTemplate<E> getTemplate() {
		return this.template;
	}

	public void setTemplate(IDatabaseTemplate<E> template) {
		this.template = template;
	}

}
