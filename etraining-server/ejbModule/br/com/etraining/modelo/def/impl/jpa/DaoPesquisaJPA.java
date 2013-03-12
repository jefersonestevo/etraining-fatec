package br.com.etraining.modelo.def.impl.jpa;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.entidades.IBean;
import br.com.etraining.modelo.def.interfaces.IDaoPesquisa;
import br.com.etraining.modelo.def.interfaces.IDatabaseTemplate;

public abstract class DaoPesquisaJPA<E extends IBean> implements
		IDaoPesquisa<E> {

	@Inject
	private IDatabaseTemplate<E> template;

	protected abstract Class<E> getEntidadePersistente();

	protected abstract String getNomeEntidadee();

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public E pesquisar(Long id) throws ETrainingException {
		return getTemplate().pesquisar(getEntidadePersistente(), id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<E> pesquisarLista() throws ETrainingException {
		return getTemplate().pesquisarLista(getEntidadePersistente());
	}

	public IDatabaseTemplate<E> getTemplate() {
		return this.template;
	}

	public void setTemplate(IDatabaseTemplate<E> template) {
		this.template = template;
	}

}
