package br.com.etraining.negocio.bo;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.negocio.bo.interfaces.IBO;
import br.com.etraining.utils.CDIUtils;

@Named
public class BOResolver {

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
