package br.com.etraining.negocio.bo.impl.exercicios;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicio;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorExercicio;

@Named("ConsultaListaExerciciosVO")
public class ConsultaListaExerciciosBO
		extends
		AbstractBO<ConsultaListaExerciciosVO, RespostaConsultaListaExerciciosVO> {

	@Inject
	private IDaoExercicio daoExercicio;

	@Inject
	private ConversorExercicio conversorExercicio;

	@Override
	protected RespostaConsultaListaExerciciosVO executarRegrasEspecificas(
			ConsultaListaExerciciosVO request) throws ETrainingException {

		List<EntExercicio> listaExercicios = daoExercicio.pesquisarLista();

		RespostaConsultaListaExerciciosVO resposta = new RespostaConsultaListaExerciciosVO();
		resposta.setListaExercicios(new ArrayList<ExercicioVO>());
		for (EntExercicio exercicio : listaExercicios) {
			resposta.getListaExercicios().add(
					conversorExercicio.toVO(exercicio));
		}

		return resposta;
	}

	public IDaoExercicio getDaoExercicio() {
		return daoExercicio;
	}

	public void setDaoExercicio(IDaoExercicio daoExercicio) {
		this.daoExercicio = daoExercicio;
	}

	public ConversorExercicio getConversor() {
		return conversorExercicio;
	}

	public void setConversor(ConversorExercicio conversor) {
		this.conversorExercicio = conversor;
	}

}
