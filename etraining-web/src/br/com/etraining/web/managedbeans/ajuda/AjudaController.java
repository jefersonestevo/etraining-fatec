package br.com.etraining.web.managedbeans.ajuda;

import javax.inject.Named;
import javax.inject.Singleton;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.etraining.web.managedbeans.EtrainingManagedBean;

@Named
@Singleton
public class AjudaController extends EtrainingManagedBean {

	private static final long serialVersionUID = 1953667635206607234L;

	private TreeNode mapaSite;

	private void preencherMapaSite() {
		mapaSite = new DefaultTreeNode(getMessage("Sistema_Nome"), null);

		// HOME
		new DefaultTreeNode("home", getMessage("Menu_Home"), mapaSite);

		// ADM
		TreeNode nodeAdministracao = new DefaultTreeNode(
				getMessage("Menu_Administracao"), mapaSite);

		// ADM > Aluno
		TreeNode nodeDadosUsuario = new DefaultTreeNode(
				getMessage("Menu_Dados_Usuario"), nodeAdministracao);
		TreeNode nodeConsultarAluno = new DefaultTreeNode("pesquisa",
				getMessage("Header_Pesquisa_Aluno"), nodeDadosUsuario);
		new DefaultTreeNode("editar", getMessage("Header_Edicao_Aluno"),
				nodeConsultarAluno);

		// ADM > Relatórios
		TreeNode nodeRelatorio = new DefaultTreeNode(
				getMessage("Menu_Relatorios"), nodeAdministracao);
		new DefaultTreeNode("pesquisa", getMessage("Header_Relatorio_Geral"),
				nodeRelatorio);
		new DefaultTreeNode("pesquisa",
				getMessage("Header_Relatorio_Individual"), nodeRelatorio);

		// TREINAMENTO
		TreeNode nodeTreinamento = new DefaultTreeNode(
				getMessage("Menu_Treinamento"), mapaSite);

		new DefaultTreeNode("pesquisa", getMessage("Menu_Lista_Exercicios"),
				nodeTreinamento);

		// TREINAMENTO >> Programa de Treinamento
		TreeNode nodeProgramaTreinamento = new DefaultTreeNode(
				getMessage("Menu_Rotina_Treinamento"), nodeTreinamento);
		TreeNode nodePesquisaProgramaTreinamento = new DefaultTreeNode(
				"pesquisa", getMessage("Header_Programa_Treinamento"),
				nodeProgramaTreinamento);
		new DefaultTreeNode("editar",
				getMessage("Header_Programa_Treinamento_Edicao"),
				nodePesquisaProgramaTreinamento);

		// AJUDA
		TreeNode nodeAjuda = new DefaultTreeNode(getMessage("Menu_Ajuda"),
				mapaSite);
		new DefaultTreeNode("ajuda", getMessage("Menu_Ajuda"), nodeAjuda);
		new DefaultTreeNode("ajuda", getMessage("Menu_Mapa_Site"), nodeAjuda);

	}

	public TreeNode getMapaSite() {
		if (mapaSite == null) {
			preencherMapaSite();
		}
		return mapaSite;
	}

	public void setMapaSite(TreeNode mapaSite) {
		System.out.println(mapaSite);
		this.mapaSite = mapaSite;
	}

}
