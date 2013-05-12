package br.com.etraining.web.utils;

import java.util.Calendar;
import java.util.Date;

import br.com.etraining.web.managedbeans.EtrainingManagedBean;

public class ValidationBean {

	public static boolean isPeriodoDataValido(Date dataInicial, Date dataFinal,
			EtrainingManagedBean bean) {
		boolean hasError = false;
		Date anoAtual = getAnoAtual();

		if (anoAtual.after(dataInicial)) {
			bean.addErrorMessage(bean.getMessage("Data_Inicial_Anterior_2013"));
			hasError = true;
		}

		if (anoAtual.after(dataFinal)) {
			bean.addErrorMessage(bean.getMessage("Data_Final_Anterior_2013"));
			hasError = true;
		}

		if (dataFinal.before(dataInicial)) {
			bean.addErrorMessage(bean
					.getMessage("Data_Final_Anterior_Data_Inicial"));
			hasError = true;
		}

		return hasError;
	}

	public static boolean isDataValido(Date data, EtrainingManagedBean bean) {
		boolean hasError = false;
		Date anoAtual = getAnoAtual();

		if (anoAtual.before(data)) {
			bean.addErrorMessage(bean.getMessage("Data_Anterior_2013"));
			hasError = true;
		}

		return hasError;
	}

	private static Date getAnoAtual() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 31);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

}
