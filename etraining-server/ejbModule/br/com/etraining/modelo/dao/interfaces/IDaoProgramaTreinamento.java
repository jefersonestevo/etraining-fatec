package br.com.etraining.modelo.dao.interfaces;

import java.util.Date;
import java.util.List;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.interfaces.IDaoCRUD;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

public interface IDaoProgramaTreinamento extends
		IDaoCRUD<EntProgramaTreinamento> {

	public EntProgramaTreinamento pesquisarAtualAprovadoPorIdAluno(Long idAluno)
			throws ETrainingException;

	public EntProgramaTreinamento pesquisarUltimoPorIdAluno(Long idAluno)
			throws ETrainingException;

	public EntProgramaTreinamento pesquisarPendenteAprovacaoPorIdAluno(
			Long idAluno) throws ETrainingException;

	public List<EntProgramaTreinamento> pesquisarListaProgramaPendenteAprovacaoPorAluno(
			Long idAluno) throws ETrainingException;

	public List<EntProgramaTreinamento> pesquisarPendentesAtualizacao()
			throws ETrainingException;

	public List<EntProgramaTreinamento> pesquisarVersoesPosteriores(
			Long idAluno, Integer versaoAtual) throws ETrainingException;

	public List<EntProgramaTreinamento> pesquisarListaAprovadosCancelados(Date dataInicial,
			Date dataFinal, Long idExercicio, Long idAluno)
			throws ETrainingException;

	public List<EntProgramaTreinamento> pesquisarListaProgramaAprovadoPorAluno(
			Long idAluno) throws ETrainingException;
}
