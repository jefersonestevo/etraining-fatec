package br.com.etraining.modelo.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntMatricula.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_matricula", sequenceName = "seq_matricula", initialValue = 1000)
public class EntMatricula extends BeanJPA {

	private static final long serialVersionUID = -3309286217027822630L;

	public static final String NOME_ENTIDADE = "matricula";

	public EntMatricula() {
		super();
	}

	public EntMatricula(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_matricula", strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(targetEntity = EntAluno.class)
	@JoinColumn(name = "id_aluno")
	private EntAluno aluno;

	@Column(name = "num_matricula", updatable = false)
	private Long numeroMatricula;

	@Column(name = "senha_usuario", updatable = false)
	private String senhaUsuario;

	@Column(name = "usuario_ativo")
	private Boolean usuarioAtivo = true;

	@Column(name = "perfil_acesso", updatable = false)
	private String perfilAcesso;

	@Column
	private String rg;

	@Column
	private String cpf;

	@ManyToMany(targetEntity = EntDiaSemana.class, fetch = FetchType.LAZY)
	@JoinTable(name = "matricula_diasemana", joinColumns = @JoinColumn(name = "id_matricula", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_dia_treinamento", referencedColumnName = "id"))
	private List<EntDiaSemana> listaDiasTreinamento = new ArrayList<EntDiaSemana>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntAluno getAluno() {
		return aluno;
	}

	public void setAluno(EntAluno aluno) {
		this.aluno = aluno;
	}

	public Long getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(Long numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public Boolean getUsuarioAtivo() {
		return usuarioAtivo;
	}

	public void setUsuarioAtivo(Boolean usuarioAtivo) {
		this.usuarioAtivo = usuarioAtivo;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<EntDiaSemana> getListaDiasTreinamento() {
		return listaDiasTreinamento;
	}

	public void setListaDiasTreinamento(List<EntDiaSemana> listaDiasTreinamento) {
		this.listaDiasTreinamento = listaDiasTreinamento;
	}

	public String getPerfilAcesso() {
		return perfilAcesso;
	}

	public void setPerfilAcesso(String perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}

}
