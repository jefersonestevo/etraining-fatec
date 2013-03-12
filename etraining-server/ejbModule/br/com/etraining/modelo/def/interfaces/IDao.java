package br.com.etraining.modelo.def.interfaces;

import br.com.etraining.modelo.def.entidades.IBean;

public interface IDao<E extends IBean> {

	public IDatabaseTemplate<E> getTemplate();

	public void setTemplate(IDatabaseTemplate<E> template);

}
