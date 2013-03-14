package br.com.etraining.negocio.bo.impl.realizarlogin;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;

import br.com.etraining.client.vo.impl.realizarlogin.RealizarLoginVO;
import br.com.etraining.client.vo.impl.realizarlogin.RespostaRealizarLoginVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingBusinessException;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;

@Named("RealizarLoginVO")
public class RealizarLoginBO extends
		AbstractBO<RealizarLoginVO, RespostaRealizarLoginVO> {

	@Inject
	private IDaoAluno daoAluno;

	@Override
	protected RespostaRealizarLoginVO executarRegrasEspecificas(
			RealizarLoginVO request) throws ETrainingException {

		if (request.getNumeroMatricula() == null) {
			throw new ETrainingBusinessException(
					CodigoExcecao.MATRICULA_NAO_INFORMADA);
		}

		if (StringUtils.isBlank(request.getSenha())) {
			throw new ETrainingBusinessException(
					CodigoExcecao.SENHA_NAO_INFORMADA);
		}

		EntAluno aluno = daoAluno.pesquisarAlunoPorMatriculaSenha(
				request.getNumeroMatricula(), request.getSenha());

		RespostaRealizarLoginVO response = new RespostaRealizarLoginVO();

		if (aluno == null) {
			throw new ETrainingBusinessException(
					CodigoExcecao.USUARIO_NAO_ENCONTRADO);
		}

		if (aluno.getMatricula().getUsuarioAtivo() != null
				&& aluno.getMatricula().getUsuarioAtivo()) {

			response.setIdAluno(aluno.getId());
			response.setNumeroMatricula(aluno.getMatricula()
					.getNumeroMatricula());

			List<String> listaPermissoes = new ArrayList<String>();
			listaPermissoes.add(aluno.getMatricula().getPerfilAcesso());
			response.setListaPermissoes(listaPermissoes);
		} else {
			throw new ETrainingBusinessException(CodigoExcecao.USUARIO_INATIVO);
		}

		return response;
	}

	public IDaoAluno getDaoAluno() {
		return daoAluno;
	}

	public void setDaoAluno(IDaoAluno daoAluno) {
		this.daoAluno = daoAluno;
	}

}
