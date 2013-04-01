package br.com.etraining.utils.io;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class EtrainingBeanUtils {

	public static void copyProperties(Object dest, Object origem){
		try {
			BeanUtils.copyProperties(dest, origem);
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
}
