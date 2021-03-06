package br.com.etraining.negocio.conversor.impl;

import java.util.ArrayList;

import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.impl.entidades.MatriculaVO;
import br.com.etraining.modelo.entidades.EntDiaSemana;
import br.com.etraining.modelo.entidades.EntMatricula;
import br.com.etraining.modelo.entidades.EntPerfilAcesso;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorMatricula implements
		IConversorVOEntidade<EntMatricula, MatriculaVO> {

	@Override
	public EntMatricula fromVO(MatriculaVO vo) {
		EntMatricula entidade = new EntMatricula();
		entidade.setId(vo.getId());
		entidade.setNumeroMatricula(vo.getNumeroMatricula());
		entidade.setCpf(vo.getCpf());
		entidade.setRg(vo.getRg());

		entidade.setListaDiasTreinamento(new ArrayList<EntDiaSemana>());
		if (CollectionUtils.isNotEmpty(vo.getListaDiasSemana())) {
			for (String diaSemana : vo.getListaDiasSemana()) {
				EntDiaSemana dia = new EntDiaSemana();
				dia.setId(new Long(diaSemana));
				entidade.getListaDiasTreinamento().add(dia);
			}
		}

		entidade.setListaPerfilAcesso(new ArrayList<EntPerfilAcesso>());
		if (CollectionUtils.isNotEmpty(vo.getListaPerfilAcesso())) {
			for (String idPerfil : vo.getListaPerfilAcesso()) {
				EntPerfilAcesso perfil = new EntPerfilAcesso();
				perfil.setId(Long.valueOf(idPerfil));
				entidade.getListaPerfilAcesso().add(perfil);
			}
		}

		return entidade;
	}

	@Override
	public MatriculaVO toVO(EntMatricula entidade) {
		if (entidade == null)
			return null;

		MatriculaVO vo = new MatriculaVO();
		vo.setId(entidade.getId());
		vo.setNumeroMatricula(entidade.getNumeroMatricula());
		vo.setCpf(entidade.getCpf());
		vo.setRg(entidade.getRg());

		vo.setListaDiasSemana(new ArrayList<String>());
		if (CollectionUtils.isNotEmpty(entidade.getListaDiasTreinamento()))
			for (EntDiaSemana diaSemana : entidade.getListaDiasTreinamento()) {
				vo.getListaDiasSemana().add(diaSemana.getId().toString());
			}

		vo.setListaPerfilAcesso(new ArrayList<String>());
		if (CollectionUtils.isNotEmpty(entidade.getListaPerfilAcesso())) {
			for (EntPerfilAcesso perfil : entidade.getListaPerfilAcesso()) {
				vo.getListaPerfilAcesso().add(perfil.getId().toString());
			}
		}

		return vo;
	}

}
