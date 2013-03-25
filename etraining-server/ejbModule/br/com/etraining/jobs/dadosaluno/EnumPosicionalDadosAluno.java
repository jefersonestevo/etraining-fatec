package br.com.etraining.jobs.dadosaluno;

import br.com.etraining.client.utils.TamanhoCampo;

public enum EnumPosicionalDadosAluno {
	MATRICULA(true, TamanhoCampo.TAMANHO_MUITO_PEQUENO, false), //
	SENHA(true, TamanhoCampo.TAMANHO_PEQUENO, false), //
	NOME(true, TamanhoCampo.TAMANHO_MEDIO, false), //
	EMAIL(false, TamanhoCampo.TAMANHO_MEDIO, false), //
	TELEFONE(false, TamanhoCampo.TAMANHO_MUITO_PEQUENO, true), //
	RG(true, 9, false), //
	CPF(false, 11, false), //
	DADA_NASCIMENTO(false, 11, false), //
	ALTURA(false, TamanhoCampo.TAMANHO_MEDIO, true), //
	PESO(false, TamanhoCampo.TAMANHO_MEDIO, true), //
	BATIMENTO_MINUTO(false, TamanhoCampo.TAMANHO_MEDIO, true), //
	GORDURA_CORPORAL(false, TamanhoCampo.TAMANHO_MEDIO, true), //
	PRESSAO_MAXIMA(false, TamanhoCampo.TAMANHO_MEDIO, true), //
	PRESSAO_MINIMA(false, TamanhoCampo.TAMANHO_MEDIO, true), //
	TEMPO_ESTEIRA(false, TamanhoCampo.TAMANHO_MEDIO, true), //
	PERFIL_ACESSO(true, TamanhoCampo.TAMANHO_MUITO_PEQUENO, false), //
	DIAS_TREINAMENTO(true, TamanhoCampo.TAMANHO_PEQUENO, false), //
	;

	private boolean obrigatorio = false;
	private Integer tamanhoMaximo;
	private boolean somenteNumerico = false;

	private EnumPosicionalDadosAluno(boolean obrigatorio,
			Integer tamanhoMaximo, boolean somenteNumerico) {
		this.obrigatorio = obrigatorio;
		this.tamanhoMaximo = tamanhoMaximo;
		this.somenteNumerico = somenteNumerico;
	}

	public boolean isObrigatorio() {
		return obrigatorio;
	}

	public Integer getTamanhoMaximo() {
		return tamanhoMaximo;
	}

	public boolean isSomenteNumerico() {
		return somenteNumerico;
	}

}
