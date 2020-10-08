package br.com.zup.estrelas.cidades.pojo;

import java.util.Date;

public class CidadePOJO {
	private String cep;
	private String nome;
	private int numeroHabitantes;
	private boolean capital;
	private String estado;
	private double rendaPerCapita;
	private String dataFuncao;
	
	public CidadePOJO() {
		
	}

	public String getCep() {
		return cep;
	}

	public String getNome() {
		return nome;
	}

	public int getNumeroHabitantes() {
		return numeroHabitantes;
	}

	public boolean isCapital() {
		return capital;
	}

	public String getEstado() {
		return estado;
	}

	public double getRendaPerCapita() {
		return rendaPerCapita;
	}

	public String getDataFuncao() {
		return dataFuncao;
	}
}
