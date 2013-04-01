package br.com.etraining.negocio.conversor.impl;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.ExercicioPropostoVO;
import br.com.etraining.client.vo.impl.entidades.ProgramaTreinamentoVO;
import br.com.etraining.modelo.entidades.EntExercicioProposto;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.conversor.IConversorVOEntidade;

@Named
public class ConversorProgramaTreinamento implements
		IConversorVOEntidade<EntProgramaTreinamento, ProgramaTreinamentoVO> {

	@Inject
	private ConversorAluno conversorAluno;

	@Inject
	private ConversorExercicioProposto conversorExercicioProposto;

	@Override
	public EntProgramaTreinamento fromVO(ProgramaTreinamentoVO vo) {
		if (vo == null)
			return null;

		EntProgramaTreinamento entidade = new EntProgramaTreinamento();
		entidade.setId(vo.getId());
		entidade.setDataVencimento(vo.getDataVencimento());
		entidade.setVersao(vo.getVersao());
		entidade.setStatus(vo.getStatus());
		entidade.setDataCancelamento(vo.getDataCancelamento());

		entidade.setListaExercicioProposto(new ArrayList<EntExercicioProposto>());
		for (ExercicioPropostoVO exerc : vo.getListaExercicioProposto()) {
			entidade.getListaExercicioProposto().add(
					conversorExercicioProposto.fromVO(exerc));
		}

		return entidade;
	}

	@Override
	public ProgramaTreinamentoVO toVO(EntProgramaTreinamento entidade) {
		if (entidade == null)
			return null;

		ProgramaTreinamentoVO vo = new ProgramaTreinamentoVO();
		vo.setId(entidade.getId());
		vo.setStatus(entidade.getStatus());
		vo.setDataVencimento(entidade.getDataVencimento());
		vo.setVersao(entidade.getVersao());
		vo.setDataCancelamento(entidade.getDataCancelamento());
		vo.setAluno(conversorAluno.toVO(entidade.getAluno()));

		vo.setListaExercicioProposto(new ArrayList<ExercicioPropostoVO>());
		for (EntExercicioProposto exerc : entidade.getListaExercicioProposto()) {
			vo.getListaExercicioProposto().add(
					conversorExercicioProposto.toVO(exerc));
			exerc.setProgramaTreinamento(entidade);
		}

		return vo;
	}

	public ConversorAluno getConversorAluno() {
		return conversorAluno;
	}

	public void setConversorAluno(ConversorAluno conversorAluno) {
		this.conversorAluno = conversorAluno;
	}

	public ConversorExercicioProposto getConversorExercicioProposto() {
		return conversorExercicioProposto;
	}

	public void setConversorExercicioProposto(
			ConversorExercicioProposto conversorExercicioProposto) {
		this.conversorExercicioProposto = conversorExercicioProposto;
	}

}
