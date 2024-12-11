package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mysql.jdbc.Statement;

import models.Aluguel;
import models.Pedidos;
import models.StatusAluguel;
import models.StatusPedido;

public class PedidoDAO {

	private Connection connection;
	DBConnection dbConnection = new DBConnection();

	public PedidoDAO() {
		this.connection = dbConnection.getConnection();
	}

	public boolean salvarPedido(Pedidos pedido) {
	    String sqlPedido = "INSERT INTO pedido (idCliente, dtPedido, dtPagamento, dtNotaFiscal, notaFiscal, dtEnvio, "
	            + "dtRecebimento, tipoFrete, statusPedido, valorTotal, qtdItems, dtCancelamento, motivoCancelamento) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    String sqlUpdateAluguel = "UPDATE aluguel SET idPedido = ? WHERE idAluguel = ?";

	    try (PreparedStatement psPedido = connection.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
	       
	        psPedido.setInt(1, pedido.getCliente().getIdCliente());
	        psPedido.setTimestamp(2, Timestamp.valueOf(pedido.getDtPedido()));
	        psPedido.setTimestamp(3, pedido.getDtPagamento() != null ? Timestamp.valueOf(pedido.getDtPagamento()) : null);
	        psPedido.setTimestamp(4, pedido.getDtNotaFiscal() != null ? Timestamp.valueOf(pedido.getDtNotaFiscal()) : null);
	        psPedido.setString(5, pedido.getNotaFiscal());
	        psPedido.setTimestamp(6, pedido.getDtEnvio() != null ? Timestamp.valueOf(pedido.getDtEnvio()) : null);
	        psPedido.setTimestamp(7, pedido.getDtRecebimento() != null ? Timestamp.valueOf(pedido.getDtRecebimento()) : null);
	        psPedido.setInt(8, pedido.getTipoFrete());
	        psPedido.setString(9, pedido.getStatusPedido().toString());
	        psPedido.setFloat(10, pedido.getValorTotal());
	        psPedido.setInt(11, pedido.getQtdItems());
	        psPedido.setTimestamp(12, pedido.getDtCancelamento() != null ? Timestamp.valueOf(pedido.getDtCancelamento()) : null);
	        psPedido.setString(13, pedido.getMotivoCancelamento());

	        int rowsAffected = psPedido.executeUpdate();

	        if (rowsAffected > 0) {
	            
	            ResultSet rs = psPedido.getGeneratedKeys();
	            if (rs.next()) {
	                int idPedido = rs.getInt(1);

	               
	                try (PreparedStatement psAluguel = connection.prepareStatement(sqlUpdateAluguel)) {
	                    for (Aluguel aluguel : pedido.getAluguel()) {
	                        psAluguel.setInt(1, idPedido);
	                        psAluguel.setInt(2, aluguel.getIdAluguel());
	                        psAluguel.addBatch();
	                    }
	                    psAluguel.executeBatch();
	                }
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	 public int criarPedido() {
		 String query = "INSERT INTO pedido (idCliente, dtPedido, statusPedido) VALUES (?, ?, ?)";
	        int idPedido = -1;
	        int idCliente = 1;
	        String statusPedido = "em andamento";
	        LocalDate dtPedido = LocalDate.now();
	   

	        
	        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            stmt.setInt(1, idCliente);
	            stmt.setDate(2, Date.valueOf(dtPedido)); ;  
	            stmt.setString(3, statusPedido);
	            
	            int affectedRows = stmt.executeUpdate(); 

	            if (affectedRows > 0) {
	                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                        idPedido = generatedKeys.getInt(1);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return idPedido; 
	    }
	 
	 public int atualizarStatusPedido(int idPedido) {
		    try {
		        
		        String sql = "UPDATE pedido SET statusPedido = ? WHERE idPedido = ?";
		        
		        
		        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
		            
		            stmt.setString(1, "CONCLUIDO");
		            stmt.setInt(2, idPedido);
		            
		           
		            int sucesso = stmt.executeUpdate();
		            
		            if (sucesso > 0) {
		                System.out.println("Atualização realizada com sucesso!");
		                return 0;
		            } else {
		                System.out.println("Nenhum registro foi atualizado.");
		                return -1;
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1;
		    }
		}

	

}
