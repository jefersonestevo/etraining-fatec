package br.com.etraining.client.vo.impl.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.etraining.client.vo.interfaces.IVO;

public class ProgramaTreinamentoVO implements IVO {

	private static final long serialVersionUID = -2116038355888203292L;

	private Long id;
	private Integer versao;
	private Boolean versaoAprovada;
	private Date dataVencimento;
	private Boolean cancelado;
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

	public Boolean getVersaoAprovada() {
		return versaoAprovada;
	}

	public void setVersaoAprovada(Boolean versaoAprovada) {
		this.versaoAprovada = versaoAprovada;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
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

}
