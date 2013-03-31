package br.com.etraining.client.vo.impl.aluno;

import br.com.etraining.client.vo.interfaces.IVO;

public class ConsultaListaAlunoSimplesVO implements IVO {

	private static final long serialVersionUID = -1609293490424612881L;

	private String nome;
	private String matricula;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
