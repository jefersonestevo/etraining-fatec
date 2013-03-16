package br.com.etraining.modelo.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntDiaExercicio.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_diaExercicio", sequenceName = "seq_diaExercicio", initialValue = 1000)
public class EntDiaExercicio extends BeanJPA {

	private static final long serialVersionUID = -1095491222324455648L;

	public static final String NOME_ENTIDADE = "diaExercicio";

	public EntDiaExercicio() {
		super();
	}

	public EntDiaExercicio(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_diaExercicio", strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(targetEntity = EntAluno.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_aluno", nullable = false)
	private EntAluno aluno;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataRealizacao;

	@OneToMany(targetEntity = EntExercicioRealizado.class, mappedBy = "diaExercicio", fetch = FetchType.LAZY)
	private List<EntExercicioRealizado> listaExercicioRealizado = new ArrayList<EntExercicioRealizado>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public EntAluno getAluno() {
		return aluno;
	}

	public void setAluno(EntAluno aluno) {
		this.aluno = aluno;
	}

	public List<EntExercicioRealizado> getListaExercicioRealizado() {
		return listaExercicioRealizado;
	}

	public void setListaExercicioRealizado(
			List<EntExercicioRealizado> listaExercicioRealizado) {
		this.listaExercicioRealizado = listaExercicioRealizado;
	}

}
