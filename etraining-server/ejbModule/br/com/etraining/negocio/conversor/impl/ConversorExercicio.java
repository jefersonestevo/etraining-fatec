package br.com.etraining.negocio.conversor.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.modelo.entidades.EntCategoriaExercicio;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorExercicio implements
		IConversorVOEntidade<EntExercicio, ExercicioVO> {

	@Inject
	private ConversorAtividade conversorAtividade;

	@Override
	public EntExercicio fromVO(ExercicioVO vo) {
		if (vo == null)
			return null;
		EntExercicio entidade = new EntExercicio();
		entidade.setId(vo.getId());
		entidade.setTitulo(vo.getTitulo());
		entidade.setPontosPorAtividade(vo.getPontosPorAtividade());
		entidade.setCategoriaExercicio(new EntCategoriaExercicio(vo
				.getIdCategoriaExercicio()));
		entidade.setAtividade(conversorAtividade.fromVO(vo.getAtividade()));
		return entidade;
	}

	@Override
	public ExercicioVO toVO(EntExercicio entidade) {
		if (entidade == null)
			return null;
		ExercicioVO vo = new ExercicioVO();
		vo.setId(entidade.getId());
		vo.setTitulo(entidade.getTitulo());
		vo.setPontosPorAtividade(entidade.getPontosPorAtividade());
		vo.setIdCategoriaExercicio(entidade.getCategoriaExercicio().getId());
		vo.setAtividade(conversorAtividade.toVO(entidade.getAtividade()));
		return vo;
	}

	public ConversorAtividade getConversorAtividade() {
		return conversorAtividade;
	}

	public void setConversorAtividade(ConversorAtividade conversorAtividade) {
		this.conversorAtividade = conversorAtividade;
	}

}
