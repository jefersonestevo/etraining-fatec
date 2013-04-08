package br.com.etraining.negocio.bo.transformer;

import java.util.Comparator;

import br.com.etraining.client.vo.impl.entidades.PontoGraficoVO;

public class ComparadorPontoGrafico implements Comparator<PontoGraficoVO> {

	@Override
	public int compare(PontoGraficoVO p1, PontoGraficoVO p2) {
		return p1.getData().compareTo(p2.getData());
	}

}
