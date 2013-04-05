package br.com.etraining.negocio.bo.impl.exerciciorealizado;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.DiaExercicioVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.ConsultaListaExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.InsercaoExercicioRealizadoVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RespostaConsultaListaExercicioRealizadoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoDiaExercicio;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicio;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicioRealizado;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntDiaExercicio;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.modelo.entidades.EntExercicioRealizado;
import br.com.etraining.negocio.bo.BOResolver;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.bo.interfaces.IBO;
import br.com.etraining.negocio.conversor.impl.ConversorDiaExercicio;

@Named("InsercaoExercicioRealizadoVO")
public class InsercaoExercicioRealizadoBO
		extends
		AbstractBO<InsercaoExercicioRealizadoVO, RespostaConsultaListaExercicioRealizadoVO> {

	@Inject
	private IDaoDiaExercicio diaExercicioDao;

	@Inject
	private IDaoExercicio exercicioDao;

	@Inject
	private IDaoExercicioRealizado exercicioRealizadoDao;

	@Inject
	private BOResolver boResolver;

	@Inject
	private ConversorDiaExercicio converdorDiaExercicio;

	@SuppressWarnings("unchecked")
	@Override
	protected RespostaConsultaListaExercicioRealizadoVO executarRegrasEspecificas(
			InsercaoExercicioRealizadoVO request) throws ETrainingException {

		DiaExercicioVO diaExercicioVO = request.getDiaExercicio();
		EntDiaExercicio diaExercicio = null;

		if (request.getIdAluno() != null
				&& request.getDiaExercicio().getData() != null) {
			diaExercicio = diaExercicioDao.pesquisar(request.getIdAluno(),
					request.getDiaExercicio().getData());
		}

		if (diaExercicio == null || diaExercicio.getId() == null) {
			// Se nao existir o dia de exercicio, insere-o na base
			diaExercicio = converdorDiaExercicio.fromVO(diaExercicioVO);
			diaExercicio.setAluno(new EntAluno(request.getIdAluno()));
			diaExercicio.setId(null);
			diaExercicioDao.inserir(diaExercicio);
		}

		EntExercicio exercicio = exercicioDao.pesquisar(request
				.getIdExercicio());
		Integer quantidadeAtividade = request.getQuantidadeAtividade();
		Double pontos = Double.parseDouble(Integer.toString(quantidadeAtividade
				* exercicio.getPontosPorAtividade()));

		EntExercicioRealizado exercicioRealizado = new EntExercicioRealizado();
		exercicioRealizado.setDiaExercicio(diaExercicio);
		exercicioRealizado.setExercicio(exercicio);
		exercicioRealizado.setQuantidadeAtividade(quantidadeAtividade);
		exercicioRealizado.setPontos(pontos);
		exercicioRealizadoDao.inserir(exercicioRealizado);

		ConsultaListaExercicioRealizadoVO consulta = new ConsultaListaExercicioRealizadoVO();
		consulta.setData(request.getDiaExercicio().getData());
		consulta.setIdAluno(request.getIdAluno());

		IBO<ConsultaListaExercicioRealizadoVO, RespostaConsultaListaExercicioRealizadoVO> boDelegate = (IBO<ConsultaListaExercicioRealizadoVO, RespostaConsultaListaExercicioRealizadoVO>) boResolver
				.getBOFor(consulta.getClass());
		return boDelegate.executa(consulta);
	}

	public IDaoDiaExercicio getDiaExercicioDao() {
		return diaExercicioDao;
	}

	public void setDiaExercicioDao(IDaoDiaExercicio diaExercicioDao) {
		this.diaExercicioDao = diaExercicioDao;
	}

	public IDaoExercicio getExercicioDao() {
		return exercicioDao;
	}

	public void setExercicioDao(IDaoExercicio exercicioDao) {
		this.exercicioDao = exercicioDao;
	}

	public IDaoExercicioRealizado getExercicioRealizadoDao() {
		return exercicioRealizadoDao;
	}

	public void setExercicioRealizadoDao(
			IDaoExercicioRealizado exercicioRealizadoDao) {
		this.exercicioRealizadoDao = exercicioRealizadoDao;
	}

	public BOResolver getBoResolver() {
		return boResolver;
	}

	public void setBoResolver(BOResolver boResolver) {
		this.boResolver = boResolver;
	}

	public ConversorDiaExercicio getConverdorDiaExercicio() {
		return converdorDiaExercicio;
	}

	public void setConverdorDiaExercicio(
			ConversorDiaExercicio converdorDiaExercicio) {
		this.converdorDiaExercicio = converdorDiaExercicio;
	}

}
