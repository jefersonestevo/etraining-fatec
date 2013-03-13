package br.com.etraining.modelo.def.impl.jpa;

import br.com.etraining.modelo.def.entidades.IBean;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

@SuppressWarnings("serial")
public abstract class BeanJPA implements IBean {

	public BeanJPA() {

	}

	public BeanJPA(Long id) {
		this();
		setId(id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntProgramaTreinamento other = (EntProgramaTreinamento) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
