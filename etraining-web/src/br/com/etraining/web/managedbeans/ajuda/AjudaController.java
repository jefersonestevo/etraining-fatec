package br.com.etraining.web.managedbeans.ajuda;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@SessionScoped
public class AjudaController extends EtrainingManagedBean {

	private static final long serialVersionUID = 1953667635206607234L;

	private TreeNode mapaSite;

	public void preencherMapaSite() {
		mapaSite = new DefaultTreeNode(getMessage("Sistema_Nome"), null);
		mapaSite.setExpanded(true);
		// HOME
		new DefaultTreeNode("home", getMessage("Menu_Home"), mapaSite)
				.setExpanded(true);

		// ADM
		TreeNode nodeAdministracao = new DefaultTreeNode(
				getMessage("Menu_Administracao"), mapaSite);
		nodeAdministracao.setExpanded(true);

		// ADM > Aluno
		TreeNode nodeDadosUsuario = new DefaultTreeNode(
				getMessage("Menu_Dados_Usuario"), nodeAdministracao);
		nodeDadosUsuario.setExpanded(true);
		TreeNode nodeConsultarAluno = new DefaultTreeNode("pesquisa",
				getMessage("Header_Pesquisa_Aluno"), nodeDadosUsuario);
		nodeConsultarAluno.setExpanded(true);
		new DefaultTreeNode("editar", getMessage("Header_Edicao_Aluno"),
				nodeConsultarAluno).setExpanded(true);

		// ADM > Relatórios
		TreeNode nodeRelatorio = new DefaultTreeNode(
				getMessage("Menu_Relatorios"), nodeAdministracao);
		nodeRelatorio.setExpanded(true);
		new DefaultTreeNode("pesquisa", getMessage("Header_Relatorio_Geral"),
				nodeRelatorio).setExpanded(true);
		new DefaultTreeNode("pesquisa",
				getMessage("Header_Relatorio_Individual"), nodeRelatorio)
				.setExpanded(true);

		// TREINAMENTO
		TreeNode nodeTreinamento = new DefaultTreeNode(
				getMessage("Menu_Treinamento"), mapaSite);
		nodeTreinamento.setExpanded(true);

		new DefaultTreeNode("pesquisa", getMessage("Menu_Lista_Exercicios"),
				nodeTreinamento).setExpanded(true);

		// TREINAMENTO >> Programa de Treinamento
		TreeNode nodeProgramaTreinamento = new DefaultTreeNode(
				getMessage("Menu_Rotina_Treinamento"), nodeTreinamento);
		nodeProgramaTreinamento.setExpanded(true);

		TreeNode nodePesquisaProgramaTreinamento = new DefaultTreeNode(
				"pesquisa", getMessage("Header_Programa_Treinamento"),
				nodeProgramaTreinamento);
		nodePesquisaProgramaTreinamento.setExpanded(true);

		new DefaultTreeNode("editar",
				getMessage("Header_Programa_Treinamento_Edicao"),
				nodePesquisaProgramaTreinamento).setExpanded(true);

		// AJUDA
		TreeNode nodeAjuda = new DefaultTreeNode(getMessage("Menu_Ajuda"),
				mapaSite);
		nodeAjuda.setExpanded(true);
		new DefaultTreeNode("ajuda", getMessage("Menu_Mapa_Site"), nodeAjuda)
				.setExpanded(true);
	}

	public String irParaTelaMapaSite() {
		limparForm();
		preencherMapaSite();
		return "/pages/ajuda/mapaSite.xhtml";
	}

	public TreeNode getMapaSite() {
		return mapaSite;
	}

	public void setMapaSite(TreeNode mapaSite) {
		this.mapaSite = mapaSite;
	}

}
