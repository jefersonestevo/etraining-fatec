package br.com.etraining.negocio.bo.impl.programatreinamento;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.dom.StatusProgramaTreinamento;
import br.com.etraining.client.vo.impl.programatreinamento.AprovarProgramaTreinamentoVO;
import br.com.etraining.client.vo.impl.programatreinamento.RespostaConsultaProgramaTreinamentoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorProgramaTreinamento;

@Named("AprovarProgramaTreinamentoVO")
public class AprovarProgramaTreinamentoBO
		extends
		AbstractBO<AprovarProgramaTreinamentoVO, RespostaConsultaProgramaTreinamentoVO> {

	@Inject
	private IDaoProgramaTreinamento programaTreinamentoDao;

	@Inject
	private ConversorProgramaTreinamento conversorProgramaTreinamento;

	@Override
	protected RespostaConsultaProgramaTreinamentoVO executarRegrasEspecificas(
			AprovarProgramaTreinamentoVO request) throws ETrainingException {

		EntProgramaTreinamento progTreinamento = programaTreinamentoDao
				.pesquisar(request.getProgramaTreinamento().getId());

		progTreinamento.setDataAprovacao(new Date());
		progTreinamento.setStatus(StatusProgramaTreinamento.APROVADO);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 7);
		progTreinamento.setDataVencimento(cal.getTime());

		progTreinamento = programaTreinamentoDao.alterar(progTreinamento);

		// Após aprovar o programa de treinamento, cancelar todos os que
		// estiverem aprovados
		List<EntProgramaTreinamento> listaPendentesAprovacao = programaTreinamentoDao
				.pesquisarListaProgramaAprovadoPorAluno(progTreinamento
						.getAluno().getId());
		for (EntProgramaTreinamento prog : listaPendentesAprovacao) {
			if (prog.getId().equals(progTreinamento.getId()))
				continue;
			prog.setDataCancelamento(new Date());
			prog.setStatus(StatusProgramaTreinamento.CANCELADO);
			programaTreinamentoDao.alterar(prog);
		}

		RespostaConsultaProgramaTreinamentoVO resposta = new RespostaConsultaProgramaTreinamentoVO();
		resposta.setProgramaTreinamento(conversorProgramaTreinamento
				.toVO(progTreinamento));
		return resposta;
	}

	public IDaoProgramaTreinamento getProgramaTreinamentoDao() {
		return programaTreinamentoDao;
	}

	public void setProgramaTreinamentoDao(
			IDaoProgramaTreinamento programaTreinamentoDao) {
		this.programaTreinamentoDao = programaTreinamentoDao;
	}

	public ConversorProgramaTreinamento getConversorProgramaTreinamento() {
		return conversorProgramaTreinamento;
	}

	public void setConversorProgramaTreinamento(
			ConversorProgramaTreinamento conversorProgramaTreinamento) {
		this.conversorProgramaTreinamento = conversorProgramaTreinamento;
	}

}
