package br.com.etraining.modelo.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.etraining.client.utils.TamanhoCampo;
import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntExercicio.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_exercicio", sequenceName = "seq_exercicio", initialValue = 1000)
public class EntExercicio extends BeanJPA {

	private static final long serialVersionUID = 1454095739324196555L;

	public static final String NOME_ENTIDADE = "exercicio";

	public EntExercicio() {
		super();
	}

	public EntExercicio(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_exercicio", strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = TamanhoCampo.TAMANHO_MEDIO, nullable = false)
	private String titulo;

	@ManyToOne(targetEntity = EntCategoriaExercicio.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_categoria_exercicio")
	private EntCategoriaExercicio categoriaExercicio;

	@Column
	private Integer pontosPorAtividade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public EntCategoriaExercicio getCategoriaExercicio() {
		return categoriaExercicio;
	}

	public void setCategoriaExercicio(EntCategoriaExercicio categoriaExercicio) {
		this.categoriaExercicio = categoriaExercicio;
	}

	public Integer getPontosPorAtividade() {
		return pontosPorAtividade;
	}

	public void setPontosPorAtividade(Integer pontosPorAtividade) {
		this.pontosPorAtividade = pontosPorAtividade;
	}

}
