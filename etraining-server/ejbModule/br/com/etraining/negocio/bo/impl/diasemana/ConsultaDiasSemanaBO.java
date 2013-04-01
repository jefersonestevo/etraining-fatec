package br.com.etraining.negocio.bo.impl.diasemana;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.diasemana.ConsultaDiasSemanaVO;
import br.com.etraining.client.vo.impl.diasemana.RespostaConsultaDiasSemanaVO;
import br.com.etraining.client.vo.impl.entidades.DiaSemanaVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoDiaSemana;
import br.com.etraining.modelo.entidades.EntDiaSemana;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorDiaSemana;

@Named("ConsultaDiasSemanaVO")
public class ConsultaDiasSemanaBO extends
		AbstractBO<ConsultaDiasSemanaVO, RespostaConsultaDiasSemanaVO> {

	@Inject
	private IDaoDiaSemana diaSemanaDao;

	@Inject
	private ConversorDiaSemana conversorDiaSemana;

	@Override
	protected RespostaConsultaDiasSemanaVO executarRegrasEspecificas(
			ConsultaDiasSemanaVO request) throws ETrainingException {

		List<EntDiaSemana> listaDiasSemana = diaSemanaDao.pesquisarLista();

		List<DiaSemanaVO> listaDiasSemanaVO = new ArrayList<DiaSemanaVO>();
		for (EntDiaSemana dia : listaDiasSemana) {
			listaDiasSemanaVO.add(conversorDiaSemana.toVO(dia));
		}

		RespostaConsultaDiasSemanaVO resposta = new RespostaConsultaDiasSemanaVO();
		resposta.setListaDiasSemana(listaDiasSemanaVO);
		return resposta;
	}

	public IDaoDiaSemana getDiaSemanaDao() {
		return diaSemanaDao;
	}

	public void setDiaSemanaDao(IDaoDiaSemana diaSemanaDao) {
		this.diaSemanaDao = diaSemanaDao;
	}

	public ConversorDiaSemana getConversorDiaSemana() {
		return conversorDiaSemana;
	}

	public void setConversorDiaSemana(ConversorDiaSemana conversorDiaSemana) {
		this.conversorDiaSemana = conversorDiaSemana;
	}

}
