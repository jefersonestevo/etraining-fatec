package br.com.etraining.client.vo.transporte;

import java.util.HashMap;
import java.util.Map;

public enum CodigoExcecao {

	// Codigos de excecao serão sempre codigos de 8 digitos e o primeiro digito
	// não pode ser 0
	USUARIO_NAO_ENCONTRADO(10000001l), //
	USUARIO_INATIVO(10000002l), //
	MATRICULA_NAO_INFORMADA(10000003l), //
	SENHA_NAO_INFORMADA(10000004l), //
	PROGRAMA_TREINAMENTO_INEXISTENTE(20000001l), //
	ERRO_DESCONHECIDO(90000001l);

	private Long codigo;
	/**
	 * Mapa para guardar os codigos de exceção como um cache para evitar rodar
	 * por todas as exceções do enum no metodo getCodigoExcecao(Long codigo)
	 */
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
