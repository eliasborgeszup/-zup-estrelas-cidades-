package br.com.zup.estrelas.cidades;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import br.com.zup.estrelas.cidades.dao.CidadeDAO;
import br.com.zup.estrelas.cidades.pojo.CidadePOJO;

public class ProgramaPrincipal {

	private static final String MENU = ("-------- BEM VINDO AO SISTEMA DE CADASTRO DO IBGE --------"
			+ "\n\n1 - Cadastrar cidade \n2 - Excluir cidade \n3 - Alterar cidade \n4 - Visualizar cidades \n0 - Sair");

	private static final String REGEX_NOME = "[A-Za-z·‡‚„ÈËÍÌÔÛÙıˆ˙ÁÒ¡¿¬√…»Õœ”‘’÷⁄«— ]*";

	private static final String REGEX_ESTADO = "[a-zA-Z]{2}";

	private static final String REGEX_CEP = "[0-9]{8}";

	private static final String REGEX_FORMATO_NUMERICO = "[1-9]*";

	private static final String REGEX_DATA = "((15|16|17|18|19|20|[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";

	public static void cadastrarCidade(Scanner teclado) throws SQLException {
		CidadeDAO cidadeDao = new CidadeDAO();

		teclado.nextLine();

		System.out.print("Digite o CEP (00000000): ");
		String cep = teclado.nextLine();
		while (cidadeDao.verificarCepCadastrado(cep) || !Pattern.matches(REGEX_CEP, cep)) {
			System.out.print("Ops.. CEP inexistente ou invalido, digite novamente: ");
			cep = teclado.nextLine();
		}

		System.out.print("Digite o nome: ");
		String nome = teclado.nextLine();
		while (!Pattern.matches(REGEX_NOME, nome)) {
			System.out.print("Ops.. Nome invalido, digite novamente: ");
			nome = teclado.nextLine();
		}

		System.out.print("Digite o numero da habitantes: ");
		String stringNumeroHabitantes = teclado.nextLine();
		while (!Pattern.matches(REGEX_FORMATO_NUMERICO, stringNumeroHabitantes)) {
			System.out.print("Ops.. Numero de habitantes invalido, digite novamente: ");
			stringNumeroHabitantes = teclado.nextLine();
		}

		int numeroHabitantes = Integer.parseInt(stringNumeroHabitantes);

		System.out.print("Cidade cadastrada È capital? \n\nDigite 1 - SIM \\n2 - N√O: ");
		int opcaoCapital = teclado.nextInt();

		while (opcaoCapital != 1 && opcaoCapital != 2) {
			System.out.print("Ops.. OpÁ„o invalida, digite novamente: ");
			opcaoCapital = teclado.nextInt();
		}

		boolean capital;

		if (opcaoCapital == 1) {
			capital = true;
		} else {
			capital = false;
		}

		teclado.nextLine();
		System.out.print("Estado: ");
		String estado = teclado.nextLine();
		while (!Pattern.matches(REGEX_ESTADO, estado)) {
			System.out.print("Ops.. Estado invalido, digite novamente: ");
			estado = teclado.nextLine();
		}
		estado = estado.toUpperCase();

		System.out.print("Renda por capita: ");
		String stringRendaPerCapita = teclado.nextLine();

		while (!Pattern.matches(REGEX_FORMATO_NUMERICO, stringRendaPerCapita)) {
			System.out.print("Ops.. Renda per capita invalida, digite novamente: ");
			stringRendaPerCapita = teclado.nextLine();
		}

		double rendaPerCapita = Double.parseDouble(stringRendaPerCapita);

		teclado.nextLine();
		System.out.print("Data da fundaÁ„o (AAAA-MM-DD): ");
		String dataFundacao = teclado.nextLine();

		while (!Pattern.matches(REGEX_DATA, dataFundacao)) {
			System.out.print("Ops.. data invalida, digite novamente: ");
			dataFundacao = teclado.nextLine();
		}

		CidadePOJO cidade = new CidadePOJO(cep, nome, numeroHabitantes, capital, estado, rendaPerCapita, dataFundacao);

		try {
			cidadeDao.insereCidadeBD(cidade);
			System.out.println("Cidade cadastrada com sucesso!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void excluirCidade(Scanner teclado) {
		teclado.nextLine();

		System.out.print("Digite o CEP (00000000): ");
		String cep = teclado.nextLine();
		while (!Pattern.matches(REGEX_CEP, cep)) {
			System.out.print("Ops.. CEP invalido, digite novamente: ");
			cep = teclado.nextLine();
		}

		CidadeDAO cidadeDao = new CidadeDAO();

		try {
			cidadeDao.excluirCidadeBD(cep);
			System.out.println("Cidade excluida com sucesso!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws SQLException {
		Scanner teclado = new Scanner(System.in);
		int opcao = 0;

		do {
			System.out.println(MENU);

			System.out.print("\nDigite uma opÁ„o: ");
			opcao = teclado.nextInt();

			switch (opcao) {
			case 1:
				cadastrarCidade(teclado);
				break;

			case 2:
				excluirCidade(teclado);
				break;

			case 3:
				System.out.println("Em desenvolvimento");
				break;

			case 4:
				System.out.println("Em desenvolvimento");
				break;

			default:
				System.out.println("OpÁ„o invalida");
			}
		} while (opcao != 0);
		teclado.close();
	}

}
