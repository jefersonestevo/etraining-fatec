package br.com.etraining.web.utils.comparador;

import java.util.Comparator;

import br.com.etraining.client.vo.impl.entidades.AlunoSimplesVO;

public class ComparadorAlunoSimplesAlfabetico implements
		Comparator<AlunoSimplesVO> {

	@Override
	public int compare(AlunoSimplesVO al1, AlunoSimplesVO al2) {

		if (al1 == null && al2 != null)
			return -1;
		if (al1 != null && al2 == null)
			return 1;
		if (al1 == null && al2 == null)
			return 0;

		if (al1.getNome() == null && al2.getNome() != null)
			return -1;
		if (al1.getNome() != null && al2.getNome() == null)
			return 1;
		if (al1.getNome() == null && al2.getNome() == null)
			return 0;

		return al1.getNome().compareTo(al2.getNome());
	}

}
