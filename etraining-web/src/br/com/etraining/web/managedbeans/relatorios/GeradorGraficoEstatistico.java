package br.com.etraining.web.managedbeans.relatorios;

import org.apache.commons.collections.CollectionUtils;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.etraining.client.vo.impl.entidades.PontoGraficoVO;
import br.com.etraining.client.vo.impl.relatorios.geral.RespostaConsultaEstatisticaVO;
import br.com.etraining.utils.data.DataUtils;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;
import br.com.etraining.web.utils.ViewDataUtils;

public class GeradorGraficoEstatistico extends EtrainingManagedBean {

	private static final long serialVersionUID = -7766242403233575998L;

	public CartesianChartModel gerarGraficoCartesiano(
			RespostaConsultaEstatisticaVO resposta) {

		CartesianChartModel grafico = new CartesianChartModel();

		LineChartSeries serieReal = new LineChartSeries();
		serieReal.setMarkerStyle("diamond");
		serieReal.setLabel(getLabel(true));

		LineChartSeries serieProposta = new LineChartSeries();
		serieProposta.setMarkerStyle("diamond");
		serieProposta.setLabel(getLabel(false));

		boolean possuiPropostos = CollectionUtils.isNotEmpty(resposta
				.getListaPontosPropostos());
		boolean possuiReal = CollectionUtils.isNotEmpty(resposta
				.getListaPontosReais());

		// Preenche os pontos reais
		if (possuiReal) {
			for (PontoGraficoVO ponto : resposta.getListaPontosReais()) {
				String data = DataUtils.getDataFormatada(ponto.getData(),
						ViewDataUtils.PATTERN_DIA_MES_ANO);
				serieReal.set(data, ponto.getPontos());

				if (!possuiPropostos) {
					serieProposta.set(data, 0l);
				}
			}
		}

		// Preenche os pontos propostos
		if (possuiPropostos) {
			for (PontoGraficoVO ponto : resposta.getListaPontosPropostos()) {
				String data = DataUtils.getDataFormatada(ponto.getData(),
						ViewDataUtils.PATTERN_DIA_MES_ANO);
				serieProposta.set(data, ponto.getPontos());

				if (!possuiReal) {
					serieReal.set(data, 0l);
				}
			}
		}

		grafico.addSeries(serieReal);
		grafico.addSeries(serieProposta);

		return grafico;
	}

	private String getLabel(boolean serieReal) {
		if (serieReal) {
			return getMessage("Serie_Real");
		} else {
			return getMessage("Serie_Proposta");
		}
	}
}
