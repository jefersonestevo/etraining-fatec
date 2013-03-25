package br.com.etraining.android.view.treinamento.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.etraining.android.R;
import br.com.etraining.android.view.treinamento.comparators.ComparadorExercicioRealizadoSimples;
import br.com.etraining.client.vo.impl.entidades.ExercicioRealizadoSimplesVO;

public class TreinamentoAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<ExercicioRealizadoSimplesVO> listaItens = new ArrayList<ExercicioRealizadoSimplesVO>();

	public TreinamentoAdapter(Context context,
			List<ExercicioRealizadoSimplesVO> listaItens) {
		if (listaItens != null) {
			this.listaItens = new ArrayList<ExercicioRealizadoSimplesVO>(
					listaItens);
		}
		inflater = LayoutInflater.from(context);

		Collections.sort(this.listaItens,
				new ComparadorExercicioRealizadoSimples());
	}

	@Override
	public int getCount() {
		return listaItens.size();
	}

	@Override
	public ExercicioRealizadoSimplesVO getItem(int index) {
		return listaItens.get(index);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ExercicioRealizadoSimplesVO item = getItem(position);
		convertView = inflater.inflate(R.layout.row_exercicio_realizado, null);

		TextView lblTituloExercicio = (TextView) convertView
				.findViewById(R.id.lbl_titulo_exercicio);
		lblTituloExercicio.setText(item.getTituloExercicio());

		if (item.getPontos() != null) {
			TextView lblPontosExercicio = (TextView) convertView
					.findViewById(R.id.lbl_pontos_exercicio_realizado);
			lblPontosExercicio.setText(item.getPontos().toString());
		}

		return convertView;
	}

}
