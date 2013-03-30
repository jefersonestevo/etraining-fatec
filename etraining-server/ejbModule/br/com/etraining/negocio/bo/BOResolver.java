package br.com.etraining.negocio.bo;

import java.io.Serializable;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.negocio.bo.interfaces.IBO;
import br.com.etraining.utils.CDIUtils;

@Named
public class BOResolver implements Serializable {

	private static final long serialVersionUID = 3975917731289360922L;

	@Inject
	private BeanManager beanManager;

	public IBO<?, ?> getBOFor(Class<?> classeVO) {
		String nomeBO = classeVO.getSimpleName();
		return (IBO<?, ?>) CDIUtils.getBean(nomeBO, beanManager);
	}

	public BeanManager getBeanManager() {
		return beanManager;
	}

	public void setBeanManager(BeanManager beanManager) {
		this.beanManager = beanManager;
	}

}
