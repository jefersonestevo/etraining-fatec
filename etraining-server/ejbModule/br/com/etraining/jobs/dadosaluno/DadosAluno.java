package br.com.etraining.jobs.dadosaluno;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import br.com.etraining.modelo.entidades.EntAluno;
import br.com.etraining.modelo.entidades.EntDadosCorporais;
import br.com.etraining.modelo.entidades.EntDiaSemana;
import br.com.etraining.modelo.entidades.EntMatricula;
import br.com.etraining.modelo.entidades.dom.Sexo;

public class DadosAluno implements Serializable {

	private static final long serialVersionUID = -1840025307172384236L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

	private EntAluno aluno;
	private EntMatricula matricula;
	private EntDadosCorporais dadosCorporais;

	public DadosAluno(String[] dadosAluno) {
		aluno = new EntAluno();
		aluno.setNome(dadosAluno[EnumPosicionalDadosAluno.NOME.ordinal()]);
		aluno.setTelefone(dadosAluno[EnumPosicionalDadosAluno.TELEFONE
				.ordinal()]);
		aluno.setEmail(dadosAluno[EnumPosicionalDadosAluno.EMAIL.ordinal()]);
		aluno.setDataNascimento(getDataNascimento(dadosAluno[EnumPosicionalDadosAluno.DADA_NASCIMENTO
				.ordinal()]));
		aluno.setSexo(Sexo.getEnumFor(dadosAluno[EnumPosicionalDadosAluno.SEXO
				.ordinal()]));

		matricula = new EntMatricula();
		matricula.setAluno(aluno);
		matricula.setCpf(dadosAluno[EnumPosicionalDadosAluno.CPF.ordinal()]);
		matricula.setRg(dadosAluno[EnumPosicionalDadosAluno.RG.ordinal()]);
		matricula
				.setNumeroMatricula(dadosAluno[EnumPosicionalDadosAluno.MATRICULA
						.ordinal()]);
		matricula
				.setPerfilAcesso(dadosAluno[EnumPosicionalDadosAluno.PERFIL_ACESSO
						.ordinal()]);
		matricula.setSenhaUsuario(dadosAluno[EnumPosicionalDadosAluno.SENHA
				.ordinal()]);
		matricula.setListaDiasTreinamento(new ArrayList<EntDiaSemana>());
		for (String diaTrein : dadosAluno[EnumPosicionalDadosAluno.DIAS_TREINAMENTO
				.ordinal()].split(",")) {
			EntDiaSemana diaSemana = new EntDiaSemana(Long.parseLong(diaTrein));
			matricula.getListaDiasTreinamento().add(diaSemana);
		}

		dadosCorporais = new EntDadosCorporais();
		dadosCorporais.setAluno(aluno);
		dadosCorporais
				.setAltura(parseDouble(dadosAluno[EnumPosicionalDadosAluno.ALTURA
						.ordinal()]));
		dadosCorporais
				.setPeso(parseDouble(dadosAluno[EnumPosicionalDadosAluno.PESO
						.ordinal()]));
		dadosCorporais.setBatimentosPorMinuto(Integer
				.parseInt(dadosAluno[EnumPosicionalDadosAluno.BATIMENTO_MINUTO
						.ordinal()]));
		dadosCorporais
				.setIndiceGorduraCorporal(parseDouble(dadosAluno[EnumPosicionalDadosAluno.GORDURA_CORPORAL
						.ordinal()]));
		dadosCorporais
				.setPressaoArterialMaxima(parseDouble(dadosAluno[EnumPosicionalDadosAluno.PRESSAO_MAXIMA
						.ordinal()]));
		dadosCorporais
				.setPressaoArterialMinima(parseDouble(dadosAluno[EnumPosicionalDadosAluno.PRESSAO_MINIMA
						.ordinal()]));
		dadosCorporais
				.setTempoEsteira(parseDouble(dadosAluno[EnumPosicionalDadosAluno.TEMPO_ESTEIRA
						.ordinal()]));

	}

	private Double parseDouble(String dados) {
		if (dados == null)
			return null;
		dados = dados.replaceAll(",", ".");
		return Double.parseDouble(dados);
	}

	private Date getDataNascimento(String data) {
		if (StringUtils.isNotBlank(data)) {
			try {
				return sdf.parse(data);
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

	public EntAluno getAluno() {
		return aluno;
	}

	public void setAluno(EntAluno aluno) {
		this.aluno = aluno;
	}

	public EntMatricula getMatricula() {
		return matricula;
	}

	public void setMatricula(EntMatricula matricula) {
		this.matricula = matricula;
	}

	public EntDadosCorporais getDadosCorporais() {
		return dadosCorporais;
	}

	public void setDadosCorporais(EntDadosCorporais dadosCorporais) {
		this.dadosCorporais = dadosCorporais;
	}

}
