package br.com.etraining.client.vo.impl.aluno;

import br.com.etraining.client.vo.interfaces.IVO;

public class TrocaSenhaVO implements IVO {

	private static final long serialVersionUID = 7808311735712597153L;

	private String matricula;
	private String senhaAntiga;
	private String senhaNova;
	private String senhaNova2;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getSenhaNova2() {
		return senhaNova2;
	}

	public void setSenhaNova2(String senhaNova2) {
		this.senhaNova2 = senhaNova2;
	}

}
