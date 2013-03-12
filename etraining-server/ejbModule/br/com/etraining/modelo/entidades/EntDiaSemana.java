package br.com.etraining.modelo.entidades;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.etraining.client.utils.TamanhoCampo;
import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntDiaSemana.NOME_ENTIDADE)
public class EntDiaSemana extends BeanJPA {

	private static final long serialVersionUID = 3628981525117376287L;

	public static final String NOME_ENTIDADE = "diaSemana";

	public static final Long DOMINGO = 1l;
	public static final Long SEGUNDA_FEIRA = 2l;
	public static final Long TERCA_FEIRA = 3l;
	public static final Long QUARTA_FEIRA = 4l;
	public static final Long QUINTA_FEIRA = 5l;
	public static final Long SEXTA_FEIRA = 6l;
	public static final Long SABADO = 7l;

	@Id
	private Long id;

	@Column(length = TamanhoCampo.TAMANHO_PEQUENO, nullable = false, updatable = false)
	private String nome;

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

	public static Long getDiaSemana(Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		return getDiaSemana(cal);
	}

	public static Long getDiaSemana(Calendar cal) {
		int diaSemanaCal = cal.get(Calendar.DAY_OF_WEEK);
		Long diaSemana = null;
		switch (diaSemanaCal) {
		case Calendar.SUNDAY:
			diaSemana = DOMINGO;
			break;
		case Calendar.MONDAY:
			diaSemana = SEGUNDA_FEIRA;
			break;
		case Calendar.TUESDAY:
			diaSemana = TERCA_FEIRA;
			break;
		case Calendar.WEDNESDAY:
			diaSemana = QUARTA_FEIRA;
			break;
		case Calendar.THURSDAY:
			diaSemana = QUINTA_FEIRA;
			break;
		case Calendar.FRIDAY:
			diaSemana = SEXTA_FEIRA;
			break;
		case Calendar.SATURDAY:
			diaSemana = SABADO;
			break;
		default:
			throw new IllegalArgumentException("Dia da semana nao reconhecido");
		}

		return diaSemana;
	}

}
