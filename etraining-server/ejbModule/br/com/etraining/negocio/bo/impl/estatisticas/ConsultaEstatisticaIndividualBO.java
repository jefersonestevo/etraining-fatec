package br.com.etraining.negocio.bo.impl.estatisticas;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.impl.entidades.PontoGraficoVO;
import br.com.etraining.client.vo.impl.relatorios.geral.ConsultaEstatisticaIndividualVO;
import br.com.etraining.client.vo.impl.relatorios.geral.RespostaConsultaEstatisticaVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.bo.transformer.GraficoEstatisticaTransformer;
import br.com.etraining.negocio.conversor.impl.ConversorAluno;

@Named("ConsultaEstatisticaIndividualVO")
public class ConsultaEstatisticaIndividualBO
		extends
		AbstractBO<ConsultaEstatisticaIndividualVO, RespostaConsultaEstatisticaVO> {

	@Inject
	private GraficoEstatisticaTransformer graficoTransformer;

	@Inject
	private IDaoAluno alunoDao;

	@Inject
	private ConversorAluno conversorAluno;

	@Override
	protected RespostaConsultaEstatisticaVO executarRegrasEspecificas(
			ConsultaEstatisticaIndividualVO request) throws ETrainingException {

		EntAluno alunoEnt = alunoDao.pesquisar(request.getIdAluno());
		AlunoVO aluno = conversorAluno.toVO(alunoEnt);

		List<PontoGraficoVO> pontosReais = graficoTransformer.getPontosReais(
				request.getDataInicio(), request.getDataFim(), null,
				request.getIdAluno());
		List<PontoGraficoVO> pontosPropostos = graficoTransformer
				.getPontosPropostos(request.getDataInicio(),
						request.getDataFim(), null, request.getIdAluno());

		RespostaConsultaEstatisticaVO resposta = new RespostaConsultaEstatisticaVO();
		resposta.setAluno(aluno);
		resposta.setListaPontosReais(pontosReais);
		resposta.setListaPontosPropostos(pontosPropostos);
		return resposta;
	}

	public GraficoEstatisticaTransformer getGraficoTransformer() {
		return graficoTransformer;
	}

	public void setGraficoTransformer(
			GraficoEstatisticaTransformer graficoTransformer) {
		this.graficoTransformer = graficoTransformer;
	}

	public IDaoAluno getAlunoDao() {
		return alunoDao;
	}

	public void setAlunoDao(IDaoAluno alunoDao) {
		this.alunoDao = alunoDao;
	}

	public ConversorAluno getConversorAluno() {
		return conversorAluno;
	}

	public void setConversorAluno(ConversorAluno conversorAluno) {
		this.conversorAluno = conversorAluno;
	}

}
