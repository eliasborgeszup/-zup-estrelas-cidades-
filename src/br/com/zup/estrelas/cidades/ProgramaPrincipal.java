package br.com.zup.estrelas.cidades;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import br.com.zup.estrelas.cidades.dao.CidadeDAO;
import br.com.zup.estrelas.cidades.pojo.CidadePOJO;
import br.com.zup.estrelas.cidades.regex.REGEX;

public class ProgramaPrincipal {

	private static final String MENU = ("-------- BEM VINDO AO SISTEMA DE CADASTRO DO IBGE --------"
			+ "\n\n1 - Cadastrar cidade \n2 - Excluir cidade \n3 - Alterar cidade \n4 - Visualizar cidades \n5 - Buscar cidade por CEP \n0 - Sair");

	public static void cadastrarCidade(Scanner teclado) {
		CidadeDAO cidadeDao = new CidadeDAO();

		teclado.nextLine();

		System.out.print("Digite o CEP (00000000): ");
		String cep = teclado.nextLine();

		CidadePOJO cidadePOJO = cidadeDao.buscaCepCadastrado(cep);

		while (cidadePOJO.getCep() != null || !Pattern.matches(REGEX.CEP, cep)) {
			System.out.print("Ops.. CEP não cadastrado ou invalido, digite novamente: ");
			cep = teclado.nextLine();

			cidadePOJO = cidadeDao.buscaCepCadastrado(cep);
		}

		System.out.print("Digite o nome: ");
		String nome = teclado.nextLine();
		while (!Pattern.matches(REGEX.NOME, nome)) {
			System.out.print("Ops.. Nome invalido, digite novamente: ");
			nome = teclado.nextLine();
		}

		System.out.print("Digite o numero da habitantes: ");
		String stringNumeroHabitantes = teclado.nextLine();
		while (!Pattern.matches(REGEX.FORMATO_NUMERICO, stringNumeroHabitantes)) {
			System.out.print("Ops.. Numero de habitantes invalido, digite novamente: ");
			stringNumeroHabitantes = teclado.nextLine();
		}

		int numeroHabitantes = Integer.parseInt(stringNumeroHabitantes);

		System.out.print("\nCidade cadastrada é capital? \nDigite 1 - SIM 2 - NÃO: ");
		int opcaoCapital = teclado.nextInt();

		while (opcaoCapital != 1 && opcaoCapital != 2) {
			System.out.print("Ops.. Opção invalida, digite novamente: ");
			opcaoCapital = teclado.nextInt();
		}

		boolean capital;

		if (opcaoCapital == 1) {
			capital = true;
		} else {
			capital = false;
		}

		teclado.nextLine();
		System.out.print("Sigla do estado (AA): ");
		String estado = teclado.nextLine().toUpperCase();

		while (!cidadeDao.verificarEstadoCadastrado(estado) || !Pattern.matches(REGEX.ESTADO, estado)) {
			System.out.print("Ops.. Estado invalido, digite novamente: ");
			estado = teclado.nextLine().toUpperCase();
		}

		System.out.print("Renda por capita: R$");
		String stringRendaPerCapita = teclado.nextLine();

		while (!Pattern.matches(REGEX.FORMATO_NUMERICO, stringRendaPerCapita)) {
			System.out.print("Ops.. Renda per capita invalida, digite novamente: ");
			stringRendaPerCapita = teclado.nextLine();
		}

		double rendaPerCapita = Double.parseDouble(stringRendaPerCapita);

		System.out.print("Data da fundação (AAAA-MM-DD): ");
		String dataFundacao = teclado.nextLine();

		while (!Pattern.matches(REGEX.DATA, dataFundacao)) {
			System.out.print("Ops.. data invalida, digite novamente: ");
			dataFundacao = teclado.nextLine();
		}

		CidadePOJO cidade = new CidadePOJO(cep, nome, numeroHabitantes, capital, estado, rendaPerCapita, dataFundacao);

		if (cidadeDao.insereCidadeBD(cidade)) {
			System.out.println("Cidade cadastrada com sucesso!");
		}
	}

	public static void excluirCidade(Scanner teclado) {
		CidadeDAO cidadeDao = new CidadeDAO();
		teclado.nextLine();

		System.out.print("Digite o CEP (00000000): ");
		String cep = teclado.nextLine();

		CidadePOJO cidadePOJO = cidadeDao.buscaCepCadastrado(cep);

		while (cidadePOJO.getCep() == null || !Pattern.matches(REGEX.CEP, cep)) {
			System.out.print("Ops.. CEP não cadastrado ou invalido, digite novamente: ");
			cep = teclado.nextLine();

			cidadePOJO = cidadeDao.buscaCepCadastrado(cep);
		}

		if (cidadeDao.excluirCidadeBD(cep)) {
			System.out.println("Cidade excluida com sucesso!");
		}

	}

	public static void listarCidades() {
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<CidadePOJO> cidadesDB = cidadeDAO.listarCidadesBD();

		for (CidadePOJO cidadePOJO : cidadesDB) {
			System.out.printf(
					"Nome: %s | CEP: %s | Numero habitantes: %d | Capital: %b | Estado: %s "
							+ "| Renda per capita: %.2f | Data fundação: %s\n\n",
					cidadePOJO.getNome(), cidadePOJO.getCep(), cidadePOJO.getNumeroHabitantes(), cidadePOJO.isCapital(),
					cidadePOJO.getEstado(), cidadePOJO.getRendaPerCapita(), cidadePOJO.getDataFundacao());
		}
	}

	public static void listaCidadePorCep(Scanner teclado) {
		CidadeDAO cidadeDao = new CidadeDAO();

		teclado.nextLine();
		System.out.print("Digite o CEP (00000000): ");
		String cep = teclado.nextLine();

		CidadePOJO cidadePOJO = cidadeDao.buscaCepCadastrado(cep);

		while (cidadePOJO.getCep() == null || !Pattern.matches(REGEX.CEP, cep)) {
			System.out.print("Ops.. CEP não cadastrado ou invalido, digite novamente: ");
			cep = teclado.nextLine();

			cidadePOJO = cidadeDao.buscaCepCadastrado(cep);
		}

		System.out.printf(
				"Nome: %s | CEP: %s | Numero habitantes: %d | Capital: %b | Estado: %s "
						+ "| Renda per capita: %.2f | Data fundação: %s\n\n",
				cidadePOJO.getNome(), cidadePOJO.getCep(), cidadePOJO.getNumeroHabitantes(), cidadePOJO.isCapital(),
				cidadePOJO.getEstado(), cidadePOJO.getRendaPerCapita(), cidadePOJO.getDataFundacao());
	}

	public static void main(String[] args) throws SQLException {
		Scanner teclado = new Scanner(System.in);
		int opcao = 0;

		do {
			System.out.println(MENU);

			System.out.print("\nDigite uma opção: ");
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
				listarCidades();
				break;

			case 5:
				listaCidadePorCep(teclado);
				break;

			case 0:
				System.out.println("Volte sempre :)");
				break;

			default:
				System.out.println("Opção invalida");
			}
		} while (opcao != 0);
		teclado.close();
	}

}
