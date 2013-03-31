package br.com.etraining.negocio.conversor.impl;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.DadosCorporaisVO;
import br.com.etraining.modelo.entidades.EntDadosCorporais;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorDadosCorporais implements
		IConversorVOEntidade<EntDadosCorporais, DadosCorporaisVO> {

	@Override
	public EntDadosCorporais fromVO(DadosCorporaisVO vo) {
		if (vo == null)
			return null;
		EntDadosCorporais entidade = new EntDadosCorporais();
		entidade.setId(vo.getId());
		entidade.setAltura(vo.getAltura());
		entidade.setBatimentosPorMinuto(vo.getBatimentosPorMinuto());
		entidade.setIndiceGorduraCorporal(vo.getIndiceGorduraCorporal());
		entidade.setPeso(vo.getPeso());
		entidade.setPressaoArterialMaxima(vo.getPressaoArterialMaxima());
		entidade.setPressaoArterialMinima(vo.getPressaoArterialMinima());
		entidade.setTempoEsteira(vo.getTempoEsteira());
		return entidade;
	}

	@Override
	public DadosCorporaisVO toVO(EntDadosCorporais entidade) {
		if (entidade == null)
			return null;

		DadosCorporaisVO vo = new DadosCorporaisVO();
		vo.setId(entidade.getId());
		vo.setAltura(entidade.getAltura());
		vo.setBatimentosPorMinuto(entidade.getBatimentosPorMinuto());
		vo.setIndiceGorduraCorporal(entidade.getIndiceGorduraCorporal());
		vo.setPeso(entidade.getPeso());
		vo.setPressaoArterialMaxima(entidade.getPressaoArterialMaxima());
		vo.setPressaoArterialMinima(entidade.getPressaoArterialMinima());
		vo.setTempoEsteira(entidade.getTempoEsteira());
		return vo;
	}

}
