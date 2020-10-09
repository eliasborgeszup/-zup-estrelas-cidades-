package br.com.zup.estrelas.cidades.pojo;

public class CidadePOJO {
	private String cep;
	private String nome;
	private int numeroHabitantes;
	private boolean capital;
	private String estado;
	private double rendaPerCapita;
	private String dataFundacao;
	
	public CidadePOJO() {
		
	}
	
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

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNumeroHabitantes(int numeroHabitantes) {
		this.numeroHabitantes = numeroHabitantes;
	}

	public void setCapital(boolean capital) {
		this.capital = capital;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setRendaPerCapita(double rendaPerCapita) {
		this.rendaPerCapita = rendaPerCapita;
	}

	public void setDataFundacao(String dataFundacao) {
		this.dataFundacao = dataFundacao;
	}
}
