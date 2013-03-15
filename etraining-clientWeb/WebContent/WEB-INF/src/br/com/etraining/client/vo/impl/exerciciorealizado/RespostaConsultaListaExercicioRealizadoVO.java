package br.com.etraining.client.vo.impl.exerciciorealizado;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.ExercicioRealizadoSimplesVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaListaExercicioRealizadoVO implements IVO {

	private static final long serialVersionUID = 5139635122491062020L;

	private List<ExercicioRealizadoSimplesVO> listaExercicioRealizado = new ArrayList<ExercicioRealizadoSimplesVO>();

	public List<ExercicioRealizadoSimplesVO> getListaExercicioRealizado() {
		return listaExercicioRealizado;
	}

	public void setListaExercicioRealizado(
			List<ExercicioRealizadoSimplesVO> listaExercicioRealizado) {
		this.listaExercicioRealizado = listaExercicioRealizado;
	}

}
