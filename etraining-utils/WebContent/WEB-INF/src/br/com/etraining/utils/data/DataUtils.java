package br.com.etraining.utils.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DataUtils {

	public static Date getDataInicialDia(Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getDataFinalDia(Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static String getDataFormatada(Date data, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(data);
	}

	public static Date getProximaData(Date data, int proximoDiaSemana) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		while (!(cal.get(Calendar.DAY_OF_WEEK) == proximoDiaSemana)) {
			cal.add(Calendar.DATE, 1);
		}
		return DateUtils.truncate(cal.getTime(), Calendar.DATE);
	}

}
