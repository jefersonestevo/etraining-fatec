package br.com.etraining.negocio.conversor.impl;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.modelo.entidades.EntCategoriaExercicio;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorCategoriaExercicio implements
		IConversorVOEntidade<EntCategoriaExercicio, CategoriaExercicioVO> {

	@Override
	public EntCategoriaExercicio fromVO(CategoriaExercicioVO vo) {
		EntCategoriaExercicio entidade = new EntCategoriaExercicio();
		entidade.setId(vo.getId());
		entidade.setTitulo(vo.getTitulo());
		return entidade;
	}

	@Override
	public CategoriaExercicioVO toVO(EntCategoriaExercicio entidade) {
		CategoriaExercicioVO vo = new CategoriaExercicioVO();
		vo.setId(entidade.getId());
		vo.setTitulo(entidade.getTitulo());
		return vo;
	}

}
