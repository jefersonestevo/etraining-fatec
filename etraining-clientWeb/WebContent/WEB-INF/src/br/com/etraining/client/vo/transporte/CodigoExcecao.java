package br.com.etraining.client.vo.transporte;

public enum CodigoExcecao {

	// Codigos de excecao serão sempre codigos de 8 digitos e o primeiro digito
	// não pode ser 0
	USUARIO_NAO_ENCONTRADO(10000001l), //
	USUARIO_INATIVO(10000002l), //
	MATRICULA_NAO_INFORMADA(10000003l), //
	SENHA_NAO_INFORMADA(10000004l), //
	PROGRAMA_TREINAMENTO_INEXISTENTE(20000001l), //
	VERSAO_POSTERIOR_PROG_TREINAMENTO_EXISTENTE(20000002l), //
	ANDROID_APARELHO_SEM_CONEXAO(50000001l), //
	ANDROID_SEM_CONEXAO_SERVIDOR(50000002l), //
	ANDROID_TIMEOUT_SERVIDOR(50000003l), //
	ANDROID_ENTRADA_SAIDA_SERVIDOR(50000004l), //
	ERRO_ARQUIVO_ALUNO_CAMPO_OBRIGATORIO(60000001l), //
	ERRO_ARQUIVO_ALUNO_CAMPO_MAIOR_MAXIMO(60000002l), //
	ERRO_ARQUIVO_ALUNO_CAMPO_NAO_NUMERICO(60000003l), //
	ERRO_LEITURA_ARQUIVO_IMPORTACAO_ALUNOS(60000004l), //
	ERRO_DESCONHECIDO(90000001l);

	private Long codigo;

	private CodigoExcecao(Long codigo) {
		setCodigo(codigo);
	}

	public Long getCodigo() {
		return codigo;
	}

	private void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public static CodigoExcecao getCodigoExcecao(Long codigo) {
		for (CodigoExcecao cod : CodigoExcecao.values()) {
			if (cod.getCodigo().equals(codigo))
				return cod;
		}
		return null;
	}

}
