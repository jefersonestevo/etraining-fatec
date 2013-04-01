package br.com.etraining.negocio.conversor.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.ExercicioPropostoVO;
import br.com.etraining.modelo.entidades.EntExercicioProposto;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorExercicioProposto implements
		IConversorVOEntidade<EntExercicioProposto, ExercicioPropostoVO> {

	@Inject
	private ConversorExercicio conversorExercicio;

	@Inject
	private ConversorDiaSemana conversorDiaSemana;

	@Override
	public EntExercicioProposto fromVO(ExercicioPropostoVO vo) {
		if (vo == null)
			return null;

		EntExercicioProposto entidade = new EntExercicioProposto();
		entidade.setId(vo.getId());
		entidade.setExercicio(conversorExercicio.fromVO(vo.getExercicio()));
		entidade.setDiaSemana(conversorDiaSemana.fromVO(vo.getDiaSemana()));
		entidade.setQuantidadeExercicioSugerida(vo
				.getQuantidadeExercicioSugerida());
		return entidade;
	}

	@Override
	public ExercicioPropostoVO toVO(EntExercicioProposto entidade) {
		if (entidade == null)
			return null;
		ExercicioPropostoVO vo = new ExercicioPropostoVO();
		vo.setId(entidade.getId());
		vo.setExercicio(conversorExercicio.toVO(entidade.getExercicio()));
		vo.setDiaSemana(conversorDiaSemana.toVO(entidade.getDiaSemana()));
		vo.setQuantidadeExercicioSugerida(entidade
				.getQuantidadeExercicioSugerida());
		return vo;
	}

	public ConversorExercicio getConversorExercicio() {
		return conversorExercicio;
	}

	public void setConversorExercicio(ConversorExercicio conversorExercicio) {
		this.conversorExercicio = conversorExercicio;
	}

	public ConversorDiaSemana getConversorDiaSemana() {
		return conversorDiaSemana;
	}

	public void setConversorDiaSemana(ConversorDiaSemana conversorDiaSemana) {
		this.conversorDiaSemana = conversorDiaSemana;
	}

}
