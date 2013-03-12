package br.com.etraining.modelo.def.interfaces;

import java.util.List;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.entidades.IBean;

public interface IDaoCRUD<E extends IBean> extends IDao<E> {

	public void inserir(E entidade) throws ETrainingException;

	public E alterar(E entidade) throws ETrainingException;

	public void remover(E entidade) throws ETrainingException;

	public E pesquisar(Long id) throws ETrainingException;

	public List<E> pesquisarLista() throws ETrainingException;

}
