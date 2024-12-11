package controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import DAO.PedidoDAO;
import DAO.ReservasDAO;
import DAO.AluguelDAO;
import DAO.EquipamentoDAO;
import models.Aluguel;
import models.Equipamento;
import models.Pedidos;
import models.Reservas;


/**
 * Servlet implementation class PedidoServlet
 */
@WebServlet("/pedidos")
public class PedidoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PedidoServlet() {
		super();

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action  = request.getParameter("action");
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	
		if("detalhes".equals(action)) { 
		int idAluguel = Integer.parseInt(request.getParameter("idAluguel"));
		
		AluguelDAO aluguelBanco = new AluguelDAO();
		int idPedido = aluguelBanco.consultarPedidoPorAluguel(idAluguel);
		
		Pedidos pedido = aluguelBanco.buscarPorId(idPedido);
		
		JsonObject responseJson = new JsonObject();
        responseJson.addProperty("idPedido", pedido.getIdPedido());
        JsonArray aluguelsArray = new JsonArray();
        EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
        
        for (Aluguel aluguel : pedido.getAluguel()) {
            JsonObject aluguelJson = new JsonObject();
            aluguelJson.addProperty("idEstoque", aluguel.getEstoque().getIdEstoque());
            aluguelJson.addProperty("valorTotal", aluguel.getValorTotal());
            aluguelJson.addProperty("quantidadeDias", aluguel.getDiasAluguel());
            
            ReservasDAO reserva = new ReservasDAO();
            Reservas reservaO = new Reservas();
            reservaO = reserva.buscarIdAluguel(idAluguel);
            
            JsonObject reservasJson = new JsonObject();
            if(reservaO != null) {
            	reservasJson.addProperty("idReserva", reservaO.getId());
            	reservasJson.addProperty("dataInicio", reservaO.getDataInicio().toString());
            	reservasJson.addProperty("dataFim", reservaO.getDataFim().toString());
            }
            
            EquipamentoDAO equipamentoBanco = new EquipamentoDAO();
            Equipamento equipamento = equipamentoDAO.listarPorId(aluguel.getEstoque().getIdEstoque());

           
            JsonObject equipamentoJson = new JsonObject();
            if (equipamento != null) {
                equipamentoJson.addProperty("idEquipamento", equipamento.getIdEquipamento());
                equipamentoJson.addProperty("nome", equipamento.getNome());
                equipamentoJson.addProperty("descricao", equipamento.getDescricao());
                equipamentoJson.addProperty("preco", equipamento.getPreco());
            }
            
            aluguelJson.add("reserva", reservasJson);
            
            aluguelJson.add("equipamento", equipamentoJson);

            
            aluguelsArray.add(aluguelJson);
        }

        
        responseJson.add("aluguels", aluguelsArray);

        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        
        response.getWriter().write(responseJson.toString());
    }

		
		}

	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			StringBuilder jsonBuilder = new StringBuilder();
			try (BufferedReader reader = request.getReader()) {
				String line;
				while ((line = reader.readLine()) != null) {
					jsonBuilder.append(line);
				}
			}

			 Gson gson = new GsonBuilder()
	                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")  
	                    .create();
			 
			Pedidos pedido = gson.fromJson(jsonBuilder.toString(), Pedidos.class);

		
			PedidoDAO pedidoDAO = new PedidoDAO();

			
			boolean sucesso = pedidoDAO.salvarPedido(pedido);

			if (sucesso) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("Pedido salvo com sucesso");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Erro ao salvar pedido");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Erro no servidor");
		}
	}
	

}
