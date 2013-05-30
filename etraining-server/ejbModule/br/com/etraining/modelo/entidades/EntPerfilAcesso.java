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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntPerfilAcesso other = (EntPerfilAcesso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
