package br.com.zup.estrelas.cidades;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import br.com.zup.estrelas.cidades.dao.CidadeDAO;
import br.com.zup.estrelas.cidades.dao.EstadoDAO;
import br.com.zup.estrelas.cidades.pojo.CidadePOJO;
import br.com.zup.estrelas.cidades.pojo.EstadoPOJO;
import br.com.zup.estrelas.cidades.validacoes.ClienteValidacao;

public class ProgramaPrincipal {

	private static final String MENU = ("-------- BEM VINDO AO SISTEMA DE CADASTRO DO IBGE --------"
			+ "\n\n1 - Cadastrar cidade \n2 - Excluir cidade "
			+ "\n3 - Visualizar cidades \n4 - Buscar cidade por CEP \n5 - Buscar cidade por nome "
			+ "\n6 - Buscar cidade pela sigla do estado \n7 - Buscar cidade por capital \n0 - Sair");

	public static void cadastrarCidade(Scanner teclado) {
		CidadeDAO cidadeDao = new CidadeDAO();

		System.out.print("Digite o CEP (00000000): ");
		String cep = teclado.nextLine();
		cep = ClienteValidacao.validaCepCadastrado(teclado, cep);

		System.out.print("Digite o nome: ");
		String nome = teclado.nextLine();
		nome = ClienteValidacao.validaNome(teclado, nome);

		System.out.print("Digite o numero da habitantes: ");
		String stringNumeroHabitantes = teclado.nextLine();
		int numeroHabitantes = ClienteValidacao.validaNumeroHabitantes(teclado, stringNumeroHabitantes);

		System.out.print("\nCidade cadastrada é capital? \nDigite 1 - SIM 2 - NÃO: ");
		int opcaoCapital = teclado.nextInt();
		boolean capital = ClienteValidacao.validaCapital(teclado, opcaoCapital);

		teclado.nextLine();

		System.out.print("Sigla do estado (AA): ");
		String estado = teclado.nextLine().toUpperCase();
		estado = ClienteValidacao.validaEstado(teclado, estado);

		System.out.print("Renda por capita: R$");
		String stringRendaPerCapita = teclado.nextLine();
		double rendaPerCapita = ClienteValidacao.validaRendaPerCapita(teclado, stringRendaPerCapita);

		System.out.print("Data da fundação (AAAA-MM-DD): ");
		String dataFundacao = teclado.nextLine();
		dataFundacao = ClienteValidacao.validaDataFundacao(teclado, dataFundacao);

		CidadePOJO cidade = new CidadePOJO(cep, nome, numeroHabitantes, capital, estado, rendaPerCapita, dataFundacao);

		if (cidadeDao.insereCidadeBD(cidade)) {
			System.out.println("Cidade cadastrada com sucesso!");
		}
	}

	public static void excluirCidade(Scanner teclado) {
		CidadeDAO cidadeDao = new CidadeDAO();

		System.out.print("Digite o CEP (00000000): ");
		String cep = teclado.nextLine();
		cep = ClienteValidacao.validaCep(teclado, cep);

		if (cidadeDao.excluirCidadeBD(cep)) {
			System.out.println("Cidade excluida com sucesso!");
		}

	}

	public static void listarCidades() {
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<CidadePOJO> cidadesBD = cidadeDAO.listarCidadesBD();

		imprimirListaCidades(cidadesBD);
	}

	public static void listarSiglasEstados() {
		EstadoDAO estadoDAO = new EstadoDAO();
		List<EstadoPOJO> estadosBD = estadoDAO.listarEstados();

		System.out.println("================================ SIGLAS EXISTENTES ================================\n");

		for (EstadoPOJO estadoPOJO : estadosBD) {
			System.out.printf("%s ", estadoPOJO.getSigla());
		}
		System.out.println("\n\n===================================================================================");
	}

	public static void listaCidadePorCep(Scanner teclado) {
		CidadeDAO cidadeDao = new CidadeDAO();

		System.out.print("Digite o CEP (00000000): ");
		String cep = teclado.nextLine();

		CidadePOJO cidadePOJO = cidadeDao.buscaCepCadastrado(cep);

		while (cidadePOJO.getCep() == null || !Pattern.matches(ClienteValidacao.REGEX_CEP, cep)) {
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

	public static void listaCidadesPorNome(Scanner teclado) {
		System.out.print("Digite o nome que deseja filtrar: ");
		String nome = teclado.nextLine();
		nome = ClienteValidacao.validaNome(teclado, nome);

		CidadeDAO cidadeDAO = new CidadeDAO();
		List<CidadePOJO> cidadesBD = cidadeDAO.listarCidadesPorNome(nome);

		imprimirListaCidades(cidadesBD);
	}

	public static void imprimirListaCidades(List<CidadePOJO> cidadesBD) {
		for (CidadePOJO cidadePOJO : cidadesBD) {
			System.out.printf(
					"Nome: %s | CEP: %s | Numero habitantes: %d | Capital: %b | Estado: %s "
							+ "| Renda per capita: %.2f | Data fundação: %s\n\n",
					cidadePOJO.getNome(), cidadePOJO.getCep(), cidadePOJO.getNumeroHabitantes(), cidadePOJO.isCapital(),
					cidadePOJO.getEstado(), cidadePOJO.getRendaPerCapita(), cidadePOJO.getDataFundacao());
		}
	}

	public static void listaCidadesPorSigla(Scanner teclado) {
		System.out.print("\nDigite uma sigla do estado (AA) que deseja filtra: ");
		String sigla = teclado.nextLine().toUpperCase();
		sigla = ClienteValidacao.validaEstado(teclado, sigla);

		CidadeDAO cidadeDAO = new CidadeDAO();
		List<CidadePOJO> cidadesBD = cidadeDAO.listarCidadesPorSigla(sigla);

		imprimirListaCidades(cidadesBD);
	}

	public static void listaCidadesPorCapital(Scanner teclado) {
		System.out.print("\nDigite 1 - PARA CAPITAIS 2 - PARA MUNICIPIOS: ");
		int opcaoCapital = teclado.nextInt();
		boolean capital = ClienteValidacao.validaCapital(teclado, opcaoCapital);

		CidadeDAO cidadeDAO = new CidadeDAO();
		List<CidadePOJO> cidadesBD = cidadeDAO.listarCidadesPorCapital(capital);

		imprimirListaCidades(cidadesBD);
		System.out.println(teclado.nextLine());
	}

	public static void main(String[] args) throws SQLException {
		Scanner teclado = new Scanner(System.in);
		int opcao = 0;

		do {
			System.out.println(MENU);

			System.out.print("\nDigite uma opção: ");
			String stringOpcao = teclado.nextLine();
			opcao = ClienteValidacao.validaOpcao(teclado, stringOpcao);

			switch (opcao) {
			case 1:
				cadastrarCidade(teclado);
				break;

			case 2:
				excluirCidade(teclado);
				break;

			case 3:
				listarCidades();
				break;

			case 4:
				listaCidadePorCep(teclado);
				break;

			case 5:
				listaCidadesPorNome(teclado);
				break;

			case 6:
				listarSiglasEstados();
				listaCidadesPorSigla(teclado);
				break;

			case 7:
				listaCidadesPorCapital(teclado);
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
