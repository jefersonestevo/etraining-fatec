package br.com.etraining.negocio.conversor.impl;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.modelo.entidades.EntCategoriaExercicio;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorExercicio implements
		IConversorVOEntidade<EntExercicio, ExercicioVO> {

	@Override
	public EntExercicio fromVO(ExercicioVO vo) {
		EntExercicio entidade = new EntExercicio();
		entidade.setId(vo.getId());
		entidade.setTitulo(vo.getTitulo());
		entidade.setPontosPorAtividade(vo.getPontosPorAtividade());
		entidade.setCategoriaExercicio(new EntCategoriaExercicio(vo
				.getIdCategoriaExercicio()));
		return entidade;
	}

	@Override
	public ExercicioVO toVO(EntExercicio entidade) {
		ExercicioVO vo = new ExercicioVO();
		vo.setId(entidade.getId());
		vo.setTitulo(entidade.getTitulo());
		vo.setPontosPorAtividade(entidade.getPontosPorAtividade());
		vo.setIdCategoriaExercicio(entidade.getCategoriaExercicio().getId());
		return vo;
	}

}
