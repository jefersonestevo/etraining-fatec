package br.com.etraining.web.managedbeans.exercicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.impl.categoriaexercicio.ConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.categoriaexercicio.RespostaConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.listaexercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.listaexercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.ITratadorNegocioService;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@SessionScoped
public class ListaExercicioController extends EtrainingManagedBean {

	private static final long serialVersionUID = -7972065534994352243L;

	@Inject
	private ITratadorNegocioService service;

	private List<CategoriaExercicioView> listaCategoria = new ArrayList<CategoriaExercicioView>();

	public String irParaTelaPesquisa() {
		try {
			RespostaConsultaListaCategoriaExercicioVO respCategoria = (RespostaConsultaListaCategoriaExercicioVO) service
					.executa(new ConsultaListaCategoriaExercicioVO());

			RespostaConsultaListaExerciciosVO respExercicio = (RespostaConsultaListaExerciciosVO) service
					.executa(new ConsultaListaExerciciosVO());
			List<ExercicioVO> listaExercicioVO = respExercicio
					.getListaExercicios();
			List<CategoriaExercicioVO> listaCategoriaVO = respCategoria
					.getListaCategoriaExercicio();
			Map<Long, CategoriaExercicioView> mapCategoriaPorId = new HashMap<Long, CategoriaExercicioView>();

			for (CategoriaExercicioVO categoria : listaCategoriaVO) {
				mapCategoriaPorId.put(
						categoria.getId(),
						new CategoriaExercicioView(categoria.getId(), categoria
								.getTitulo()));
			}

			for (ExercicioVO exercicio : listaExercicioVO) {
				CategoriaExercicioView catView = mapCategoriaPorId
						.get(exercicio.getIdCategoriaExercicio());
				catView.getListaExercicios().add(exercicio);
			}

			this.listaCategoria.clear();
			if (CollectionUtils.isNotEmpty(mapCategoriaPorId.values())) {
				this.listaCategoria = new ArrayList<CategoriaExercicioView>(
						mapCategoriaPorId.values());
			}
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}

		return "/pages/exercicio/listarExercicios.xhtml";
	}

	public List<CategoriaExercicioView> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<CategoriaExercicioView> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public ITratadorNegocioService getService() {
		return service;
	}

	public void setService(ITratadorNegocioService service) {
		this.service = service;
	}

}
