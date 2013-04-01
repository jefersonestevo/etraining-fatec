package br.com.etraining.modelo.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.etraining.client.utils.TamanhoCampo;
import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntPerfilAcesso.NOME_ENTIDADE)
public class EntPerfilAcesso extends BeanJPA {

	private static final long serialVersionUID = -7596933765979557681L;

	public static final String NOME_ENTIDADE = "perfilAcesso";

	public EntPerfilAcesso() {
		super();
	}

	public EntPerfilAcesso(Long id) {
		super(id);
	}

	@Id
	private Long id;

	@Column(length = TamanhoCampo.TAMANHO_MEDIO, nullable = false, updatable = false, insertable = false)
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

}
