package br.com.etraining.client.vo.impl.diasemana;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.DiaSemanaVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaDiasSemanaVO implements IVO {

	private static final long serialVersionUID = 4119632247644117555L;

	private List<DiaSemanaVO> listaDiasSemana = new ArrayList<DiaSemanaVO>();

	public List<DiaSemanaVO> getListaDiasSemana() {
		return listaDiasSemana;
	}

	public void setListaDiasSemana(List<DiaSemanaVO> listaDiasSemana) {
		this.listaDiasSemana = listaDiasSemana;
	}

}
