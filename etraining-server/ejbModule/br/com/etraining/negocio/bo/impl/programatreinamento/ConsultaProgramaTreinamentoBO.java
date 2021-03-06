package br.com.etraining.negocio.bo.impl.programatreinamento;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.impl.programatreinamento.ConsultaProgramaTreinamentoVO;
import br.com.etraining.client.vo.impl.programatreinamento.RespostaConsultaProgramaTreinamentoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntExercicioProposto;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorProgramaTreinamento;
import br.com.etraining.utils.StringFakeUtils;

@Named("ConsultaProgramaTreinamentoVO")
public class ConsultaProgramaTreinamentoBO
		extends
		AbstractBO<ConsultaProgramaTreinamentoVO, RespostaConsultaProgramaTreinamentoVO> {

	@Inject
	private IDaoProgramaTreinamento daoProgramaTreinamento;

	@Inject
	private ConversorProgramaTreinamento conversorProgramaTreinamento;

	@Override
	protected RespostaConsultaProgramaTreinamentoVO executarRegrasEspecificas(
			ConsultaProgramaTreinamentoVO request) throws ETrainingException {
		RespostaConsultaProgramaTreinamentoVO resposta = new RespostaConsultaProgramaTreinamentoVO();

		EntProgramaTreinamento programaTreinamento = daoProgramaTreinamento
				.pesquisarUltimoPorIdAluno(request.getIdAluno());

		if (CollectionUtils.isNotEmpty(programaTreinamento
				.getListaExercicioProposto()))
			for (EntExercicioProposto exercProposto : programaTreinamento
					.getListaExercicioProposto()) {
				StringFakeUtils.replaceCaracteres(exercProposto.getDiaSemana(),
						"nome");
			}

		resposta.setProgramaTreinamento(conversorProgramaTreinamento
				.toVO(programaTreinamento));

		return resposta;
	}

	public IDaoProgramaTreinamento getDaoProgramaTreinamento() {
		return daoProgramaTreinamento;
	}

	public void setDaoProgramaTreinamento(
			IDaoProgramaTreinamento daoProgramaTreinamento) {
		this.daoProgramaTreinamento = daoProgramaTreinamento;
	}

	public ConversorProgramaTreinamento getConversorProgramaTreinamento() {
		return conversorProgramaTreinamento;
	}

	public void setConversorProgramaTreinamento(
			ConversorProgramaTreinamento conversorProgramaTreinamento) {
		this.conversorProgramaTreinamento = conversorProgramaTreinamento;
	}

}
