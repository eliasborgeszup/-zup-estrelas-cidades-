package br.com.zup.estrelas.cidades.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.zup.estrelas.cidades.connection.factory.ConnectionFactory;
import br.com.zup.estrelas.cidades.pojo.EstadoPOJO;

public class EstadoDAO {
	private Connection conexao;

	public EstadoDAO() {
		this.conexao = new ConnectionFactory().obterConexao();
	}
	
	public List<EstadoPOJO> listarEstados(){
		List<EstadoPOJO> estados = new ArrayList<>();
		
		String selecionarEstadoSql = "SELECT * FROM estado";
		
		try {
			PreparedStatement stmt = conexao.prepareStatement(selecionarEstadoSql);

			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				EstadoPOJO estado = new EstadoPOJO();

				estado.setSigla(rs.getString("sigla"));
				estados.add(estado);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar estado " + e.getMessage());
		}
		
		return estados;
	}
}
