package br.com.etraining.negocio.bo.impl.atividades;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.atividades.ConsultaListaAtividadeVO;
import br.com.etraining.client.vo.impl.atividades.RespostaConsultaListaAtividadeVO;
import br.com.etraining.client.vo.impl.entidades.AtividadeVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAtividade;
import br.com.etraining.modelo.entidades.EntAtividade;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorAtividade;

@Named("ConsultaListaAtividadeVO")
public class ConsultaListaAtividadesBO extends
		AbstractBO<ConsultaListaAtividadeVO, RespostaConsultaListaAtividadeVO> {

	@Inject
	private IDaoAtividade atividadeDao;

	@Inject
	private ConversorAtividade conversorAtividade;

	@Override
	protected RespostaConsultaListaAtividadeVO executarRegrasEspecificas(
			ConsultaListaAtividadeVO request) throws ETrainingException {

		RespostaConsultaListaAtividadeVO response = new RespostaConsultaListaAtividadeVO();
		
		List<EntAtividade> listaAtividades = atividadeDao.pesquisarLista();

		List<AtividadeVO> listaAtividadesVO = new ArrayList<AtividadeVO>();
		for (EntAtividade atividade : listaAtividades) {
			listaAtividadesVO.add(conversorAtividade.toVO(atividade));
		}

		response.setListaAtividades(listaAtividadesVO);

		return response;
	}

	public IDaoAtividade getAtividadeDao() {
		return atividadeDao;
	}

	public void setAtividadeDao(IDaoAtividade atividadeDao) {
		this.atividadeDao = atividadeDao;
	}

	public ConversorAtividade getConversorAtividade() {
		return conversorAtividade;
	}

	public void setConversorAtividade(ConversorAtividade conversorAtividade) {
		this.conversorAtividade = conversorAtividade;
	}

}
