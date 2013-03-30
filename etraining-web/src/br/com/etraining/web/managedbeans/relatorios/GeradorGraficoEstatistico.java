package br.com.etraining.web.managedbeans.relatorios;

import java.util.List;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.entidades.PontoGraficoVO;
import br.com.etraining.utils.data.DataUtils;
import br.com.etraining.web.utils.ViewDataUtils;

public class GeradorGraficoEstatistico {

	public static CartesianChartModel gerarGraficoCartesiano(
			List<PontoGraficoVO> listaPontos, ExercicioVO exercicio) {

		CartesianChartModel grafico = new CartesianChartModel();

		LineChartSeries serie = new LineChartSeries();
		if (exercicio != null)
			serie.setLabel(exercicio.getTitulo());

		serie.setMarkerStyle("diamond");
		for (PontoGraficoVO ponto : listaPontos) {
			String data = DataUtils.getDataFormatada(ponto.getData(),
					ViewDataUtils.PATTERN_DIA_MES_ANO);
			serie.set(data, ponto.getPontos());
		}
		grafico.addSeries(serie);

		return grafico;
	}

}
