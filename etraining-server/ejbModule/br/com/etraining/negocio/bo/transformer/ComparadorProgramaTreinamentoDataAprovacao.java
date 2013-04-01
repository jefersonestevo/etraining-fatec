package br.com.etraining.negocio.bo.transformer;

import java.util.Comparator;

import br.com.etraining.modelo.entidades.EntProgramaTreinamento;

public class ComparadorProgramaTreinamentoDataAprovacao implements
		Comparator<EntProgramaTreinamento> {

	@Override
	public int compare(EntProgramaTreinamento p1, EntProgramaTreinamento p2) {
		return p1.getDataAprovacao().compareTo(p2.getDataAprovacao());
	}

}
