package br.com.etraining.negocio.conversor.impl;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.DiaExercicioVO;
import br.com.etraining.modelo.entidades.EntDiaExercicio;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorDiaExercicio implements
		IConversorVOEntidade<EntDiaExercicio, DiaExercicioVO> {

	@Override
	public EntDiaExercicio fromVO(DiaExercicioVO vo) {
		EntDiaExercicio entidade = new EntDiaExercicio();
		entidade.setId(vo.getId());
		entidade.setDataRealizacao(vo.getData());
		return entidade;
	}

	@Override
	public DiaExercicioVO toVO(EntDiaExercicio entidade) {
		DiaExercicioVO vo = new DiaExercicioVO();
		vo.setId(entidade.getId());
		vo.setData(entidade.getDataRealizacao());
		return vo;
	}

}
