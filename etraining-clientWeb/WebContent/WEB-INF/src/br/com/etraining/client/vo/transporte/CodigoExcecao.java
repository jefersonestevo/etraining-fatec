package br.com.etraining.client.vo.transporte;

import java.util.HashMap;
import java.util.Map;

public enum CodigoExcecao {

	// Codigos de exceção são sempre codigos de 8 digitos e o primeiro digito
	// não pode ser 0
	USUARIO_NAO_ENCONTRADO(10000001l), //
	USUARIO_INATIVO(10000002l), //
	MATRICULA_NAO_INFORMADA(10000003l), //
	SENHA_NAO_INFORMADA(10000004l), //
	ERRO_DESCONHECIDO(90000001l);

	private Long codigo;
	private static final Map<Long, CodigoExcecao> mapCodigoExcecao = new HashMap<Long, CodigoExcecao>();

	private CodigoExcecao(Long codigo) {
		setCodigo(codigo);
	}

	public Long getCodigo() {
		return codigo;
	}

	private void setCodigo(Long codigo) {
		this.codigo = codigo;
		mapCodigoExcecao.put(codigo, this);
	}

	public static CodigoExcecao getCodigoExcecao(Long codigo) {
		return mapCodigoExcecao.get(codigo);
	}

}
