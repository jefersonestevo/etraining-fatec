package br.com.etraining.android.view.interfaces;

public interface TreinamentoStrategy {

	public int getContentView();

	public void inicializarCampos();

	public void botaoPrincipal();

	public void botaoSecundario();

	public void botaoVoltar();

	public void onItemListaPress(int position);

	public boolean onItemListaLongPress(int position);

}
