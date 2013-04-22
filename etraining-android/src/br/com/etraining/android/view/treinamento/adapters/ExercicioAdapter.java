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
import br.com.etraining.android.view.treinamento.comparators.ComparadorExercicio;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;

public class ExercicioAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<ExercicioVO> listaItens = new ArrayList<ExercicioVO>();

	public ExercicioAdapter(Context context, List<ExercicioVO> listaItens) {
		if (listaItens == null) {
			listaItens = new ArrayList<ExercicioVO>();
		}
		this.listaItens = new ArrayList<ExercicioVO>(listaItens);
		inflater = LayoutInflater.from(context);

		Collections.sort(this.listaItens, new ComparadorExercicio());
	}

	@Override
	public int getCount() {
		return listaItens.size();
	}

	@Override
	public ExercicioVO getItem(int index) {
		return listaItens.get(index);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ExercicioVO item = getItem(position);
		convertView = inflater.inflate(R.layout.row_exercicio, null);

		TextView lblTituloExercicio = (TextView) convertView
				.findViewById(R.id.lbl_titulo_exercicio);
		lblTituloExercicio.setText(item.getTitulo());

		return convertView;
	}

}
