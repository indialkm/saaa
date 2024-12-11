package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Categoria;

public class CategoriaDAO {
    private Connection connection;

    public CategoriaDAO(Connection connection) {
        this.connection = connection;
    }

    public Categoria buscarCategoriaPorId(int idCategoria) {
        Categoria categoria = null;
        String sql = "SELECT * FROM categorias WHERE idCategoria = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNomeCategoria(rs.getString("nomeCategoria"));
                categoria.setDescricaoCategoria(rs.getString("descricao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categoria;
    }
}

