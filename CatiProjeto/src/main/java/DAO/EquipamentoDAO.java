package DAO;

import models.Equipamento;
//import models.Fornecedor;
//import models.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class EquipamentoDAO {

	private Connection connection;

	DBConnection dbConnection = new DBConnection();

	public EquipamentoDAO() {
		this.connection = dbConnection.getConnection();
		
		if (connection == null) {
		    System.out.println("Falha na conexão com o banco de dados.");
		} else {
		    System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
		}
	}

	public Equipamento listarPorId(int idEquipamento) {
        Equipamento equipamento = null;
        String sql = "SELECT * FROM equipamento WHERE idEquipamento = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEquipamento);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                equipamento = mapResultSetToEquipamento(rs);
                //Fornecedor fornecedor = buscarFornecedorPorId(rs.getInt("idFornecedor"));
                //Categoria categoria = buscarCategoriaPorId(rs.getInt("idCategoria"));

                //equipamento.setFornecedor(fornecedor);
                //equipamento.setCategoria(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equipamento;
    }

    public List<Equipamento> listarTodos() {
        List<Equipamento> equipamentos = new ArrayList<>();
        String sql = "SELECT * FROM equipamento";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Equipamento equipamento = mapResultSetToEquipamento(rs);
                //Fornecedor fornecedor = buscarFornecedorPorId(rs.getInt("idFornecedor"));
                //Categoria categoria = buscarCategoriaPorId(rs.getInt("idCategoria"));

                //equipamento.setFornecedor(fornecedor);
                //equipamento.setCategoria(categoria);
                
                equipamentos.add(equipamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equipamentos;
    }

    private Equipamento mapResultSetToEquipamento(ResultSet rs) throws SQLException {
        Equipamento equipamento = new Equipamento();
        equipamento.setIdEquipamento(rs.getInt("idEquipamento"));
        equipamento.setNome(rs.getString("nome"));
        equipamento.setPreco(rs.getDouble("preco"));
        equipamento.setMarca(rs.getString("marca"));
        equipamento.setModelo(rs.getString("modelo"));
        equipamento.setDescricao(rs.getString("descricao"));
        equipamento.setUnidadeMedida(rs.getString("unidadeMedida"));
        equipamento.setLargura(rs.getBigDecimal("largura"));
        equipamento.setAltura(rs.getBigDecimal("altura"));
        equipamento.setProfundidade(rs.getBigDecimal("profundidade"));
        equipamento.setPeso(rs.getBigDecimal("peso"));
        equipamento.setCor(rs.getString("cor"));
        equipamento.setFoto(rs.getString("foto"));
        equipamento.setEstadoConservacao(rs.getBigDecimal("estadoConservacao"));
        return equipamento;
    }

   /* private Fornecedor buscarFornecedorPorId(int idFornecedor) {
        FornecedorDAO fornecedorDAO = new FornecedorDAO(connection);
        return fornecedorDAO.buscarFornecedorPorId(idFornecedor);
    }

    private Categoria buscarCategoriaPorId(int idCategoria) {
        CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
        return categoriaDAO.buscarCategoriaPorId(idCategoria);
    }*/


}
