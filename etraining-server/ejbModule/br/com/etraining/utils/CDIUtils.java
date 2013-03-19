package br.com.etraining.utils;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CDIUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> void programmaticInjection(Class clazz, T injectionObject)
			throws NamingException {
		InitialContext initialContext = new InitialContext();
		Object lookup = initialContext.lookup("java:comp/BeanManager");
		BeanManager beanManager = (BeanManager) lookup;
		AnnotatedType annotatedType = beanManager.createAnnotatedType(clazz);
		InjectionTarget injectionTarget = beanManager
				.createInjectionTarget(annotatedType);
		CreationalContext creationalContext = beanManager
				.createCreationalContext(null);
		injectionTarget.inject(injectionObject, creationalContext);
		creationalContext.release();
	}

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
