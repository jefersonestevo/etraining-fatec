package br.com.etraining.negocio.conversor.impl;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.AtividadeVO;
import br.com.etraining.modelo.entidades.EntAtividade;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorAtividade implements
		IConversorVOEntidade<EntAtividade, AtividadeVO> {

	@Override
	public EntAtividade fromVO(AtividadeVO vo) {
		if (vo == null)
			return null;
		EntAtividade entidade = new EntAtividade();
		entidade.setId(vo.getId());
		entidade.setTitulo(vo.getTitulo());
		return entidade;
	}

	@Override
	public AtividadeVO toVO(EntAtividade entidade) {
		if (entidade == null)
			return null;
		AtividadeVO vo = new AtividadeVO();
		vo.setId(entidade.getId());
		vo.setTitulo(entidade.getTitulo());
		return vo;
	}

}
