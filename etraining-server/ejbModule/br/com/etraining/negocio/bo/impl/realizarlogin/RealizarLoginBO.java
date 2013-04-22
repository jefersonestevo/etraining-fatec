package br.com.etraining.negocio.bo.impl.realizarlogin;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import br.com.etraining.client.dom.PerfilAcesso;
import br.com.etraining.client.vo.impl.realizarlogin.RealizarLoginVO;
import br.com.etraining.client.vo.impl.realizarlogin.RespostaRealizarLoginVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingBusinessException;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntPerfilAcesso;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;

@Named("RealizarLoginVO")
public class RealizarLoginBO extends
		AbstractBO<RealizarLoginVO, RespostaRealizarLoginVO> {

	@Inject
	private IDaoAluno daoAluno;

	@Override
	protected RespostaRealizarLoginVO executarRegrasEspecificas(
			RealizarLoginVO request) throws ETrainingException {

		if (StringUtils.isBlank(request.getNumeroMatricula())) {
			throw new ETrainingBusinessException(
					CodigoExcecao.MATRICULA_NAO_INFORMADA);
		}

		if (StringUtils.isBlank(request.getSenha())) {
			throw new ETrainingBusinessException(
					CodigoExcecao.SENHA_NAO_INFORMADA);
		}

		EntAluno aluno = daoAluno.pesquisarAlunoPorMatriculaSenha(
				request.getNumeroMatricula(), request.getSenha());

		if (aluno == null) {
			throw new ETrainingBusinessException(
					CodigoExcecao.USUARIO_NAO_ENCONTRADO);
		}

		if (CollectionUtils.isNotEmpty(request.getPerfisAceitos())) {
			// Verifica se o aluno possui algum dos perfis aceitos passados como
			// parametro, se estes existirem
			boolean hasPerfil = false;
			outer: for (PerfilAcesso perfilAceito : request.getPerfisAceitos()) {
				for (EntPerfilAcesso perfilAluno : aluno.getMatricula()
						.getListaPerfilAcesso()) {
					if (perfilAceito.getNome().equals(perfilAluno.getNome())) {
						hasPerfil = true;
						break outer;
					}
				}
			}
			if (!hasPerfil)
				throw new ETrainingBusinessException(
						CodigoExcecao.USUARIO_SEM_PERFIL);
		}

		RespostaRealizarLoginVO response = new RespostaRealizarLoginVO();

		if (aluno.getMatricula().getUsuarioAtivo() != null
				&& aluno.getMatricula().getUsuarioAtivo()) {

			response.setIdAluno(aluno.getId());
			response.setNumeroMatricula(aluno.getMatricula()
					.getNumeroMatricula());
			response.setSenha(aluno.getMatricula().getSenhaUsuario());
			response.setNome(aluno.getNome());

			List<String> listaPermissoes = new ArrayList<String>();
			for (EntPerfilAcesso perfilAcesso : aluno.getMatricula()
					.getListaPerfilAcesso()) {
				listaPermissoes.add(perfilAcesso.getNome());
			}
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
