package br.com.etraining.negocio.bo.impl.programatreinamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.dom.StatusProgramaTreinamento;
import br.com.etraining.client.vo.impl.entidades.ExercicioPropostoVO;
import br.com.etraining.client.vo.impl.entidades.ProgramaTreinamentoVO;
import br.com.etraining.client.vo.impl.programatreinamento.GerarProgramaTreinamentoVO;
import br.com.etraining.client.vo.impl.programatreinamento.RespostaConsultaProgramaTreinamentoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntExercicioProposto;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorExercicioProposto;
import br.com.etraining.negocio.conversor.impl.ConversorProgramaTreinamento;

@Named("GerarProgramaTreinamentoVO")
public class GerarProgramaTreinamentoBO
		extends
		AbstractBO<GerarProgramaTreinamentoVO, RespostaConsultaProgramaTreinamentoVO> {

	@Inject
	private IDaoProgramaTreinamento programaTreinamentoDao;

	@Inject
	private ConversorExercicioProposto conversorExercicioProposto;

	@Inject
	private ConversorProgramaTreinamento conversorProgramaTreinamento;

	@Override
	protected RespostaConsultaProgramaTreinamentoVO executarRegrasEspecificas(
			GerarProgramaTreinamentoVO request) throws ETrainingException {
		ProgramaTreinamentoVO programaTreinamentoAntigo = request
				.getProgramaTreinamentoAnterior();

		EntProgramaTreinamento progTreinamento = programaTreinamentoDao
				.pesquisar(programaTreinamentoAntigo.getId());

		EntProgramaTreinamento novoPrograma = new EntProgramaTreinamento();

		novoPrograma.setId(null);
		novoPrograma.setStatus(StatusProgramaTreinamento.AGUARDANDO_APROVACAO);
		novoPrograma.setVersao(progTreinamento.getVersao() + 1);
		novoPrograma.setAluno(progTreinamento.getAluno());
		novoPrograma.setDataCancelamento(null);
		novoPrograma.setDataAprovacao(null);
		novoPrograma.setDataVencimento(null);

		// Gerar o programa com os exercicios propostos fornecidos junto ao
		// request
		novoPrograma
				.setListaExercicioProposto(new ArrayList<EntExercicioProposto>());
		for (ExercicioPropostoVO exercVO : programaTreinamentoAntigo
				.getListaExercicioProposto()) {
			EntExercicioProposto exercProposto = conversorExercicioProposto
					.fromVO(exercVO);
			exercProposto.setId(null);
			exercProposto.setProgramaTreinamento(novoPrograma);
			novoPrograma.getListaExercicioProposto().add(exercProposto);
		}

		programaTreinamentoDao.inserir(novoPrograma);

		// Após gerar o novo programa de treinamento, cancelar todos os que
		// estiverem aguardando aprovacao
		List<EntProgramaTreinamento> listaPendentesAprovacao = programaTreinamentoDao
				.pesquisarListaProgramaPendenteAprovacaoPorAluno(novoPrograma
						.getAluno().getId());
		for (EntProgramaTreinamento prog : listaPendentesAprovacao) {
			if (prog.getId().equals(novoPrograma.getId()))
				continue;
			prog.setDataCancelamento(new Date());
			prog.setStatus(StatusProgramaTreinamento.CANCELADO);
			programaTreinamentoDao.alterar(prog);
		}

		RespostaConsultaProgramaTreinamentoVO resposta = new RespostaConsultaProgramaTreinamentoVO();
		resposta.setProgramaTreinamento(conversorProgramaTreinamento
				.toVO(novoPrograma));
		return resposta;
	}

	public IDaoProgramaTreinamento getProgramaTreinamentoDao() {
		return programaTreinamentoDao;
	}

	public void setProgramaTreinamentoDao(
			IDaoProgramaTreinamento programaTreinamentoDao) {
		this.programaTreinamentoDao = programaTreinamentoDao;
	}

	public ConversorExercicioProposto getConversorExercicioProposto() {
		return conversorExercicioProposto;
	}

	public void setConversorExercicioProposto(
			ConversorExercicioProposto conversorExercicioProposto) {
		this.conversorExercicioProposto = conversorExercicioProposto;
	}

	public ConversorProgramaTreinamento getConversorProgramaTreinamento() {
		return conversorProgramaTreinamento;
	}

	public void setConversorProgramaTreinamento(
			ConversorProgramaTreinamento conversorProgramaTreinamento) {
		this.conversorProgramaTreinamento = conversorProgramaTreinamento;
	}

}
