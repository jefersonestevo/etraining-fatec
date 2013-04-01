package br.com.etraining.negocio.conversor.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorAluno implements IConversorVOEntidade<EntAluno, AlunoVO> {

	@Inject
	private ConversorMatricula conversorMatricula;

	@Inject
	private ConversorDadosCorporais conversorDadosCorporais;

	@Override
	public EntAluno fromVO(AlunoVO vo) {
		if (vo == null)
			return null;
		EntAluno entidade = new EntAluno();
		entidade.setId(vo.getId());
		entidade.setNome(vo.getNome());
		entidade.setEmail(vo.getEmail());
		entidade.setDataNascimento(vo.getDataNascimento());
		entidade.setSexo(vo.getSexo());
		entidade.setTelefone(vo.getTelefone());
		entidade.setMatricula(conversorMatricula.fromVO(vo.getMatricula()));
		entidade.setDadosCorporais(conversorDadosCorporais.fromVO(vo
				.getDadosCorporais()));
		return entidade;
	}

	@Override
	public AlunoVO toVO(EntAluno entidade) {
		if (entidade == null)
			return null;
		AlunoVO vo = new AlunoVO();
		vo.setId(entidade.getId());
		vo.setNome(entidade.getNome());
		vo.setEmail(entidade.getEmail());
		vo.setDataNascimento(entidade.getDataNascimento());
		vo.setSexo(entidade.getSexo());
		vo.setTelefone(entidade.getTelefone());
		vo.setMatricula(conversorMatricula.toVO(entidade.getMatricula()));
		vo.setDadosCorporais(conversorDadosCorporais.toVO(entidade
				.getDadosCorporais()));
		return vo;
	}

	public ConversorMatricula getConversorMatricula() {
		return conversorMatricula;
	}

	public void setConversorMatricula(ConversorMatricula conversorMatricula) {
		this.conversorMatricula = conversorMatricula;
	}

	public ConversorDadosCorporais getConversorDadosCorporais() {
		return conversorDadosCorporais;
	}

	public void setConversorDadosCorporais(
			ConversorDadosCorporais conversorDadosCorporais) {
		this.conversorDadosCorporais = conversorDadosCorporais;
	}

}
