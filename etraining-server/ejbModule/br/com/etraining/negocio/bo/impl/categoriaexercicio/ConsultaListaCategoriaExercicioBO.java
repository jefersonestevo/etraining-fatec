package br.com.etraining.negocio.bo.impl.categoriaexercicio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.etraining.client.vo.impl.categoriaexercicio.ConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.categoriaexercicio.RespostaConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoCategoriaExercicio;
import br.com.etraining.modelo.entidades.EntCategoriaExercicio;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorCategoriaExercicio;

public class ConsultaListaCategoriaExercicioBO
		extends
		AbstractBO<ConsultaListaCategoriaExercicioVO, RespostaConsultaListaCategoriaExercicioVO> {

	@Inject
	private IDaoCategoriaExercicio categoriaExercicioDao;

	@Inject
	private ConversorCategoriaExercicio conversorCategoriaExercicio;

	@Override
	protected RespostaConsultaListaCategoriaExercicioVO executarRegrasEspecificas(
			ConsultaListaCategoriaExercicioVO request)
			throws ETrainingException {

		RespostaConsultaListaCategoriaExercicioVO response = new RespostaConsultaListaCategoriaExercicioVO();

		List<EntCategoriaExercicio> listaCategoriaExercicio = categoriaExercicioDao
				.pesquisarLista();

		List<CategoriaExercicioVO> listaCategoriaExercicioVO = new ArrayList<CategoriaExercicioVO>();
		for (EntCategoriaExercicio cat : listaCategoriaExercicio) {
			listaCategoriaExercicioVO
					.add(conversorCategoriaExercicio.toVO(cat));
		}

		response.setListaCategoriaExercicio(listaCategoriaExercicioVO);

		return response;
	}

}
