package br.com.etraining.negocio.bo.impl.aluno;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.aluno.RespostaTrocaSenhaVO;
import br.com.etraining.client.vo.impl.aluno.TrocaSenhaVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingBusinessException;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoMatricula;
import br.com.etraining.modelo.entidades.EntMatricula;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;

@Named("TrocaSenhaVO")
public class TrocaSenhaBO extends
		AbstractBO<TrocaSenhaVO, RespostaTrocaSenhaVO> {

	@Inject
	private IDaoMatricula matriculaDao;

	@Override
	protected RespostaTrocaSenhaVO executarRegrasEspecificas(
			TrocaSenhaVO request) throws ETrainingException {

		EntMatricula matricula = matriculaDao.pesquisarPorMatricula(request
				.getMatricula());

		if (matricula == null) {
			throw new ETrainingBusinessException(
					CodigoExcecao.TROCA_SENHA_MATRICULA_INEXISTENTE);
		}

		if (!request.getSenhaNova().equals(request.getSenhaNova2())) {
			throw new ETrainingBusinessException(
					CodigoExcecao.TROCA_SENHA_SENHAS_INCONSISTENTES);
		}

		if (!request.getSenhaAntiga().equals(matricula.getSenhaUsuario())) {
			throw new ETrainingBusinessException(
					CodigoExcecao.TROCA_SENHA_SENHA_INFORMADA_DIFERENTE_ATUAL);
		}

		matricula.setSenhaUsuario(request.getSenhaNova());
		matriculaDao.alterar(matricula);

		RespostaTrocaSenhaVO resposta = new RespostaTrocaSenhaVO();
		return resposta;
	}

	public IDaoMatricula getMatriculaDao() {
		return matriculaDao;
	}

	public void setMatriculaDao(IDaoMatricula matriculaDao) {
		this.matriculaDao = matriculaDao;
	}

}