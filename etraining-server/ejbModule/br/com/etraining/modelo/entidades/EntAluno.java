package br.com.etraining.modelo.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.etraining.client.utils.TamanhoCampo;
import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntAluno.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_aluno", sequenceName = "seq_aluno", initialValue = 1000)
public class EntAluno extends BeanJPA {

	private static final long serialVersionUID = -1072246639405180151L;

	public static final String NOME_ENTIDADE = "aluno";

	public EntAluno() {
		super();
	}

	public EntAluno(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_aluno", strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = TamanhoCampo.TAMANHO_MEDIO, nullable = false)
	private String nome;

	@Column(length = TamanhoCampo.TAMANHO_MEDIO)
	private String email;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Column(length = TamanhoCampo.TAMANHO_PEQUENO)
	private String telefone;

	@Column
	private Integer pontuacaoSemanalAluno;

	@OneToOne(targetEntity = EntDadosCorporais.class, mappedBy = "aluno")
	private EntDadosCorporais dadosCorporais;

	@OneToOne(targetEntity = EntMatricula.class, mappedBy = "aluno")
	private EntMatricula matricula;

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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getPontuacaoSemanalAluno() {
		return pontuacaoSemanalAluno;
	}

	public void setPontuacaoSemanalAluno(Integer pontuacaoSemanalAluno) {
		this.pontuacaoSemanalAluno = pontuacaoSemanalAluno;
	}

	public EntDadosCorporais getDadosCorporais() {
		return dadosCorporais;
	}

	public void setDadosCorporais(EntDadosCorporais dadosCorporais) {
		this.dadosCorporais = dadosCorporais;
	}

	public EntMatricula getMatricula() {
		return matricula;
	}

	public void setMatricula(EntMatricula matricula) {
		this.matricula = matricula;
	}

}
