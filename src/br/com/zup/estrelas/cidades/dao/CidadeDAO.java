package br.com.zup.estrelas.cidades.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.zup.estrelas.cidades.connection.factory.ConnectionFactory;
import br.com.zup.estrelas.cidades.pojo.CidadePOJO;

public class CidadeDAO {
	private Connection conexao;

	public CidadeDAO() {
		this.conexao = new ConnectionFactory().obterConexao();
	}

	public boolean insereCidadeBD(CidadePOJO cidade){
		String inserirCidadeSql = "INSERT INTO cidade"
				+ "(cep, nome, numero_habitantes, capital, estado, renda_per_capita, data_fundacao)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conexao.prepareStatement(inserirCidadeSql);
			stmt.setString(1, cidade.getCep());
			stmt.setString(2, cidade.getNome());
			stmt.setInt(3, cidade.getNumeroHabitantes());
			stmt.setBoolean(4, cidade.isCapital());
			stmt.setString(5, cidade.getEstado());
			stmt.setDouble(6, cidade.getRendaPerCapita());
			stmt.setString(7, cidade.getDataFundacao());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("Não foi possivel cadastrar cidade, tente novamente");
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	public boolean excluirCidadeBD(String cep){
		String deletarCidadeSql = "DELETE FROM cidade" + " WHERE cep = ?";

		try {
			PreparedStatement stmt = conexao.prepareStatement(deletarCidadeSql);
			stmt.setString(1, cep);

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.err.printf("Não foi possivel excluir o CEP %s no BD, tente novamente\n", cep);
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	public CidadePOJO buscaCepCadastrado(String cep) {
		CidadePOJO cidade = new CidadePOJO();

		String buscarCepSql = "SELECT * FROM cidade WHERE cep = ?";

		try {
			PreparedStatement stmt = conexao.prepareStatement(buscarCepSql);
			stmt.setString(1, cep);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				cidade.setCep(rs.getString("cep"));
				cidade.setNome(rs.getString("nome"));
				cidade.setNumeroHabitantes(rs.getInt("numero_habitantes"));
				cidade.setCapital(rs.getBoolean("capital"));
				cidade.setEstado(rs.getString("estado"));
				cidade.setRendaPerCapita(rs.getDouble("renda_per_capita"));
				cidade.setDataFundacao(rs.getString("data_fundacao"));
			}

		} catch (SQLException e) {
			System.err.println("Erro ao lista cidade" + e.getMessage());
		}

		return cidade;
	}

	public boolean verificarEstadoCadastrado(String estado) {
		String buscarCepSql = "SELECT * FROM estado WHERE sigla = ?";

		try {
			PreparedStatement stmt = conexao.prepareStatement(buscarCepSql);
			stmt.setString(1, estado);

			if (stmt.executeQuery().next()) {
				stmt.close();
				return true;
			}
		} catch (SQLException e) {
			return false;
		}

		return false;
	}

	public List<CidadePOJO> listarCidadesBD() {
		List<CidadePOJO> cidades = new ArrayList<>();

		String selecionarCidadesSql = "SELECT * FROM cidade";

		try {
			PreparedStatement stmt = conexao.prepareStatement(selecionarCidadesSql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				CidadePOJO cidade = new CidadePOJO();

				cidade.setCep(rs.getString("cep"));
				cidade.setNome(rs.getString("nome"));
				cidade.setNumeroHabitantes(rs.getInt("numero_habitantes"));
				cidade.setCapital(rs.getBoolean("capital"));
				cidade.setEstado(rs.getString("estado"));
				cidade.setRendaPerCapita(rs.getDouble("renda_per_capita"));
				cidade.setDataFundacao(rs.getString("data_fundacao"));

				cidades.add(cidade);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao lista cidade" + e.getMessage());
		}
		return cidades;
	}

	public void fecharConexaoBD() throws SQLException {
		this.conexao.close();
	}
}
