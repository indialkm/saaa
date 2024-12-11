package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonSyntaxException;

import DAO.AluguelDAO;
import DAO.PedidoDAO;
import DAO.ReservasDAO;

/**
 * Servlet implementation class PagamentoServelet
 */
@WebServlet("/pagamentos")
public class PagamentoServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagamentoServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            System.out.println("JSON Recebido: " + json);

            com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();
            int idPedido = jsonObject.get("idPedido").getAsInt();

            System.out.println("idPedido: " + idPedido);

            if (idPedido == 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Parâmetros obrigatórios estão ausentes\"}");
                return;
            }

            PedidoDAO pedidoBanco = new PedidoDAO();
            int pedido = pedidoBanco.atualizarStatusPedido(idPedido);

            
            if (pedido == 0) {
                System.out.println("Atualização realizada com sucesso!");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Status do pedido e reserva atualizados com sucesso\"}");
            } else {
                System.out.println("Falha ao atualizar pedido.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"Erro ao atualizar pedido\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();  
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Erro no servidor\"}");
        }
    }




	
	

}
