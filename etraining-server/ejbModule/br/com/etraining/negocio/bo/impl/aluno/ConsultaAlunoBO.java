package br.com.etraining.negocio.bo.impl.aluno;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.aluno.ConsultaAlunoVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaAlunoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorAluno;

@Named("ConsultaAlunoVO")
public class ConsultaAlunoBO extends
		AbstractBO<ConsultaAlunoVO, RespostaConsultaAlunoVO> {

	@Inject
	private IDaoAluno daoAluno;

	@Inject
	private ConversorAluno conversorAluno;

	@Override
	protected RespostaConsultaAlunoVO executarRegrasEspecificas(
			ConsultaAlunoVO request) throws ETrainingException {

		EntAluno aluno = daoAluno.pesquisar(request.getIdAluno());

		RespostaConsultaAlunoVO resposta = new RespostaConsultaAlunoVO();
		resposta.setAluno(conversorAluno.toVO(aluno));
		return resposta;
	}

	public IDaoAluno getDaoAluno() {
		return daoAluno;
	}

	public void setDaoAluno(IDaoAluno daoAluno) {
		this.daoAluno = daoAluno;
	}

	public ConversorAluno getConversorAluno() {
		return conversorAluno;
	}

	public void setConversorAluno(ConversorAluno conversorAluno) {
		this.conversorAluno = conversorAluno;
	}

}
