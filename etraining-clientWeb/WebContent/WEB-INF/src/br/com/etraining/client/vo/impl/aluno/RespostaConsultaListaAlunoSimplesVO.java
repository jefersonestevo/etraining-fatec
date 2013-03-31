package br.com.etraining.client.vo.impl.aluno;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.AlunoSimplesVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaListaAlunoSimplesVO implements IVO {

	private static final long serialVersionUID = -1609293490424612881L;

	private List<AlunoSimplesVO> listaAlunos = new ArrayList<AlunoSimplesVO>();

	public List<AlunoSimplesVO> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<AlunoSimplesVO> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

}
