package br.com.etraining.client.vo.impl.realizarlogin;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.dom.PerfilAcesso;
import br.com.etraining.client.vo.interfaces.IVO;

public class RealizarLoginVO implements IVO {

	private static final long serialVersionUID = -8567095371269700172L;

	private String numeroMatricula;
	private String senha;
	private List<PerfilAcesso> perfisAceitos = new ArrayList<PerfilAcesso>();

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public List<PerfilAcesso> getPerfisAceitos() {
		return perfisAceitos;
	}

	public void setPerfisAceitos(List<PerfilAcesso> perfisAceitos) {
		this.perfisAceitos = perfisAceitos;
	}

}
