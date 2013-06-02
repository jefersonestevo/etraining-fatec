package br.com.etraining.web.managedbeans.aluno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.dom.PerfilAcesso;
import br.com.etraining.client.dom.Sexo;
import br.com.etraining.client.vo.impl.aluno.AlteraAlunoVO;
import br.com.etraining.client.vo.impl.aluno.ConsultaAlunoVO;
import br.com.etraining.client.vo.impl.aluno.ConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.aluno.InsereAlunoVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaAlunoVO;
import br.com.etraining.client.vo.impl.aluno.RespostaConsultaListaAlunoSimplesVO;
import br.com.etraining.client.vo.impl.aluno.TrocaSenhaVO;
import br.com.etraining.client.vo.impl.entidades.AlunoVO;
import br.com.etraining.client.vo.impl.entidades.DadosCorporaisVO;
import br.com.etraining.client.vo.impl.entidades.MatriculaVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.web.exceptions.ViewException;
import br.com.etraining.web.fachada.ITratadorNegocioService;
import br.com.etraining.web.managedbeans.EtrainingManagedBean;
import br.com.etraining.web.utils.comparador.ComparadorAlunoSimplesAlfabetico;

@Named
@SessionScoped
public class AlunoController extends EtrainingManagedBean {

	private static final long serialVersionUID = 6811465607206792357L;

	@Inject
	private ITratadorNegocioService service;

	private ConsultaListaAlunoSimplesVO consulta = new ConsultaListaAlunoSimplesVO();
	private RespostaConsultaListaAlunoSimplesVO respostaLista;
	private RespostaConsultaAlunoVO resposta = new RespostaConsultaAlunoVO();
	private TrocaSenhaVO trocaSenha = new TrocaSenhaVO();

	private static final Long ID_PERFIL_INVALIDO = -1l;
	private List<SelectItem> listaPerfilAcesso = new ArrayList<SelectItem>();
	private List<SelectItem> listaPerfilAcessoAlteracao = new ArrayList<SelectItem>();

	private Integer sexoAluno;
	private List<SelectItem> listaSexo = new ArrayList<SelectItem>();

	@PostConstruct
	public void inicializar() {
		this.listaSexo = new ArrayList<SelectItem>();
		this.listaSexo.add(new SelectItem(Sexo.M.getId(),
				getMessage("Sexo_Masculino")));
		this.listaSexo.add(new SelectItem(Sexo.F.getId(),
				getMessage("Sexo_Feminino")));
	}

	public String irParaTelaPesquisa() {
		consulta = new ConsultaListaAlunoSimplesVO();
		consulta.setPerfilAcesso(PerfilAcesso.ALUNO.getId());
		respostaLista = new RespostaConsultaListaAlunoSimplesVO();
		resposta = new RespostaConsultaAlunoVO();

		limparForm();
		preencherListaPerfilAcesso();
		return "/pages/aluno/pesquisaAluno.xhtml";
	}

	public void pesquisar() {
		respostaLista = new RespostaConsultaListaAlunoSimplesVO();
		RespostaConsultaListaAlunoSimplesVO resp = null;

		try {
			if (ID_PERFIL_INVALIDO.equals(consulta.getPerfilAcesso())) {
				consulta.setPerfilAcesso(null);
			}

			resp = (RespostaConsultaListaAlunoSimplesVO) service
					.executa(consulta);

			Collections.sort(resp.getListaAlunos(),
					new ComparadorAlunoSimplesAlfabetico());

			respostaLista.getListaAlunos().addAll(resp.getListaAlunos());
		} catch (ViewException e) {
			addExceptionMessage(e);
		}
	}

	public String irParaTelaEdicao() {
		Long id = getLongParameter("id");
		try {
			ConsultaAlunoVO consulta = new ConsultaAlunoVO();
			consulta.setIdAluno(id);
			resposta = (RespostaConsultaAlunoVO) service.executa(consulta);
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}
		return "/pages/aluno/editarAluno.xhtml";
	}

	public String alterar() {

		try {
			AlteraAlunoVO altera = new AlteraAlunoVO();
			altera.setAluno(resposta.getAluno());
			resposta = (RespostaConsultaAlunoVO) service.executa(altera);

			if (resposta == null || resposta.getAluno() == null) {
				throw new ViewException(CodigoExcecao.ERRO_DESCONHECIDO);
			}

			showSuccessMessage();
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}

		return "/pages/aluno/editarAluno.xhtml";
	}

