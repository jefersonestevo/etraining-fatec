package br.com.etraining.negocio.bo.impl.programatreinamento;

import javax.inject.Named;

import br.com.etraining.client.vo.impl.programatreinamento.ConsultaProgramaTreinamentoVO;
import br.com.etraining.client.vo.impl.programatreinamento.RespostaConsultaProgramaTreinamentoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.negocio.bo.interfaces.IBO;

@Named("ConsultaProgramaTreinamentoVO")
public class ConsultaProgramaTreinamentoBO
		implements
		IBO<ConsultaProgramaTreinamentoVO, RespostaConsultaProgramaTreinamentoVO> {

	@Override
	public RespostaConsultaProgramaTreinamentoVO executa(
			ConsultaProgramaTreinamentoVO request) throws ETrainingException {
		System.out.println("EXECUTOU: " + getClass().getName());
		// TODO Auto-generated method stub
		return null;
	}

}
