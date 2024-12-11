package DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.Aluguel;
import models.Estoque;
import models.Reservas;
import models.StatusAluguel;
import models.StatusReserva;

public class ReservasDAO {

    private Connection connection;
    DBConnection dbConnection = new DBConnection();
    

    public ReservasDAO() {
        this.connection = dbConnection.getConnection();
    }
    public int visualizarIdAluguelPorIdReserva(int idReserva) {
        try {
           
            String sqlIdAluguel = "SELECT aluguel_id FROM reservas WHERE id = ?";
            
            
            PreparedStatement psIdAluguel = connection.prepareStatement(sqlIdAluguel);
            psIdAluguel.setInt(1, idReserva);
            
            
            ResultSet rs = psIdAluguel.executeQuery();
            
            
            if (rs.next()) {
                return rs.getInt("aluguel_id"); 
            } else {
                return 0; 
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        }
    }
    
    public Reservas buscarIdAluguel(int idAluguel) {
        try {
            
            String sqlIdReserva = "SELECT id, data_inicio, data_fim FROM reservas WHERE aluguel_id = ?";
            
            
            PreparedStatement psIdReserva = connection.prepareStatement(sqlIdReserva);
            psIdReserva.setInt(1, idAluguel);
            
            
            ResultSet rs = psIdReserva.executeQuery();
            
            if (rs.next()) {
               
                Reservas reserva = new Reservas();
               
                reserva.setId(rs.getInt("id"));
                reserva.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                reserva.setDataFim(rs.getDate("data_fim").toLocalDate());
                
                
                return reserva;
            } else {
                return null; 
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;         
            }
    }

    

    public boolean verificarDisponibilidade(int idEstoque, LocalDate dataInicio, LocalDate dataFim) {
        
        String sql = """
                SELECT COUNT(*) AS total
                FROM reservas r
                INNER JOIN aluguel a ON r.aluguel_id = a.idAluguel
                INNER JOIN pedido p ON a.idPedido = p.idPedido
                INNER JOIN estoque e ON a.idEstoque = e.idEstoque
                WHERE e.idEstoque = ? 
                  AND p.statusPedido = 'concluido'
                  AND (
                      (r.data_inicio <= ? AND r.data_fim >= ?) OR
                      (r.data_inicio >= ? AND r.data_inicio <= ?)
                  )
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEstoque); 
            stmt.setDate(2, Date.valueOf(dataFim));   
            stmt.setDate(3, Date.valueOf(dataInicio)); 
            stmt.setDate(4, Date.valueOf(dataInicio)); 
            stmt.setDate(5, Date.valueOf(dataFim));    

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
               
                return total == 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar disponibilidade: " + e.getMessage());
            e.printStackTrace();
        }

        return false; 
    }
    
    
    public boolean salvarReserva(int idEstoque, LocalDate dataInicio, LocalDate dataFim, String status, int aluguelId) {
        String sql = "INSERT INTO reservas (data_inicio, data_fim, status, idEstoque, aluguel_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(dataInicio));  
            ps.setDate(2, Date.valueOf(dataFim));     
            ps.setString(3, status);                  
            ps.setInt(4, idEstoque);                  
            ps.setInt(5, aluguelId);                 
            
           
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  
        } catch (SQLException e) {
            e.printStackTrace();  
            return false;         
        }
    }
    
    

    public boolean atualizarStatus(int idReserva, StatusReserva status) {
        String sql = "UPDATE reservas SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, idReserva);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Reservas buscarPorId(int idReserva) {
        String sql = "SELECT id, data_inicio, data_fim, status FROM reservas WHERE id = ?";
        Reservas reserva = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                reserva = new Reservas();
                reserva.setId(rs.getInt("id"));
                reserva.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                reserva.setDataFim(rs.getDate("data_fim").toLocalDate());
                reserva.setStatus(StatusReserva.valueOf(rs.getString("status"))); // Verifique o status da reserva
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserva;
    }

    
    public List<Reservas> listarReservasPorAluguel(int idAluguel) {
        String query = "SELECT * FROM reserva WHERE aluguel_id = ?";

        List<Reservas> reservas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idAluguel);  
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservas reserva = new Reservas();
                    reserva.setId(rs.getInt("id"));
                    reserva.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                    reserva.setDataFim(rs.getDate("data_fim").toLocalDate());
                    reserva.setStatus(StatusReserva.valueOf(rs.getString("status")));
                    
                    Estoque estoque = new Estoque();
                    estoque.setIdEstoque(rs.getInt("idEstoque"));
               
                    reserva.setEstoque(estoque);
                   
                    reservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }
    
	public int atualizarStatusReservas(int idReserva) {
		try {
			String sql = "UPDATE status FROM reservas Where id = ?";
			PreparedStatement ps= connection.prepareStatement(sql);
			ps.setInt(1, idReserva);
			
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, StatusReserva.confirmada.name());
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

    
}
