package br.com.etraining.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

public class StringFakeUtils {

	private static Map<String, String> mapReplace = new HashMap<String, String>();

	static {
		mapReplace.put("‡", "ç");
		mapReplace.put("Æ", "ã");
		mapReplace.put("¡", "í");
		mapReplace.put("ƒ", "â");
		mapReplace.put("ˆ", "ê");
		mapReplace.put("£", "ú");
		mapReplace.put("‚", "é");
	}

	public static String ajustarAcentos(String valor) {
		String valorNovo = valor;
		if (valorNovo == null)
			return null;

		for (Entry<String, String> entry : mapReplace.entrySet()) {
			valorNovo = valorNovo.replaceAll(entry.getKey(), entry.getValue());
		}
		return valorNovo;
	}

	public static void replaceCaracteres(Object objeto, String propriedade) {
		String valor;
		if (objeto == null)
			return;

		try {
			valor = (String) PropertyUtils.getProperty(objeto, propriedade);
			valor = ajustarAcentos(valor);
			PropertyUtils.setProperty(objeto, propriedade, valor);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

}
