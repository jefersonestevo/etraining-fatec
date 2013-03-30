package br.com.etraining.web.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.inject.Singleton;

import br.com.etraining.client.vo.impl.enums.DiaSemana;
import br.com.etraining.utils.data.DataUtils;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@Singleton
public class ViewDataUtils extends EtrainingManagedBean {

	private static final long serialVersionUID = 8951756497025704005L;

	public static final String PATTERN_ANO = "yyyy";
	public static final String PATTERN_DIA_MES_ANO = "dd/MM/yyyy";

	private List<SelectItem> listaDiasSemana;

	public List<SelectItem> getListaDiasSemana() {
		if (listaDiasSemana == null) {
			listaDiasSemana = new ArrayList<SelectItem>();

			for (DiaSemana dia : DiaSemana.values()) {
				SelectItem sel = new SelectItem(dia.getId(),
						getMessage(dia.getChave() + "_Sigla"));
				listaDiasSemana.add(sel);
			}
		}
		return listaDiasSemana;
	}

	public String getAnoAtual() {
		return DataUtils.getDataFormatada(new Date(), PATTERN_ANO);
	}

	public String getPatternData() {
		return PATTERN_DIA_MES_ANO;
	}
}
