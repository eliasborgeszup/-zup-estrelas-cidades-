package br.com.zup.estrelas.cidades.pojo;

public class CidadePOJO {
	private String cep;
	private String nome;
	private int numeroHabitantes;
	private boolean capital;
	private String estado;
	private double rendaPerCapita;
	private String dataFundacao;
	
	public CidadePOJO(String cep, String nome, int numeroHabitantes, boolean capital, String estado, double rendaPerCapita, String dataFundacao) {
		this.cep = cep;
		this.nome = nome;
		this.numeroHabitantes = numeroHabitantes;
		this.capital = capital;
		this.estado = estado;
		this.rendaPerCapita = rendaPerCapita;
		this.dataFundacao = dataFundacao;
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

	public String getDataFundacao() {
		return dataFundacao;
	}
}
