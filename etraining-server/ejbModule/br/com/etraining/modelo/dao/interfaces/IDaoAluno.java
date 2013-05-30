package br.com.etraining.modelo.dao.interfaces;

import java.util.List;

import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.def.interfaces.IDaoCRUD;
import br.com.etraining.modelo.entidades.EntAluno;

public interface IDaoAluno extends IDaoCRUD<EntAluno> {

	public EntAluno pesquisarAlunoPorMatriculaSenha(String matricula,
			String senha) throws ETrainingException;

	public List<EntAluno> pesquisarPorMatriculaNome(String matricula,
			String nome, Long idPerfilAcesso) throws ETrainingException;

	public EntAluno pesquisarPorMatricula(String matricula)
			throws ETrainingException;

}
