package br.com.etraining.client.vo.impl.entidades;

import java.util.Date;

import br.com.etraining.client.dom.Sexo;
import br.com.etraining.client.vo.interfaces.IVO;

public class AlunoVO implements IVO {

	private static final long serialVersionUID = 4276276280257784013L;

	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String senha;
	private Date dataNascimento;
	private Sexo sexo;
	private MatriculaVO matricula;
	private DadosCorporaisVO dadosCorporais;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public MatriculaVO getMatricula() {
		return matricula;
	}

	public void setMatricula(MatriculaVO matricula) {
		this.matricula = matricula;
	}

	public DadosCorporaisVO getDadosCorporais() {
		return dadosCorporais;
	}

	public void setDadosCorporais(DadosCorporaisVO dadosCorporais) {
		this.dadosCorporais = dadosCorporais;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
