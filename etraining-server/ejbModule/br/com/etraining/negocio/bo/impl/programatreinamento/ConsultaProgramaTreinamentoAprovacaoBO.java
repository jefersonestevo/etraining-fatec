package br.com.etraining.negocio.bo.impl.programatreinamento;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.programatreinamento.ConsultaProgramaTreinamentoAprovacaoVO;
import br.com.etraining.client.vo.impl.programatreinamento.RespostaConsultaProgramaTreinamentoVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingBusinessException;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorProgramaTreinamento;

@Named("ConsultaProgramaTreinamentoAprovacaoVO")
public class ConsultaProgramaTreinamentoAprovacaoBO
		extends
		AbstractBO<ConsultaProgramaTreinamentoAprovacaoVO, RespostaConsultaProgramaTreinamentoVO> {

	@Inject
	private IDaoProgramaTreinamento daoProgramaTreinamento;

	@Inject
	private ConversorProgramaTreinamento conversorProgramaTreinamento;

	@Override
	protected RespostaConsultaProgramaTreinamentoVO executarRegrasEspecificas(
			ConsultaProgramaTreinamentoAprovacaoVO request)
			throws ETrainingException {
		RespostaConsultaProgramaTreinamentoVO resposta = new RespostaConsultaProgramaTreinamentoVO();

		EntProgramaTreinamento programaTreinamento = daoProgramaTreinamento
				.pesquisarPendenteAprovacaoPorIdAluno(request.getIdAluno());

		if (programaTreinamento == null || programaTreinamento.getId() == null) {
			throw new ETrainingBusinessException(
					CodigoExcecao.PROGRAMA_TREINAMENTO_APROVACAO_INEXISTENTE);
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
