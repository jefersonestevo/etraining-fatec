package br.com.etraining.modelo.entidades;

import javax.persistence.CascadeType;
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
@Table(name = EntExercicioProposto.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_exercicioProposto", sequenceName = "seq_exercicioProposto", initialValue = 1000)
public class EntExercicioProposto extends BeanJPA {

	private static final long serialVersionUID = 4498345396228979892L;

	public static final String NOME_ENTIDADE = "exercicioProposto";

	public EntExercicioProposto() {
		super();
	}

	public EntExercicioProposto(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_exercicioProposto", strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(targetEntity = EntDiaSemana.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_dia_semana", nullable = false)
	private EntDiaSemana diaSemana;

	@ManyToOne(targetEntity = EntExercicio.class)
	@JoinColumn(name = "id_exercicio", nullable = false)
	private EntExercicio exercicio;

	@ManyToOne(targetEntity = EntAtividade.class)
	@JoinColumn(name = "id_atividade", nullable = false)
	private EntAtividade atividade;

	@ManyToOne(targetEntity = EntProgramaTreinamento.class)
	@JoinColumn(name = "id_progtreinamento", nullable = false)
	private EntProgramaTreinamento programaTreinamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntExercicio getExercicio() {
		return exercicio;
	}

	public void setExercicio(EntExercicio exercicio) {
		this.exercicio = exercicio;
	}

	public EntDiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(EntDiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

	public EntProgramaTreinamento getProgramaTreinamento() {
		return programaTreinamento;
	}

	public void setProgramaTreinamento(
			EntProgramaTreinamento programaTreinamento) {
		this.programaTreinamento = programaTreinamento;
	}

	public EntAtividade getAtividade() {
		return atividade;
	}

	public void setAtividade(EntAtividade atividade) {
		this.atividade = atividade;
	}

}
