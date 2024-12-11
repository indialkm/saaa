package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Fornecedor;

public class FornecedorDAO {

    private Connection connection;

    // Construtor para inicializar a conexão com o banco
    public FornecedorDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para buscar o fornecedor por ID
    public Fornecedor buscarFornecedorPorId(int idFornecedor) {
        Fornecedor fornecedor = null;
        String sql = "SELECT * FROM fornecedor WHERE idFornecedor = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFornecedor);  // Definir o idFornecedor para a consulta
            ResultSet rs = stmt.executeQuery();  // Executa a consulta
            
            if (rs.next()) {
                // Preenche os dados do Fornecedor
                fornecedor = new Fornecedor();
                fornecedor.setIdFornecedor(rs.getInt("idFornecedor"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setTipoPessoa(rs.getString("tipoPessoa"));
                fornecedor.setCpfcnpj(rs.getString("cpf_cnpj"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedor.setBairro(rs.getString("bairro"));
                fornecedor.setCidade(rs.getString("cidade"));
                fornecedor.setUf(rs.getString("uf"));
                fornecedor.setCep(rs.getString("cep"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setDescricao(rs.getString("descricao"));
                fornecedor.setFoto(rs.getString("logo"));
                fornecedor.setAtivo(rs.getBoolean("ativo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return fornecedor;  // Retorna o objeto Fornecedor preenchido
    }
}
