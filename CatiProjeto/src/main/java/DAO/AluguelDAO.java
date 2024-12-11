package DAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import models.Aluguel;
import models.Equipamento;
import models.Estoque;
import models.Pedidos;
import models.Reservas;
import models.StatusAluguel;
import models.StatusReserva;

public class AluguelDAO {

	private Connection connection;
	DBConnection dbConnection = new DBConnection();

	public AluguelDAO() {
		this.connection = dbConnection.getConnection();
	}
	
	public int atualizarStatusAluguel(int idAluguel) {
		try {
			String sql = "UPDATE status FROM aluguel Where idAluguel = ?";
			PreparedStatement ps= connection.prepareStatement(sql);
			ps.setInt(1, idAluguel);
			
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, StatusAluguel.pago.name());
			int sucesso = stmt.executeUpdate();
			
				if(sucesso > 0) {
					 System.out.println("Atualização realizada com sucesso!");
					 return 0;
				}else {
					 System.out.println("Nenhum registro foi atualizado.");
					 return -1;
				}
			
			}
	
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}

	}
	

	public int consultarPedidoPorAluguel(int idAluguel) {
		try {
			String sqlPedido = "SELECT idPedido FROM aluguel Where idAluguel = ?";
			PreparedStatement psPedido = connection.prepareStatement(sqlPedido);
			psPedido.setInt(1, idAluguel);
			ResultSet rs = psPedido.executeQuery();
			
			int pedido = 0;
			if (rs.next()) {
				pedido = rs.getInt("idPedido");
			}
			
			return pedido;
			
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public Pedidos buscarPorId(int idPedido) {
        Pedidos pedido = null;
        String queryPedido = "SELECT * FROM pedido WHERE idPedido = ?";
        String queryAluguels = "SELECT * FROM aluguel WHERE idPedido = ?";

        try (PreparedStatement stmtPedido = connection.prepareStatement(queryPedido)) {
            stmtPedido.setInt(1, idPedido);

            try (ResultSet rsPedido = stmtPedido.executeQuery()) {
                if (rsPedido.next()) {
                    pedido = new Pedidos();
                    pedido.setIdPedido(rsPedido.getInt("idPedido"));

                    List<Aluguel> aluguels = new ArrayList<>();
                    try (PreparedStatement stmtAluguels = connection.prepareStatement(queryAluguels)) {
                        stmtAluguels.setInt(1, idPedido);

                        try (ResultSet rsAluguels = stmtAluguels.executeQuery()) {
                            while (rsAluguels.next()) {
                                Aluguel aluguel = new Aluguel();
                                aluguel.setIdAluguel(rsAluguels.getInt("idAluguel")); 
                                Estoque estoque = new Estoque();
                                estoque.setIdEstoque(rsAluguels.getInt("idEstoque")); 
                                
                                aluguel.setEstoque(estoque);
                                aluguel.setPedido(pedido);
                                aluguel.setStatus(StatusAluguel.valueOf(rsAluguels.getString("status")));
                                aluguel.setValorTotal(rsAluguels.getDouble("valorTotal"));
                                aluguel.setDiasAluguel(rsAluguels.getInt("diasAluguel"));
                                aluguels.add(aluguel);
                            }
                        }
                    }
                    pedido.setAluguel(aluguels);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

	
	
	public Aluguel calcularValorTotalAluguel(int idEstoque, LocalDate dataInicio, LocalDate dataFim) {
		try {
			String sqlPreco = "SELECT precoVenda FROM estoque WHERE idEstoque = ?";
			PreparedStatement psPreco = connection.prepareStatement(sqlPreco);
			psPreco.setInt(1, idEstoque);
			ResultSet rs = psPreco.executeQuery();

			double precoVenda = 0;
			if (rs.next()) {
				precoVenda = rs.getDouble("precoVenda");
			}

			long diasAluguel = ChronoUnit.DAYS.between(dataInicio, dataFim);

			double valorTotal = diasAluguel * precoVenda;

			Aluguel aluguel = new Aluguel();
			aluguel.setValorTotal(valorTotal);
			aluguel.setDiasAluguel((int) diasAluguel);

			return aluguel;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean atualizarEndereco(int idAluguel, String entregaEndereco, String entregaNumero, String entregaCompl, 
            String entregaBairro, String entregaCidade, String entregaUF, String entregaCEP, 
            String entregaTelefone, String entregaRefer) {
				String sql = "UPDATE aluguel SET "
				+ "entregaEndereco = ?, "
				+ "entregaNumero = ?, "
				+ "entregaCompl = ?, "
				+ "entregaBairro = ?, "
				+ "entregaCidade = ?, "
				+ "entregaUF = ?, "
				+ "entregaCEP = ?, "
				+ "entregaTelefone = ?, "
				+ "entregaRefer = ? "
				+ "WHERE idAluguel = ?";
				
				try (PreparedStatement ps = connection.prepareStatement(sql)) {
				
				ps.setString(1, entregaEndereco);
				ps.setString(2, entregaNumero);
				ps.setString(3, entregaCompl);
				ps.setString(4, entregaBairro);
				ps.setString(5, entregaCidade);
				ps.setString(6, entregaUF);
				ps.setString(7, entregaCEP);
				ps.setString(8, entregaTelefone);
				ps.setString(9, entregaRefer);
				
				
				ps.setInt(10, idAluguel);
				
				
				int rowsAffected = ps.executeUpdate();
				return rowsAffected > 0;
				} catch (SQLException e) {
				e.printStackTrace();
				return false;
				}
		}
	
	public int inserirAluguel(int idEstoque, int idPedido, double valorTotal, int dias) throws SQLException {
	    String sql = "INSERT INTO aluguel (idEstoque, idPedido, valorTotal, diasAluguel) VALUES (?, ?, ?, ?)"; 
	    try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        ps.setInt(1, idEstoque);  
	        ps.setInt(2, idPedido);   
	        ps.setDouble(3, valorTotal);
	        ps.setInt(4, dias);

	        int affectedRows = ps.executeUpdate();  
	        if (affectedRows > 0) { 
	            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);  
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1;  
	}
	
	public Aluguel listarAluguelPorId(int idAluguel) {
	    String query = "SELECT * FROM aluguel WHERE idAluguel = ?";

	    Aluguel aluguel = null;
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setInt(1, idAluguel);  // Substitui '?' pelo idAluguel fornecido
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                aluguel = new Aluguel();
	                aluguel.setIdAluguel(rs.getInt("idAluguel"));
	                
	                Pedidos pedido = new Pedidos();
	                pedido.setIdPedido(rs.getInt("idPedido"));
	                aluguel.setPedido(pedido);
	  
	                Estoque estoque = new Estoque();
	                estoque.setIdEstoque(rs.getInt("idEstoque"));
	                aluguel.setEstoque(estoque);
	                
	               
	                aluguel.setStatus(StatusAluguel.valueOf(rs.getString("status")));
	                aluguel.setEntregaEndereco(rs.getString("entregaendereco"));
	                aluguel.setEntregaNumero(rs.getString("entregaNumero"));
	                aluguel.setEntregaCompl(rs.getString("entregaCompl"));
	                aluguel.setEntregaBairro(rs.getString("entregaBairro"));
	                aluguel.setEntregaCidade(rs.getString("entregaCidade"));
	                aluguel.setEntregaUF(rs.getString("entregaUF"));
	                aluguel.setEntregaCEP(rs.getString("entregaCEP"));
	                aluguel.setEntregaTelefone(rs.getString("entregaTelefone"));
	                aluguel.setEntregaRefer(rs.getString("entregaRefer"));
	                aluguel.setValorTotal(rs.getDouble("valorTotal"));
	                aluguel.setDiasAluguel(rs.getInt("diasAluguel"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return aluguel;
	}



}
