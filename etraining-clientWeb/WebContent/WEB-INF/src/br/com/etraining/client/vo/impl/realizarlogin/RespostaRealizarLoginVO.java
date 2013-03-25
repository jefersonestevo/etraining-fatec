package br.com.etraining.client.vo.impl.realizarlogin;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaRealizarLoginVO implements IVO {

	private static final long serialVersionUID = 4260819387773240384L;

	private Long idAluno;
	private String numeroMatricula;
	private List<String> listaPermissoes = new ArrayList<String>();

	public List<String> getListaPermissoes() {
		return listaPermissoes;
	}

	public void setListaPermissoes(List<String> listaPermissoes) {
		this.listaPermissoes = listaPermissoes;
	}

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

}
