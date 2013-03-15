package br.com.etraining.modelo.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntExercicioRealizado.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_exercicioRealizado", sequenceName = "seq_exercicioRealizado", initialValue = 1000)
public class EntExercicioRealizado extends BeanJPA {

	private static final long serialVersionUID = 8015803438553985170L;

	public static final String NOME_ENTIDADE = "exercicioRealizado";

	public EntExercicioRealizado() {
		super();
	}

	public EntExercicioRealizado(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_exercicioRealizado", strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(targetEntity = EntDiaExercicio.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "dia_exercicio_id", nullable = false)
	private EntDiaExercicio diaExercicio;

	@ManyToOne(targetEntity = EntExercicio.class)
	@JoinColumn(name = "exercicio_id", nullable = false)
	private EntExercicio exercicio;

	@ManyToOne(targetEntity = EntAtividade.class)
	@JoinColumn(name = "atividade_id", nullable = false)
	private EntAtividade atividade;

	@Column(name = "quantidade_atividade")
	private Integer quantidadeAtividade;

	@Column
	private Double pontos = 0d;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntDiaExercicio getDiaExercicio() {
		return diaExercicio;
	}

	public void setDiaExercicio(EntDiaExercicio diaExercicio) {
		this.diaExercicio = diaExercicio;
	}

	public EntExercicio getExercicio() {
		return exercicio;
	}

	public void setExercicio(EntExercicio exercicio) {
		this.exercicio = exercicio;
	}

	public EntAtividade getAtividade() {
		return atividade;
	}

	public void setAtividade(EntAtividade atividade) {
		this.atividade = atividade;
	}

	public Double getPontos() {
		return pontos;
	}

	public void setPontos(Double pontos) {
		this.pontos = pontos;
	}

	public Integer getQuantidadeAtividade() {
		return quantidadeAtividade;
	}

	public void setQuantidadeAtividade(Integer quantidadeAtividade) {
		this.quantidadeAtividade = quantidadeAtividade;
	}

}
