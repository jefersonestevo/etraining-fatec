package br.com.etraining.negocio.bo.impl.aluno;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.aluno.InsereAlunoVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaAlunoVO;
import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingBusinessException;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.dao.interfaces.IDaoDadosCorporais;
import br.com.etraining.modelo.dao.interfaces.IDaoMatricula;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntDadosCorporais;
import br.com.etraining.modelo.entidades.EntMatricula;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorAluno;
import br.com.etraining.negocio.conversor.impl.ConversorDadosCorporais;
import br.com.etraining.negocio.conversor.impl.ConversorMatricula;
import br.com.etraining.negocio.gerador.fake.GeradorProgramaTreinamentoRandomico;

@Named("InsereAlunoVO")
public class InsereAlunoBO extends
		AbstractBO<InsereAlunoVO, RespostaConsultaAlunoVO> {

	@Inject
	private IDaoAluno alunoDao;

	@Inject
	private IDaoDadosCorporais dadosCorporaisDao;

	@Inject
	private IDaoMatricula matriculaDao;

	@Inject
	private ConversorAluno conversorAluno;

	@Inject
	private ConversorMatricula conversorMatricula;

	@Inject
	private ConversorDadosCorporais conversorDadosCorporais;

	@Inject
	private GeradorProgramaTreinamentoRandomico geradorProgramaTreinamento;

	@Override
	protected RespostaConsultaAlunoVO executarRegrasEspecificas(
			InsereAlunoVO request) throws ETrainingException {

		if (matriculaDao.existeMatricula(request.getAluno().getMatricula()
				.getNumeroMatricula())) {
			throw new ETrainingBusinessException(
					CodigoExcecao.MATRICULA_EXISTENTE);
		}

		AlunoVO alunoVO = request.getAluno();
		EntAluno aluno = conversorAluno.fromVO(alunoVO);
		aluno.setPontuacaoSemanalAluno(400);
		aluno.setMatricula(null);
		aluno.setDadosCorporais(null);
		alunoDao.inserir(aluno);

		EntDadosCorporais dadosCorporais = conversorDadosCorporais
				.fromVO(request.getAluno().getDadosCorporais());
		dadosCorporais.setAluno(aluno);
		dadosCorporaisDao.inserir(dadosCorporais);
		aluno.setDadosCorporais(dadosCorporais);

		EntMatricula matricula = conversorMatricula.fromVO(request.getAluno()
				.getMatricula());
		matricula.setSenhaUsuario(request.getAluno().getSenha());
		matricula.setAluno(aluno);
		matriculaDao.inserir(matricula);
		aluno.setMatricula(matricula);

		geradorProgramaTreinamento.gerarProgramaTreinamento(aluno, null);

		alunoVO = conversorAluno.toVO(aluno);

		RespostaConsultaAlunoVO resposta = new RespostaConsultaAlunoVO();
		resposta.setAluno(alunoVO);

		return resposta;
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

	public IDaoDadosCorporais getDadosCorporaisDao() {
		return dadosCorporaisDao;
	}

	public void setDadosCorporaisDao(IDaoDadosCorporais dadosCorporaisDao) {
		this.dadosCorporaisDao = dadosCorporaisDao;
	}

	public IDaoMatricula getMatriculaDao() {
		return matriculaDao;
	}

	public void setMatriculaDao(IDaoMatricula matriculaDao) {
		this.matriculaDao = matriculaDao;
	}

	public ConversorMatricula getConversorMatricula() {
		return conversorMatricula;
	}

	public void setConversorMatricula(ConversorMatricula conversorMatricula) {
		this.conversorMatricula = conversorMatricula;
	}

	public ConversorDadosCorporais getConversorDadosCorporais() {
		return conversorDadosCorporais;
	}

	public void setConversorDadosCorporais(
			ConversorDadosCorporais conversorDadosCorporais) {
		this.conversorDadosCorporais = conversorDadosCorporais;
	}

	public GeradorProgramaTreinamentoRandomico getGeradorProgramaTreinamento() {
		return geradorProgramaTreinamento;
	}

	public void setGeradorProgramaTreinamento(
			GeradorProgramaTreinamentoRandomico geradorProgramaTreinamento) {
		this.geradorProgramaTreinamento = geradorProgramaTreinamento;
	}

}
