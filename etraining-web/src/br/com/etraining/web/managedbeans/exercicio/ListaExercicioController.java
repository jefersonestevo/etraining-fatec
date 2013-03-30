package br.com.etraining.web.managedbeans.exercicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.categoriaexercicio.RespostaConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.AtividadeVO;
import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.listaexercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@SessionScoped
public class ListaExercicioController extends EtrainingManagedBean {

	private static final long serialVersionUID = -7972065534994352243L;

	private List<CategoriaExercicioView> listaCategoria = new ArrayList<CategoriaExercicioView>();

	public String irParaTelaPesquisa() {
		Map<Integer, AtividadeVO> mapAtividadePorId = new HashMap<Integer, AtividadeVO>();
		for (int i = 0; i < 5; i++) {
			AtividadeVO atv = new AtividadeVO();
			atv.setId(new Long(i));
			atv.setTitulo("ATIVIDADE 0" + i);
			mapAtividadePorId.put(i, atv);
		}

		RespostaConsultaListaCategoriaExercicioVO respCategoria = new RespostaConsultaListaCategoriaExercicioVO();

		for (int i = 0; i < 5; i++) {
			CategoriaExercicioVO cat = new CategoriaExercicioVO();
			cat.setId(new Long(i));
			cat.setTitulo("CATEGORIA 0" + i);
			respCategoria.getListaCategoriaExercicio().add(cat);
		}

		RespostaConsultaListaExerciciosVO respExercicio = new RespostaConsultaListaExerciciosVO();

		for (int i = 0; i < 100; i++) {
			ExercicioVO exerc = new ExercicioVO();
			exerc.setId(new Long(i));
			Integer idCategoria = new Double(Math.random() * 5).intValue();
			exerc.setIdCategoriaExercicio(new Long(idCategoria));
			exerc.setTitulo("EXERC " + i);

			AtividadeVO atv = mapAtividadePorId.get(new Double(
					Math.random() * 5).intValue());
			exerc.setAtividade(atv);

			exerc.setPontosPorAtividade(new Double(Math.random() * 100)
					.intValue());
			respExercicio.getListaExercicios().add(exerc);
		}

		// TODO - Fazer funcionar EJB

		List<ExercicioVO> listaExercicioVO = respExercicio.getListaExercicios();
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
			CategoriaExercicioView catView = mapCategoriaPorId.get(exercicio
					.getIdCategoriaExercicio());
			catView.getListaExercicios().add(exercicio);
		}

		this.listaCategoria = new ArrayList<CategoriaExercicioView>(
				mapCategoriaPorId.values());

		return "/pages/exercicio/listarExercicios.xhtml";
	}

	public List<CategoriaExercicioView> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<CategoriaExercicioView> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

}
