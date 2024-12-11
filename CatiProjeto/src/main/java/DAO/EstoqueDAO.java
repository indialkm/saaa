package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstoqueDAO {
	
	private Connection connection;
	DBConnection dbConnection = new DBConnection();

	public EstoqueDAO() {
		this.connection = dbConnection.getConnection();
	}

	public int buscarIdEstoquePorEquipamento(int idEquipamento) {
	    int idEstoque = -1; 

	    String sql = "SELECT idEstoque FROM estoque WHERE idEquipamento = ?";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setInt(1, idEquipamento); 

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                idEstoque = rs.getInt("idEstoque"); 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return idEstoque;
	}


}
