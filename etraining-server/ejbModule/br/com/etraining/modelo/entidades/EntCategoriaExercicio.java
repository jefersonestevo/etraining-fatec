package br.com.etraining.modelo.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.etraining.client.utils.TamanhoCampo;
import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntCategoriaExercicio.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_categoriaExercicio", sequenceName = "seq_categoriaExercicio", initialValue = 1000)
public class EntCategoriaExercicio extends BeanJPA {

	private static final long serialVersionUID = 3254619956194868886L;

	public static final String NOME_ENTIDADE = "categoriaExercicio";

	public EntCategoriaExercicio() {
		super();
	}

	public EntCategoriaExercicio(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_categoriaExercicio", strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = TamanhoCampo.TAMANHO_MEDIO, nullable = false)
	private String titulo;

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

}
