package br.com.zup.estrelas.cidades.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.zup.estrelas.cidades.connection.factory.ConnectionFactory;
import br.com.zup.estrelas.cidades.pojo.CidadePOJO;

public class CidadeDAO {
	private Connection conexao;

	public CidadeDAO() {
		this.conexao = new ConnectionFactory().obterConexao();
	}

	public void insereCidadeBD(CidadePOJO cidade) throws SQLException {

		String inserirCidadeSql = "INSERT INTO banco_estrelas.cidade"
				+ "(cep, nome, numero_habitantes, capital, estado, renda_per_capita, data_fundacao)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement stmt = conexao.prepareStatement(inserirCidadeSql);
		stmt.setString(1, cidade.getCep());
		stmt.setString(2, cidade.getNome());
		stmt.setInt(3, cidade.getNumeroHabitantes());
		stmt.setBoolean(4, cidade.isCapital());
		stmt.setString(5, cidade.getEstado());
		stmt.setDouble(6, cidade.getRendaPerCapita());
		stmt.setString(7, cidade.getDataFundacao());

		if (!stmt.execute()) {
			stmt.close();
			return;
		}

		stmt.close();
		throw new SQLException("N�o foi possivel inserir no BD, tente novamente");
	}

	public void excluirCidadeBD(String cep) throws SQLException {

		String deletarCidadeSql = "DELETE FROM banco_estrelas.cidade" + " WHERE cep = ?";

		PreparedStatement stmt = conexao.prepareStatement(deletarCidadeSql);
		stmt.setString(1, cep);

		if (stmt.executeUpdate() == 1) {
			stmt.close();
			return;
		}

		throw new SQLException(String.format("N�o foi possivel excluir o CEP %s no BD, tente novamente", cep));

	}

	public boolean verificarCepCadastrado(String cep) throws SQLException {

		String buscarCepSql = "SELECT * FROM banco_estrelas.cidade WHERE cep = ?";

		PreparedStatement stmt = conexao.prepareStatement(buscarCepSql);
		stmt.setString(1, cep);

		if (stmt.executeQuery().next()) {
			stmt.close();
			return true;
		}

		stmt.close();
		return false;
	}

	public boolean verificarEstadoCadastrado(String estado) throws SQLException {

		String buscarCepSql = "SELECT * FROM banco_estrelas.estado WHERE sigla = ?";

		PreparedStatement stmt = conexao.prepareStatement(buscarCepSql);
		stmt.setString(1, estado);

		if (stmt.executeQuery().next()) {
			stmt.close();
			return true;
		}
		stmt.close();
		return false;
	}

	public void fecharConexaoBD() throws SQLException {
		this.conexao.close();
	}
}
