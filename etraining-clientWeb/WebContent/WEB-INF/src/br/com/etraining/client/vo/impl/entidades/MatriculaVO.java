package br.com.etraining.client.vo.impl.entidades;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.interfaces.IVO;

public class MatriculaVO implements IVO {

	private static final long serialVersionUID = 4542691961735055463L;

	private Long id;
	private String numeroMatricula;
	private String rg;
	private String cpf;
	private List<Integer> listaDiasSemana = new ArrayList<Integer>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Integer> getListaDiasSemana() {
		return listaDiasSemana;
	}

	public void setListaDiasSemana(List<Integer> listaDiasSemana) {
		this.listaDiasSemana = listaDiasSemana;
	}
}
