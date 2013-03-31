package br.com.etraining.negocio.conversor.impl;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.AlunoSimplesVO;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorAlunoSimples implements
		IConversorVOEntidade<EntAluno, AlunoSimplesVO> {

	@Override
	public EntAluno fromVO(AlunoSimplesVO vo) {
		throw new UnsupportedOperationException(
				"AlunoSimplesVO não pode ser convertido para EntAluno.");
	}

	@Override
	public AlunoSimplesVO toVO(EntAluno entidade) {
		if (entidade == null)
			return null;
		AlunoSimplesVO vo = new AlunoSimplesVO();
		vo.setId(entidade.getId());
		vo.setNome(entidade.getNome());
		vo.setMatricula(entidade.getMatricula().getNumeroMatricula());
		vo.setCpf(entidade.getMatricula().getCpf());
		vo.setRg(entidade.getMatricula().getRg());
		return vo;
	}

}
