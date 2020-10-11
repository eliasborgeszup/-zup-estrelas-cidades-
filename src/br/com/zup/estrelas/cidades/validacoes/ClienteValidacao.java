package br.com.zup.estrelas.cidades.validacoes;

import java.util.Scanner;
import java.util.regex.Pattern;

import br.com.zup.estrelas.cidades.dao.CidadeDAO;
import br.com.zup.estrelas.cidades.pojo.CidadePOJO;

public class ClienteValidacao {
	public static final String REGEX_NOME = "[A-Za-z·‡‚„ÈËÍÌÔÛÙıˆ˙ÁÒ¡¿¬√…»Õœ”‘’÷⁄«— ]*";

	public static final String REGEX_ESTADO = "[A-Z]{2}";

	public static final String REGEX_CEP = "[0-9]{8}";

	public static final String REGEX_FORMATO_NUMERICO = "[0-9]*";

	public static final String REGEX_DATA = "((15|16|17|18|19|20)[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";

	public static int validaOpcao(Scanner teclado, String stringOpcao) {		
		while (!Pattern.matches(ClienteValidacao.REGEX_FORMATO_NUMERICO, stringOpcao)) {
			System.out.print("Ops.. OpÁao invalida, digite novamente: ");
			stringOpcao = teclado.nextLine();
		}

		int opcao = Integer.parseInt(stringOpcao);

		return opcao;
	}
	
	public static String validaCepCadastrado(Scanner teclado, String cep) {
		CidadeDAO cidadeDao = new CidadeDAO();

		CidadePOJO cidadePOJO = cidadeDao.buscaCepCadastrado(cep);

		while (cidadePOJO.getCep() != null || !Pattern.matches(REGEX_CEP, cep)) {
			System.out.print("Ops.. CEP cadastrado ou invalido, digite novamente: ");
			cep = teclado.nextLine();

			cidadePOJO = cidadeDao.buscaCepCadastrado(cep);
		}

		return cep;
	}

	public static String validaCep(Scanner teclado, String cep) {
		while (!Pattern.matches(ClienteValidacao.REGEX_CEP, cep)) {
			System.out.print("Ops.. Formato do CEP invalido, digite novamente: ");
			cep = teclado.nextLine();
		}

		return cep;
	}

	public static String validaNome(Scanner teclado, String nome) {
		while (!Pattern.matches(ClienteValidacao.REGEX_NOME, nome)) {
			System.out.print("Ops.. Nome invalido, digite novamente: ");
			nome = teclado.nextLine();
		}

		return nome;
	}

	public static int validaNumeroHabitantes(Scanner teclado, String stringNumeroHabitantes) {
		while (!Pattern.matches(ClienteValidacao.REGEX_FORMATO_NUMERICO, stringNumeroHabitantes)) {
			System.out.print("Ops.. Numero de habitantes invalido, digite novamente: ");
			stringNumeroHabitantes = teclado.nextLine();
		}

		int numeroHabitantes = Integer.parseInt(stringNumeroHabitantes);

		return numeroHabitantes;
	}

	public static boolean validaCapital(Scanner teclado, int opcaoCapital) {
		while (opcaoCapital != 1 && opcaoCapital != 2) {
			System.out.print("Ops.. OpÁ„o invalida, digite novamente: ");
			opcaoCapital = teclado.nextInt();
		}

		if (opcaoCapital == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static String validaEstado(Scanner teclado, String estado) {
		CidadeDAO cidadeDao = new CidadeDAO();

		while (!cidadeDao.verificarEstadoCadastrado(estado)
				|| !Pattern.matches(ClienteValidacao.REGEX_ESTADO, estado)) {
			System.out.print("Ops.. Estado invalido, digite novamente: ");
			estado = teclado.nextLine().toUpperCase();
		}

		return estado;
	}

	public static Double validaRendaPerCapita(Scanner teclado, String stringRendaPerCapita) {
		while (!Pattern.matches(ClienteValidacao.REGEX_FORMATO_NUMERICO, stringRendaPerCapita)) {
			System.out.print("Ops.. Renda per capita invalida, digite novamente: ");
			stringRendaPerCapita = teclado.nextLine();
		}

		double rendaPerCapita = Double.parseDouble(stringRendaPerCapita);

		return rendaPerCapita;
	}

	public static String validaDataFundacao(Scanner teclado, String dataFundacao) {
		while (!Pattern.matches(ClienteValidacao.REGEX_DATA, dataFundacao)) {
			System.out.print("Ops.. data invalida, digite novamente: ");
			dataFundacao = teclado.nextLine();
		}

		return dataFundacao;
	}
}
