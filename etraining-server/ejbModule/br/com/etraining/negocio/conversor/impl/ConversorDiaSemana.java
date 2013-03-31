package br.com.etraining.negocio.conversor.impl;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.DiaSemanaVO;
import br.com.etraining.modelo.entidades.EntDiaSemana;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorDiaSemana implements
		IConversorVOEntidade<EntDiaSemana, DiaSemanaVO> {

	@Override
	public EntDiaSemana fromVO(DiaSemanaVO vo) {
		if (vo == null)
			return null;

		EntDiaSemana entidade = new EntDiaSemana();
		entidade.setId(vo.getId());
		entidade.setNome(vo.getNome());
		return entidade;
	}

	@Override
	public DiaSemanaVO toVO(EntDiaSemana entidade) {
		if (entidade == null)
			return null;

		DiaSemanaVO vo = new DiaSemanaVO();
		vo.setId(entidade.getId());
		vo.setNome(entidade.getNome());
		return vo;
	}

}
