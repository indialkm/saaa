package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import DAO.AluguelDAO;
import models.Aluguel;

@WebServlet("/alugueis")
public class AluguelServlet extends HttpServlet {

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    try {
	    	String action  = request.getParameter("action");
	    	response.setContentType("application/json");
	    	response.setCharacterEncoding("UTF-8");
	    	
	    	if("verificar".equals(action)) {
	    		int idAluguel = Integer.parseInt(request.getParameter("idAluguel"));
	    		
	    		AluguelDAO aluguel = new AluguelDAO();
	    		
	    		int idPedido = aluguel.consultarPedidoPorAluguel(idAluguel);
	    		
	    		JsonObject responseJson = new JsonObject();
	    		responseJson.addProperty("idAluguel", idPedido);
	    		response.getWriter().write(responseJson.toString());
	    		
	    		System.out.println(idPedido);
	    		
	    		if (idAluguel != -1) {
		            response.setStatus(HttpServletResponse.SC_OK);
		            

		        } else {
		            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		            response.getWriter().write("{\"reservaCriada\": false, \"message\": \"Erro ao criar reserva.\"}");
		        }
	    	}
	    	
	    	
	    	
	    	
	    	
	    }catch (Exception e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("{\"message\":\"Erro ao processar a solicitação: " + e.getMessage() + "\"}");
	        e.printStackTrace();
	    }
	
	
	
	}
	
	
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
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

	        int idAluguel = jsonObject.get("idAluguel").getAsInt(); 

	        String entregaEndereco = jsonObject.get("entregaEndereco").getAsString();
	        String entregaNumero = jsonObject.get("entregaNumero").getAsString();
	        String entregaCompl = jsonObject.get("entregaCompl").getAsString();
	        String entregaBairro = jsonObject.get("entregaBairro").getAsString();
	        String entregaCidade = jsonObject.get("entregaCidade").getAsString();
	        String entregaUF = jsonObject.get("entregaUF").getAsString();
	        String entregaCEP = jsonObject.get("entregaCEP").getAsString();
	        String entregaTelefone = jsonObject.get("entregaTelefone").getAsString();
	        String entregaRefer = jsonObject.get("entregaRefer").getAsString();

	        AluguelDAO aluguelDAO = new AluguelDAO();
	        boolean sucesso = aluguelDAO.atualizarEndereco(idAluguel, entregaEndereco, entregaNumero, entregaCompl, 
	                                                       entregaBairro, entregaCidade, entregaUF, entregaCEP, 
	                                                       entregaTelefone, entregaRefer);

	        if (sucesso) {
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().write("{\"message\":\"Endereço atualizado com sucesso!\"}");
	        } else {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("{\"message\":\"Erro ao atualizar o endereço. Verifique os dados e tente novamente.\"}");
	        }
	    } catch (Exception e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("{\"message\":\"Erro ao processar a solicitação: " + e.getMessage() + "\"}");
	        e.printStackTrace();
	    }
	}

	}

