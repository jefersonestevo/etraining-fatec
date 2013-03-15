package br.com.etraining.client.vo.impl.atividades;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.AtividadeVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class RespostaConsultaListaAtividadeVO implements IVO {

	private static final long serialVersionUID = -4197957922878083127L;

	private List<AtividadeVO> listaAtividades = new ArrayList<AtividadeVO>();

	public List<AtividadeVO> getListaAtividades() {
		return listaAtividades;
	}

	public void setListaAtividades(List<AtividadeVO> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

}
