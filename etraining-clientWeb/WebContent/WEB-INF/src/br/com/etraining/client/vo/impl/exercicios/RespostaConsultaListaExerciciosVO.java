package br.com.etraining.client.vo.impl.exercicios;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaListaExerciciosVO implements IVO {

	private static final long serialVersionUID = 5751694905438042675L;

	private List<ExercicioVO> listaExercicios = new ArrayList<ExercicioVO>();

	public List<ExercicioVO> getListaExercicios() {
		return listaExercicios;
	}

	public void setListaExercicios(List<ExercicioVO> listaExercicios) {
		this.listaExercicios = listaExercicios;
	}

}
