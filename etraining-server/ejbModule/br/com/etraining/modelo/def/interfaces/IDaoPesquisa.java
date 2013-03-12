package br.com.etraining.modelo.def.interfaces;

import java.util.List;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.entidades.IBean;

public interface IDaoPesquisa<E extends IBean> extends IDao<E> {

	public E pesquisar(Long id) throws ETrainingException;

	public List<E> pesquisarLista() throws ETrainingException;

}
