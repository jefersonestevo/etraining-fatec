package br.com.etraining.client.vo.impl.relatorios.geral;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.entidades.PontoGraficoVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaEstatisticaVO implements IVO {

	private static final long serialVersionUID = -175020763333358951L;

	private Integer tipoGrafico;
	private ExercicioVO exercicio;
	private AlunoVO aluno;
	private List<PontoGraficoVO> listaPontos = new ArrayList<PontoGraficoVO>();

	public Integer getTipoGrafico() {
		return tipoGrafico;
	}

	public void setTipoGrafico(Integer tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
	}

	public ExercicioVO getExercicio() {
		return exercicio;
	}

	public void setExercicio(ExercicioVO exercicio) {
		this.exercicio = exercicio;
	}

	public List<PontoGraficoVO> getListaPontos() {
		return listaPontos;
	}

	public void setListaPontos(List<PontoGraficoVO> listaPontos) {
		this.listaPontos = listaPontos;
	}

	public AlunoVO getAluno() {
		return aluno;
	}

	public void setAluno(AlunoVO aluno) {
		this.aluno = aluno;
	}

}
