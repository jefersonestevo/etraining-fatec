package br.com.etraining.web.managedbeans.aluno;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.aluno.ConsultaListaAlunoVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaListaAlunoVO;
import br.com.etraining.client.vo.impl.entidades.AlunoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.impl.entidades.DadosCorporaisVO;
import br.com.etraining.client.vo.impl.entidades.MatriculaVO;
import br.com.etraining.web.fachada.ITratadorNegocioService;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@SessionScoped
public class AlunoController extends EtrainingManagedBean {

	private static final long serialVersionUID = 6811465607206792357L;

	@Inject
	private ITratadorNegocioService service;

	private ConsultaListaAlunoVO consulta = new ConsultaListaAlunoVO();
	private RespostaConsultaListaAlunoVO respostaLista;
	private AlunoVO aluno;

	public String irParaTelaPesquisa() {
		pesquisar();
		return "/pages/aluno/pesquisaAluno.xhtml";
	}

	public void pesquisar() {
		// TODO - Consertar EJB Web
		respostaLista = new RespostaConsultaListaAlunoVO();
		AlunoSimplesVO aluno = new AlunoSimplesVO();
		aluno.setNome("Teste");
		aluno.setMatricula("1209130");
		aluno.setRg("12313213");
		aluno.setCpf("12312321312");
		respostaLista.getListaAlunos().add(aluno);
	}

	public String irParaTelaEdicao() {
		aluno = new AlunoVO();
		aluno.setMatricula(new MatriculaVO());
		aluno.setDadosCorporais(new DadosCorporaisVO());

		// TODO - Consertar EJB
		Long id = null;
		return irParaTelaEdicao(id);
	}

	protected String irParaTelaEdicao(Long id) {

		return "/pages/aluno/editarAluno.xhtml";
	}

	public String alterar() {

		System.out.println("ALTERANDO");
		// TODO - Consertar EJB
		Long id = null;
		return irParaTelaEdicao(id);
	}

	public ConsultaListaAlunoVO getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaListaAlunoVO consulta) {
		this.consulta = consulta;
	}

	public ITratadorNegocioService getService() {
		return service;
	}

	public void setService(ITratadorNegocioService service) {
		this.service = service;
	}

	public RespostaConsultaListaAlunoVO getRespostaLista() {
		return respostaLista;
	}

	public void setRespostaLista(RespostaConsultaListaAlunoVO respostaLista) {
		this.respostaLista = respostaLista;
	}

	public AlunoVO getAluno() {
		return aluno;
	}

	public void setAluno(AlunoVO aluno) {
		this.aluno = aluno;
	}

}
