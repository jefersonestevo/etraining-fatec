package br.com.etraining.client.vo.impl.listaexercicios;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaListaExerciciosAlunoVO implements IVO {

	private static final long serialVersionUID = -5699497612211560381L;

	private Long idProgramaTreinamento;
	private List<ExercicioVO> listaExerciciosSugeridos = new ArrayList<ExercicioVO>();
	private List<ExercicioVO> listaExercicios = new ArrayList<ExercicioVO>();

	public List<ExercicioVO> getListaExerciciosSugeridos() {
		return listaExerciciosSugeridos;
	}

	public void setListaExerciciosSugeridos(
			List<ExercicioVO> listaExerciciosSugeridos) {
		this.listaExerciciosSugeridos = listaExerciciosSugeridos;
	}

	public List<ExercicioVO> getListaExercicios() {
		return listaExercicios;
	}

	public void setListaExercicios(List<ExercicioVO> listaExercicios) {
		this.listaExercicios = listaExercicios;
	}

	public Long getIdProgramaTreinamento() {
		return idProgramaTreinamento;
	}

	public void setIdProgramaTreinamento(Long idProgramaTreinamento) {
		this.idProgramaTreinamento = idProgramaTreinamento;
	}

}
