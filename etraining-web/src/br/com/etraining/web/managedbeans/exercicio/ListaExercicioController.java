package br.com.etraining.web.managedbeans.exercicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.impl.atividades.ConsultaListaAtividadeVO;
import br.com.etraining.client.vo.impl.atividades.RespostaConsultaListaAtividadeVO;
import br.com.etraining.client.vo.impl.categoriaexercicio.ConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.categoriaexercicio.RespostaConsultaListaCategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.AtividadeVO;
import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.AlteraExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.ConsultaListaExerciciosVO;
import br.com.etraining.client.vo.impl.exercicios.InsereExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaListaExerciciosVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
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

	public static final int CATEGORIA_INVALIDA = -1;
	public static final int ATIVIDADE_INVALIDA = -1;

	private List<SelectItem> listaCategoriaSelect = new ArrayList<SelectItem>();
	private List<SelectItem> listaAtividadeSelect = new ArrayList<SelectItem>();
	private ExercicioVO exercicioAtual;

	public String irParaTelaPesquisa() {
		try {
			limparForm();

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

	public String irParaTelaInsercaoExercicio() {
		try {
			this.exercicioAtual = new ExercicioVO();
			this.exercicioAtual.setAtividade(new AtividadeVO());
			preencherListaCategoriaSelect();
			preencherListaAtividadeSelect();
			return "/pages/exercicio/inserirExercicio.xhtml";
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}
	}

	public String irParaTelaEdicaoExercicio() {
		Long idExerc = getLongParameter("id");
		return irParaTelaEdicaoExercicio(idExerc);
	}

	public String irParaTelaEdicaoExercicio(Long idExerc) {
		try {
			RespostaConsultaListaExerciciosVO respExercicio = (RespostaConsultaListaExerciciosVO) service
					.executa(new ConsultaListaExerciciosVO());

			ExercicioVO exercicio = null;
			for (ExercicioVO exerc : respExercicio.getListaExercicios()) {
				if (exerc.getId().equals(idExerc)) {
					exercicio = exerc;
					break;
				}
			}

			this.exercicioAtual = exercicio;
			preencherListaCategoriaSelect();
			preencherListaAtividadeSelect();
			return "/pages/exercicio/alterarExercicio.xhtml";
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}
	}

	public String inserir() {
		try {
			if (this.exercicioAtual.getIdCategoriaExercicio() == CATEGORIA_INVALIDA) {
				throw new ViewException(CodigoExcecao.CATEGORIA_EXERCICIO_NULA);
			}

			if (this.exercicioAtual.getAtividade().getId() == ATIVIDADE_INVALIDA) {
				throw new ViewException(CodigoExcecao.ATIVIDADE_EXERCICIO_NULA);
			}

			this.exercicioAtual.setId(null);
			InsereExercicioVO insercao = new InsereExercicioVO();
			insercao.setExercicio(this.exercicioAtual);
			RespostaConsultaExercicioVO resp = (RespostaConsultaExercicioVO) service
					.executa(insercao);
			this.exercicioAtual = resp.getExercicio();
			String ret = irParaTelaEdicaoExercicio(this.exercicioAtual.getId());
			showSuccessMessage();
			return ret;
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}
	}

	public String alterar() {
		try {
			if (this.exercicioAtual.getIdCategoriaExercicio() == CATEGORIA_INVALIDA) {
				throw new ViewException(CodigoExcecao.CATEGORIA_EXERCICIO_NULA);
			}

			if (this.exercicioAtual.getAtividade().getId() == ATIVIDADE_INVALIDA) {
				throw new ViewException(CodigoExcecao.ATIVIDADE_EXERCICIO_NULA);
			}

			AlteraExercicioVO altera = new AlteraExercicioVO();
			altera.setExercicio(this.exercicioAtual);
			RespostaConsultaExercicioVO resp = (RespostaConsultaExercicioVO) service
					.executa(altera);
			this.exercicioAtual = resp.getExercicio();
			String ret = irParaTelaEdicaoExercicio(this.exercicioAtual.getId());
			showSuccessMessage();
			return ret;
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}
	}

	private void preencherListaCategoriaSelect() throws ViewException {
		ConsultaListaCategoriaExercicioVO consulta = new ConsultaListaCategoriaExercicioVO();
		RespostaConsultaListaCategoriaExercicioVO resp = (RespostaConsultaListaCategoriaExercicioVO) service
				.executa(consulta);

		this.listaCategoriaSelect = new ArrayList<SelectItem>();
		this.listaCategoriaSelect.add(new SelectItem(CATEGORIA_INVALIDA, ""));
		for (CategoriaExercicioVO cat : resp.getListaCategoriaExercicio()) {
			this.listaCategoriaSelect.add(new SelectItem(cat.getId(), cat
					.getTitulo()));
		}
	}

	private void preencherListaAtividadeSelect() throws ViewException {
		ConsultaListaAtividadeVO consulta = new ConsultaListaAtividadeVO();
		RespostaConsultaListaAtividadeVO resp = (RespostaConsultaListaAtividadeVO) service
				.executa(consulta);

		this.listaAtividadeSelect = new ArrayList<SelectItem>();
		this.listaAtividadeSelect.add(new SelectItem(ATIVIDADE_INVALIDA, ""));
		for (AtividadeVO atv : resp.getListaAtividades()) {
			this.listaAtividadeSelect.add(new SelectItem(atv.getId(), atv
					.getTitulo()));
		}
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

	public ExercicioVO getExercicioAtual() {
		return exercicioAtual;
	}

	public void setExercicioAtual(ExercicioVO exercicioAtual) {
		this.exercicioAtual = exercicioAtual;
	}

	public List<SelectItem> getListaCategoriaSelect() {
		return listaCategoriaSelect;
	}

	public void setListaCategoriaSelect(List<SelectItem> listaCategoriaSelect) {
		this.listaCategoriaSelect = listaCategoriaSelect;
	}

	public List<SelectItem> getListaAtividadeSelect() {
		return listaAtividadeSelect;
	}

	public void setListaAtividadeSelect(List<SelectItem> listaAtividadeSelect) {
		this.listaAtividadeSelect = listaAtividadeSelect;
	}

}
