package br.com.etraining.negocio.bo.impl.programatreinamento;

import javax.inject.Named;

import org.apache.commons.lang.StringUtils;

import br.com.etraining.client.vo.impl.programatreinamento.ConsultaProgramaTreinamentoVO;
import br.com.etraining.client.vo.impl.programatreinamento.RespostaConsultaProgramaTreinamentoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;

@Named("ConsultaProgramaTreinamentoVO")
public class ConsultaProgramaTreinamentoBO
		extends
		AbstractBO<ConsultaProgramaTreinamentoVO, RespostaConsultaProgramaTreinamentoVO> {

	@Override
	protected RespostaConsultaProgramaTreinamentoVO executarRegrasEspecificas(
			ConsultaProgramaTreinamentoVO request) throws ETrainingException {
		System.out.println("EXECUTOU: " + getClass().getName());

		if (StringUtils.isNotBlank(getClass().getName())) {
			System.out.println("TESTE");
		}
		// TODO Auto-generated method stub
		return null;
	}

}
