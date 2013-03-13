package br.com.etraining.modelo.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntDadosCorporais.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_dadosCorporais", sequenceName = "seq_dadosCorporais", initialValue = 1000)
public class EntDadosCorporais extends BeanJPA {

	private static final long serialVersionUID = 6708864138472398625L;

	public static final String NOME_ENTIDADE = "dadosCorporais";

	public EntDadosCorporais() {
		super();
	}

	public EntDadosCorporais(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_dadosCorporais", strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(targetEntity = EntAluno.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "aluno_id")
	private EntAluno aluno;

	@Column
	private Double peso;

	@Column
	private Double altura;

	@Column(name = "ind_gordura_corporal")
	private Double indiceGorduraCorporal;

	@Column(name = "batimentos_minuto")
	private Integer batimentosPorMinuto;

	@Column(name = "tempo_esteira")
	private Double tempoEsteira;

	@Column(name = "pressao_arterial_maxima")
	private Double pressaoArterialMaxima;

	@Column(name = "pressao_arterial_minima")
	private Double pressaoArterialMinima;

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

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getIndiceGorduraCorporal() {
		return indiceGorduraCorporal;
	}

	public void setIndiceGorduraCorporal(Double indiceGorduraCorporal) {
		this.indiceGorduraCorporal = indiceGorduraCorporal;
	}

	public Integer getBatimentosPorMinuto() {
		return batimentosPorMinuto;
	}

	public void setBatimentosPorMinuto(Integer batimentosPorMinuto) {
		this.batimentosPorMinuto = batimentosPorMinuto;
	}

	public Double getTempoEsteira() {
		return tempoEsteira;
	}

	public void setTempoEsteira(Double tempoEsteira) {
		this.tempoEsteira = tempoEsteira;
	}

	public Double getPressaoArterialMaxima() {
		return pressaoArterialMaxima;
	}

	public void setPressaoArterialMaxima(Double pressaoArterialMaxima) {
		this.pressaoArterialMaxima = pressaoArterialMaxima;
	}

	public Double getPressaoArterialMinima() {
		return pressaoArterialMinima;
	}

	public void setPressaoArterialMinima(Double pressaoArterialMinima) {
		this.pressaoArterialMinima = pressaoArterialMinima;
	}

}
