package br.com.etraining.utils;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public class CDIUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getBean(final String clazzName, BeanManager bm) {
		Set<Bean<?>> beans = bm.getBeans(clazzName);
		Bean<?> bean = bm.resolve(beans);
		return (T) getBean(bean.getBeanClass(), bm);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(final Class<T> clazz, BeanManager bm) {
		Bean<T> bean = (Bean<T>) bm.getBeans(clazz).iterator().next();
		CreationalContext<T> ctx = bm.createCreationalContext(bean);
		return (T) bm.getReference(bean, clazz, ctx);
	}
}
