package br.com.etraining.web.managedbeans.programatreinamento;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.aluno.ConsultaListaAlunoVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaListaAlunoVO;
import br.com.etraining.client.vo.impl.entidades.AlunoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioPropostoVO;
import br.com.etraining.client.vo.impl.programatreinamento.RespostaConsultaProgramaTreinamentoVO;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@SessionScoped
public class ProgramaTreinamentoController extends EtrainingManagedBean {

	private static final long serialVersionUID = -6032506735894603834L;

	private RespostaConsultaProgramaTreinamentoVO resposta = new RespostaConsultaProgramaTreinamentoVO();

	private List<AlunoSimplesVO> listaAlunos = new ArrayList<AlunoSimplesVO>();
	private List<AlunoSimplesVO> listaAlunosFiltrados = new ArrayList<AlunoSimplesVO>();
	private AlunoSimplesVO alunoSelecionado = new AlunoSimplesVO();

	private List<SelectItem> listaExercicios = new ArrayList<SelectItem>();
	private ExercicioPropostoVO exercicioProposto = new ExercicioPropostoVO();
	private List<Integer> listaDiasSemana = new ArrayList<Integer>();

	public String irParaTelaPesquisa() {
		alunoSelecionado = new AlunoSimplesVO();
		preencherListaAluno();

		return "/pages/programaTreinamento/programaTreinamentoPesquisa.xhtml";
	}

	public String pesquisar() {
		boolean hasError = false;
		if (alunoSelecionado == null || alunoSelecionado.getId() == null) {
			addErrorMessage(getMessage("Selecao_Aluno_Obrigatoria"));
			hasError = true;
		}
		if (hasError)
			return null;

		preencherListaExercicio();

		return "/pages/programaTreinamento/programaTreinamentoEdicao.xhtml";
	}

	public String alterar() {

		return "/pages/programaTreinamento/programaTreinamentoEdicao.xhtml";
	}

	public void adicionarExercicio() {

	}

	public void removerExercicio() {

	}

	private void preencherListaAluno() {
		this.listaAlunos.clear();
		this.listaAlunosFiltrados.clear();

		new ConsultaListaAlunoVO();
		RespostaConsultaListaAlunoVO resp = new RespostaConsultaListaAlunoVO();
		resp.getListaAlunos();
		// TODO - Fazer EJB Funcionar

		for (int i = 0; i < 20; i++) {
			AlunoSimplesVO aluno = new AlunoSimplesVO();
			aluno.setId(new Long(i));
			aluno.setNome("ALUNO " + i);
			aluno.setMatricula("1010M" + i);
			this.listaAlunos.add(aluno);
		}
		this.listaAlunosFiltrados.addAll(this.listaAlunos);
	}

	private void preencherListaExercicio() {
		// TODO - Fazer o EJB Funcionar
		this.listaExercicios.add(new SelectItem("", ""));
		this.listaExercicios.clear();
		for (int i = 0; i < 10; i++) {
			this.listaExercicios.add(new SelectItem(new Long(i), "EXERCICIO "
					+ i));
		}
	}

	public List<AlunoSimplesVO> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<AlunoSimplesVO> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public AlunoSimplesVO getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(AlunoSimplesVO alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}

	public List<AlunoSimplesVO> getListaAlunosFiltrados() {
		return listaAlunosFiltrados;
	}

	public void setListaAlunosFiltrados(
			List<AlunoSimplesVO> listaAlunosFiltrados) {
		this.listaAlunosFiltrados = listaAlunosFiltrados;
	}

	public List<SelectItem> getListaExercicios() {
		return listaExercicios;
	}

	public void setListaExercicios(List<SelectItem> listaExercicios) {
		this.listaExercicios = listaExercicios;
	}

	public RespostaConsultaProgramaTreinamentoVO getResposta() {
		return resposta;
	}

	public void setResposta(RespostaConsultaProgramaTreinamentoVO resposta) {
		this.resposta = resposta;
	}

	public ExercicioPropostoVO getExercicioProposto() {
		return exercicioProposto;
	}

	public void setExercicioProposto(ExercicioPropostoVO exercicioProposto) {
		this.exercicioProposto = exercicioProposto;
	}

	public List<Integer> getListaDiasSemana() {
		return listaDiasSemana;
	}

	public void setListaDiasSemana(List<Integer> listaDiasSemana) {
		this.listaDiasSemana = listaDiasSemana;
	}

}
