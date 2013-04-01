package br.com.etraining.client.vo.impl.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.etraining.client.dom.StatusProgramaTreinamento;
import br.com.etraining.client.vo.interfaces.IVO;

public class ProgramaTreinamentoVO implements IVO {

	private static final long serialVersionUID = -2116038355888203292L;

	private Long id;
	private Integer versao;
	private StatusProgramaTreinamento status;
	private Date dataVencimento;
	private Date dataCancelamento;
	private AlunoVO aluno;
	private List<ExercicioPropostoVO> listaExercicioProposto = new ArrayList<ExercicioPropostoVO>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public AlunoVO getAluno() {
		return aluno;
	}

	public void setAluno(AlunoVO aluno) {
		this.aluno = aluno;
	}

	public List<ExercicioPropostoVO> getListaExercicioProposto() {
		return listaExercicioProposto;
	}

	public void setListaExercicioProposto(
			List<ExercicioPropostoVO> listaExercicioProposto) {
		this.listaExercicioProposto = listaExercicioProposto;
	}

	public StatusProgramaTreinamento getStatus() {
		return status;
	}

	public void setStatus(StatusProgramaTreinamento status) {
		this.status = status;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

}