	public String irParaTelaInsercao() {
		resposta = new RespostaConsultaAlunoVO();
		AlunoVO aluno = new AlunoVO();
		resposta.setAluno(aluno);
		aluno.setMatricula(new MatriculaVO());
		aluno.setDadosCorporais(new DadosCorporaisVO());
		this.sexoAluno = Sexo.M.getId();
		return "/pages/aluno/inserirAluno.xhtml";
	}

	public String inserir() {
		try {
			InsereAlunoVO insercao = new InsereAlunoVO();
			AlunoVO aluno = resposta.getAluno();

			if (Sexo.M.getId().equals(this.sexoAluno)) {
				aluno.setSexo(Sexo.M);
			} else {
				aluno.setSexo(Sexo.F);
			}

			insercao.setAluno(aluno);
			resposta = (RespostaConsultaAlunoVO) service.executa(insercao);

			if (resposta == null || resposta.getAluno() == null) {
				throw new ViewException(CodigoExcecao.ERRO_DESCONHECIDO);
			}

			showSuccessMessage();
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}

		return "/pages/aluno/editarAluno.xhtml";
	}

	public String trocarSenha() {
		try {
			boolean hasError = false;
			if (isCampoMenorPermitido(trocaSenha.getSenhaAntiga(), 6,
					"Senha_Menor_Permitido")) {
				hasError = true;
			}
			if (isCampoMenorPermitido(trocaSenha.getSenhaNova(), 6,
					"Senha_Nova_Menor_Permitido")) {
				hasError = true;
			}
			if (isCampoMenorPermitido(trocaSenha.getSenhaNova2(), 6,
					"Senha_Nova_2_Menor_Permitido")) {
				hasError = true;
			}
			if (hasError)
				return null;

			service.executa(trocaSenha);
			showSuccessMessage();
			trocaSenha = new TrocaSenhaVO();
		} catch (ViewException e) {
			addExceptionMessage(e);
			return null;
		}
		return "login.xhtml";
	}

	private boolean isCampoMenorPermitido(String campo, Integer permitido,
			String message) {
		if (campo.length() < permitido) {
			addErrorMessage(getMessage(message));
			return true;
		}
		return false;
	}

	private void preencherListaPerfilAcesso() {
		listaPerfilAcesso = new ArrayList<SelectItem>();
		listaPerfilAcessoAlteracao = new ArrayList<SelectItem>();

		listaPerfilAcesso.add(new SelectItem(ID_PERFIL_INVALIDO, ""));
		for (PerfilAcesso perfil : PerfilAcesso.values()) {
			listaPerfilAcesso.add(new SelectItem(perfil.getId(), perfil
					.getNome()));
			listaPerfilAcessoAlteracao.add(new SelectItem(perfil.getId(),
					perfil.getNome()));
		}
	}

	public ConsultaListaAlunoSimplesVO getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaListaAlunoSimplesVO consulta) {
		this.consulta = consulta;
	}

	public ITratadorNegocioService getService() {
		return service;
	}

	public void setService(ITratadorNegocioService service) {
		this.service = service;
	}

	public RespostaConsultaListaAlunoSimplesVO getRespostaLista() {
		return respostaLista;
	}

	public void setRespostaLista(
			RespostaConsultaListaAlunoSimplesVO respostaLista) {
		this.respostaLista = respostaLista;
	}

	public RespostaConsultaAlunoVO getResposta() {
		return resposta;
	}

	public void setResposta(RespostaConsultaAlunoVO resposta) {
		this.resposta = resposta;
	}

	public List<SelectItem> getListaPerfilAcesso() {
		return listaPerfilAcesso;
	}

	public void setListaPerfilAcesso(List<SelectItem> listaPerfilAcesso) {
		this.listaPerfilAcesso = listaPerfilAcesso;
	}

	public List<SelectItem> getListaPerfilAcessoAlteracao() {
		return listaPerfilAcessoAlteracao;
	}

	public void setListaPerfilAcessoAlteracao(
			List<SelectItem> listaPerfilAcessoAlteracao) {
		this.listaPerfilAcessoAlteracao = listaPerfilAcessoAlteracao;
	}

	public Integer getSexoAluno() {
		return sexoAluno;
	}

	public void setSexoAluno(Integer sexoAluno) {
		this.sexoAluno = sexoAluno;
	}

	public List<SelectItem> getListaSexo() {
		return listaSexo;
	}

	public void setListaSexo(List<SelectItem> listaSexo) {
		this.listaSexo = listaSexo;
	}

	public TrocaSenhaVO getTrocaSenha() {
		return trocaSenha;
	}

	public void setTrocaSenha(TrocaSenhaVO trocaSenha) {
		this.trocaSenha = trocaSenha;
	}

}
