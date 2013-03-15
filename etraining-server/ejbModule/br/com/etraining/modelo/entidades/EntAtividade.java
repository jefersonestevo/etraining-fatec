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
@Table(name = EntAtividade.NOME_ENTIDADE)
@SequenceGenerator(name = "seq_atividade", sequenceName = "seq_atividade", initialValue = 1000)
public class EntAtividade extends BeanJPA {

	private static final long serialVersionUID = -6506040164762675556L;

	public static final String NOME_ENTIDADE = "atividade";

	public EntAtividade() {
		super();
	}

	public EntAtividade(Long id) {
		super(id);
	}

	@Id
	@GeneratedValue(generator = "seq_atividade", strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = TamanhoCampo.TAMANHO_MEDIO, nullable = false)
	private String titulo;

	@Column(name = "quantidade_alteravel", nullable = false)
	private Boolean quantidadeAlteravel = false;

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

	public Boolean getQuantidadeAlteravel() {
		return quantidadeAlteravel;
	}

	public void setQuantidadeAlteravel(Boolean quantidadeAlteravel) {
		this.quantidadeAlteravel = quantidadeAlteravel;
	}

}
