package br.com.etraining.negocio.bo.impl.aluno;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import br.com.etraining.client.vo.impl.aluno.AlteraAlunoVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaAlunoVO;
import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoAluno;
import br.com.etraining.modelo.dao.interfaces.IDaoDadosCorporais;
import br.com.etraining.modelo.dao.interfaces.IDaoMatricula;
import br.com.etraining.modelo.dao.interfaces.IDaoProgramaTreinamento;
import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntDadosCorporais;
import br.com.etraining.modelo.entidades.EntMatricula;
import br.com.etraining.modelo.entidades.EntPerfilAcesso;
import br.com.etraining.modelo.entidades.EntProgramaTreinamento;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorAluno;
import br.com.etraining.negocio.gerador.ICalculadoraDePontos;

@Named("AlteraAlunoVO")
public class AlteraAlunoBO extends
		AbstractBO<AlteraAlunoVO, RespostaConsultaAlunoVO> {

	@Inject
	private IDaoAluno alunoDao;

	@Inject
	private IDaoDadosCorporais dadosCorporaisDao;

	@Inject
	private IDaoMatricula matriculaDao;

	@Inject
	private IDaoProgramaTreinamento programaTreinamentoDao;

	@Inject
	private ConversorAluno conversorAluno;

	@Inject
	private ICalculadoraDePontos calculadoraPontos;

	@Override
	protected RespostaConsultaAlunoVO executarRegrasEspecificas(
			AlteraAlunoVO request) throws ETrainingException {

		AlunoVO alunoVO = request.getAluno();
		EntAluno aluno = conversorAluno.fromVO(alunoVO);

		EntProgramaTreinamento programaTreinamento = programaTreinamentoDao
				.pesquisarAtualAprovadoPorIdAluno(aluno.getId());
		Integer novaPontuacao = calculadoraPontos.calcularNovaPontuacaoAluno(
				aluno, programaTreinamento);
		aluno.setPontuacaoSemanalAluno(novaPontuacao);

		EntMatricula matricula = matriculaDao.pesquisar(aluno.getMatricula()
				.getId());
		matricula.setCpf(aluno.getMatricula().getCpf());
		matricula.setRg(aluno.getMatricula().getRg());
		matricula.setListaDiasTreinamento(aluno.getMatricula()
				.getListaDiasTreinamento());

		if (request.getAluno().getMatricula() != null
				&& CollectionUtils.isNotEmpty(request.getAluno().getMatricula()
						.getListaPerfilAcesso())) {
			matricula.getListaPerfilAcesso().clear();
			for (String idPerfil : request.getAluno().getMatricula()
					.getListaPerfilAcesso()) {
				EntPerfilAcesso perfil = new EntPerfilAcesso();
				perfil.setId(Long.valueOf(idPerfil));
				matricula.getListaPerfilAcesso().add(perfil);
			}
		}

		matriculaDao.alterar(matricula);
		aluno.setMatricula(matricula);

		EntDadosCorporais dadosCorporais = dadosCorporaisDao.pesquisar(aluno
				.getDadosCorporais().getId());
		dadosCorporais.setPeso(aluno.getDadosCorporais().getPeso());
		dadosCorporais.setAltura(aluno.getDadosCorporais().getAltura());
		dadosCorporais.setIndiceGorduraCorporal(aluno.getDadosCorporais()
				.getIndiceGorduraCorporal());
		dadosCorporais.setBatimentosPorMinuto(aluno.getDadosCorporais()
				.getBatimentosPorMinuto());
		dadosCorporais.setTempoEsteira(aluno.getDadosCorporais()
				.getTempoEsteira());

		dadosCorporaisDao.alterar(dadosCorporais);
		aluno.setDadosCorporais(dadosCorporais);

		aluno = alunoDao.alterar(aluno);

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

	public IDaoProgramaTreinamento getProgramaTreinamentoDao() {
		return programaTreinamentoDao;
	}

	public void setProgramaTreinamentoDao(
			IDaoProgramaTreinamento programaTreinamentoDao) {
		this.programaTreinamentoDao = programaTreinamentoDao;
	}

	public ICalculadoraDePontos getCalculadoraPontos() {
		return calculadoraPontos;
	}

	public void setCalculadoraPontos(ICalculadoraDePontos calculadoraPontos) {
		this.calculadoraPontos = calculadoraPontos;
	}

}
