package br.com.etraining.negocio.bo.impl.aluno;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.aluno.ConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaListaAlunoSimplesVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorAlunoSimples;

@Named("ConsultaListaAlunoSimplesVO")
public class ConsultaListaAlunoSimplesBO extends
		AbstractBO<ConsultaListaAlunoSimplesVO, RespostaConsultaListaAlunoSimplesVO> {

	@Inject
	private IDaoAluno daoAluno;

	@Inject
	private ConversorAlunoSimples conversorAluno;

	@Override
	protected RespostaConsultaListaAlunoSimplesVO executarRegrasEspecificas(
			ConsultaListaAlunoSimplesVO request) throws ETrainingException {

		List<EntAluno> listaAlunos = daoAluno.pesquisarPorMatriculaNome(
				request.getMatricula(), request.getNome());

		RespostaConsultaListaAlunoSimplesVO resposta = new RespostaConsultaListaAlunoSimplesVO();

		for (EntAluno aluno : listaAlunos) {
			resposta.getListaAlunos().add(conversorAluno.toVO(aluno));
		}

		return resposta;
	}

	public IDaoAluno getDaoAluno() {
		return daoAluno;
	}

	public void setDaoAluno(IDaoAluno daoAluno) {
		this.daoAluno = daoAluno;
	}

	public ConversorAlunoSimples getConversorAluno() {
		return conversorAluno;
	}

	public void setConversorAluno(ConversorAlunoSimples conversorAluno) {
		this.conversorAluno = conversorAluno;
	}

}
