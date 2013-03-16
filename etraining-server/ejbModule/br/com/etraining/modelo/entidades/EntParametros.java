package br.com.etraining.modelo.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.etraining.client.utils.TamanhoCampo;
import br.com.etraining.modelo.def.impl.jpa.BeanJPA;

@Entity
@Table(name = EntParametros.NOME_ENTIDADE)
public class EntParametros extends BeanJPA {

	private static final long serialVersionUID = -6912567878150397822L;

	public static final String NOME_ENTIDADE = "parametros";

	public static final Long ID_DIRETORIO_CARGA_ALUNOS = 1l;

	public EntParametros() {
		super();
	}

	public EntParametros(Long id) {
		super(id);
	}

	@Id
	private Long id;

	@Column(length = TamanhoCampo.TAMANHO_GRANDE)
	private String descricao;

	@Column(length = TamanhoCampo.TAMANHO_MEDIO, nullable = false)
	private String valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
