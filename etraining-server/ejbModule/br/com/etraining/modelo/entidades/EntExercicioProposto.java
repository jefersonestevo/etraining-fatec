package br.com.etraining.modelo.entidades;

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

	@ManyToOne(targetEntity = EntDiaSemana.class)
	@JoinColumn(name = "id_dia_semana", nullable = false)
	private EntDiaSemana diaSemana;

	@ManyToOne(targetEntity = EntExercicio.class)
	@JoinColumn(name = "id_exercicio", nullable = false)
	private EntExercicio exercicio;

	@ManyToOne(targetEntity = EntProgramaTreinamento.class)
	@JoinColumn(name = "id_progtreinamento", nullable = false)
	private EntProgramaTreinamento programaTreinamento;

	@Column(name = "qntd_exerc_sugerida", nullable = false)
	private Integer quantidadeExercicioSugerida = 1;

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

	public Integer getQuantidadeExercicioSugerida() {
		return quantidadeExercicioSugerida;
	}

	public void setQuantidadeExercicioSugerida(
			Integer quantidadeExercicioSugerida) {
		this.quantidadeExercicioSugerida = quantidadeExercicioSugerida;
	}

}
