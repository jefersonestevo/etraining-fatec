package br.com.etraining.modelo.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.etraining.client.dom.StatusProgramaTreinamento;
import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntProgramaTreinamento.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_programaTreinamento", sequenceName = "seq_programaTreinamento", initialValue = 1000)
public class EntProgramaTreinamento extends BeanJPA {

	private static final long serialVersionUID = 9221415056856230733L;

	public static final String NOME_ENTIDADE = "programaTreinamento";

	public EntProgramaTreinamento() {
		super();
	}

	public EntProgramaTreinamento(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_programaTreinamento", strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(targetEntity = EntAluno.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_aluno", nullable = false)
	private EntAluno aluno;

	@OneToMany(targetEntity = EntExercicioProposto.class, mappedBy = "programaTreinamento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EntExercicioProposto> listaExercicioProposto = new ArrayList<EntExercicioProposto>();

	@Column(nullable = false)
	private Integer versao = 1;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private StatusProgramaTreinamento status = StatusProgramaTreinamento.AGUARDANDO_APROVACAO;

	@Column(name = "data_aprovacao")
	@Temporal(TemporalType.DATE)
	private Date dataAprovacao;

	@Column(name = "data_vencimento")
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;

	@Column(name = "data_cancelamento")
	@Temporal(TemporalType.DATE)
	private Date dataCancelamento;

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

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public List<EntExercicioProposto> getListaExercicioProposto() {
		return listaExercicioProposto;
	}

	public void setListaExercicioProposto(
			List<EntExercicioProposto> listaExercicioProposto) {
		this.listaExercicioProposto = listaExercicioProposto;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public StatusProgramaTreinamento getStatus() {
		return status;
	}

	public void setStatus(StatusProgramaTreinamento status) {
		this.status = status;
	}

	public Date getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

}
