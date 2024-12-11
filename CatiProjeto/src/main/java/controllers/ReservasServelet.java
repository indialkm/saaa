package controllers;

import com.google.gson.JsonObject;

import DAO.AluguelDAO;
import DAO.PedidoDAO;
import DAO.ReservasDAO;
import models.Aluguel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/reservas")
public class ReservasServelet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action  = request.getParameter("action");
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	
		if("verificar".equals(action)) { // Getmapping tá diferente, vencendo o servlet 
		int idEstoque = Integer.parseInt(request.getParameter("idEstoque"));
		String dataInicioStr = request.getParameter("dataInicio");
		String dataFimStr = request.getParameter("dataFim");

		LocalDate dataInicio = LocalDate.parse(dataInicioStr);
		LocalDate dataFim = LocalDate.parse(dataFimStr);

		ReservasDAO reservasDAO = new ReservasDAO();
		boolean isDisponivel = reservasDAO.verificarDisponibilidade(idEstoque, dataInicio, dataFim);
		AluguelDAO aluguel = new AluguelDAO();
		Aluguel aluguelobj = new Aluguel();
		
		aluguelobj = aluguel.calcularValorTotalAluguel(idEstoque, dataInicio, dataFim);

		JsonObject responseJson = new JsonObject();
		responseJson.addProperty("idEstoque", idEstoque);
		responseJson.addProperty("dataInicio", dataInicioStr);
		responseJson.addProperty("dataFim", dataFimStr);
		responseJson.addProperty("disponivel", isDisponivel);
		responseJson.addProperty("valorTotal", aluguelobj.getValorTotal());
		responseJson.addProperty("quantidadeDias", aluguelobj.getDiasAluguel());
	
		//System.out.println("Valor Total: " + aluguelobj.getValorTotal());
		//System.out.println("Quantidade de Dias: " + aluguelobj.getDiasAluguel());
	

		response.getWriter().write(responseJson.toString());
		}
		else if ("detalhes".equals(action)) {

			int idEstoque = Integer.parseInt(request.getParameter("idEstoque"));
			
			ReservasDAO reservasDAO = new ReservasDAO();
			
			int idAluguel = reservasDAO.visualizarIdAluguelPorIdReserva(idEstoque);

			if (idAluguel == 0) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().write("Nenhum aluguel encontrado para o idEstoque informado.");
				return;
			}
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("idAluguel", idAluguel);
		} else {
			
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Parâmetro 'idEstoque' ausente ou inválido.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    try {
	        StringBuilder jsonBuilder = new StringBuilder();
	        try (BufferedReader reader = request.getReader()) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                jsonBuilder.append(line);
	            }
	        }

	        String json = jsonBuilder.toString();
	        com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();

	        int idEstoque = jsonObject.get("idEstoque").getAsInt();
	        LocalDate dataInicio = LocalDate.parse(jsonObject.get("dataInicio").getAsString());
	        LocalDate dataFim = LocalDate.parse(jsonObject.get("dataFim").getAsString());

	        
	        ReservasDAO reservasDAO = new ReservasDAO();
	        boolean dataBloqueada = reservasDAO.verificarDisponibilidade(idEstoque, dataInicio, dataFim);

	        if (!dataBloqueada) {
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().write("{\"reservaCriada\": false, \"message\": \"Datas de reserva estão bloqueadas para o equipamento.\"}");
	            return;
	        }

	        PedidoDAO pedidoDAO = new PedidoDAO();
	        int idPedido = pedidoDAO.criarPedido();  

	        if (idPedido == -1) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("{\"reservaCriada\": false, \"message\": \"Erro ao criar pedido.\"}");
	            return;
	        }

	        AluguelDAO aluguelDAO = new AluguelDAO();
	        Aluguel aluguel = new Aluguel();
	        aluguel = aluguelDAO.calcularValorTotalAluguel(idEstoque, dataInicio, dataFim);
	        double valorTotal = aluguel.getValorTotal();
	        int dias = aluguel.getDiasAluguel();
	        int idAluguel = aluguelDAO.inserirAluguel(idEstoque, idPedido, valorTotal, dias); 

	        if (idAluguel == -1) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("{\"reservaCriada\": false, \"message\": \"Erro ao criar aluguel.\"}");
	            return;
	        }


	        String status = "confirmada"; 
	        boolean sucesso = reservasDAO.salvarReserva(idEstoque, dataInicio, dataFim, status, idAluguel);

	        if (sucesso) {
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().write("{\"reservaCriada\": true, \"message\": \"Reserva criada com sucesso!\", \"idAluguel\": " + idAluguel + "}");

	        } else {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("{\"reservaCriada\": false, \"message\": \"Erro ao criar reserva.\"}");
	        }

	    } catch (Exception e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("{\"reservaCriada\": false, \"message\": \"Erro ao processar a solicitação.\"}");
	        e.printStackTrace();
	    }
	}




}
