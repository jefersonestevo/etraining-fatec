package br.com.etraining.web.managedbean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.web.fachada.impl.TratadorNegocioService;

@Named
@SessionScoped
public class DiaExercicioBean extends EtrainingManagedBean {

	private static final long serialVersionUID = -3793222987299513289L;

	@Inject
	private TratadorNegocioService service;

	public String sair() {
		return "";
	}

	public void selecionarDia() {

	}

	public String voltarTelaDiaExercicio() {
		return "";
	}

	public String irParaTelaInserirExercicio() {
		return "";
	}

	public String inserirExercicio() {
		return "";
	}

	public String removerExercicio() {
		return "";
	}

}
