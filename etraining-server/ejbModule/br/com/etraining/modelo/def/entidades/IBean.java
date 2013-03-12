package br.com.etraining.modelo.def.entidades;

import java.io.Serializable;

public interface IBean extends Serializable, Cloneable {

	public Long getId();

	public void setId(Long id);

}
