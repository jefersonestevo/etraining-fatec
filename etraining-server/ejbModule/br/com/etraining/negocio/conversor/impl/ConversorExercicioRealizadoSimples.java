package br.com.etraining.negocio.conversor.impl;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.ExercicioRealizadoSimplesVO;
import br.com.etraining.modelo.entidades.EntExercicioRealizado;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorExercicioRealizadoSimples
		implements
		IConversorVOEntidade<EntExercicioRealizado, ExercicioRealizadoSimplesVO> {

	@Override
	public EntExercicioRealizado fromVO(ExercicioRealizadoSimplesVO vo) {
		throw new UnsupportedOperationException(
				"ExercicioRealizadoSimplesVO nao pode ser convertido para EntExercicioRealizado");
	}

	@Override
	public ExercicioRealizadoSimplesVO toVO(EntExercicioRealizado entidade) {
		ExercicioRealizadoSimplesVO vo = new ExercicioRealizadoSimplesVO();
		vo.setIdExercicioRealizado(entidade.getId());
		vo.setTituloExercicio(entidade.getExercicio().getTitulo());
		vo.setPontos(entidade.getPontos());
		return vo;
	}

}
