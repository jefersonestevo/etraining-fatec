package br.com.etraining.exception;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ApplicationException;

import br.com.etraining.client.vo.transporte.CodigoExcecao;

@ApplicationException(rollback = true)
public class ETrainingImportacaoException extends ETrainingException {

	private static final long serialVersionUID = 2841774896783168096L;

	private List<ErroImportacao> listaErros = new ArrayList<ErroImportacao>();

	public ETrainingImportacaoException(CodigoExcecao codigoExcecao) {
		super(codigoExcecao);
	}

	public void adicionarErro(ErroImportacao erro) {
		listaErros.add(erro);
	}

	public List<ErroImportacao> getListaErros() {
		return new ArrayList<ErroImportacao>(listaErros);
	}

}
