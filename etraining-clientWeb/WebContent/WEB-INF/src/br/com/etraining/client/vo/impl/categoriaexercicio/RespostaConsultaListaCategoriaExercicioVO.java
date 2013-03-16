package br.com.etraining.client.vo.impl.categoriaexercicio;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaListaCategoriaExercicioVO implements IVO {

	private static final long serialVersionUID = -3702027477216179412L;

	private List<CategoriaExercicioVO> listaCategoriaExercicio = new ArrayList<CategoriaExercicioVO>();

	public List<CategoriaExercicioVO> getListaCategoriaExercicio() {
		return listaCategoriaExercicio;
	}

	public void setListaCategoriaExercicio(
			List<CategoriaExercicioVO> listaCategoriaExercicio) {
		this.listaCategoriaExercicio = listaCategoriaExercicio;
	}

}
