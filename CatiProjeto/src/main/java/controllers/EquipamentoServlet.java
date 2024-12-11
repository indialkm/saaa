package controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import DAO.EquipamentoDAO;
import models.Equipamento;

/**
 * Servlet implementation class EquipamentoServlet
 */
@WebServlet("/equipamentos")
public class EquipamentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EquipamentoServlet() {
        super();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String idEquipamento = request.getParameter("id");

        if (idEquipamento != null) {
            
            listarPorId(request, response, Integer.parseInt(idEquipamento));
        } else {
            
            listarTodos(request, response);
        }
    }

    
    private void listarTodos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
        List<Equipamento> equipamentos = equipamentoDAO.listarTodos();

       
        String json = new Gson().toJson(equipamentos);

        
        response.getWriter().write(json);
        System.out.println("Requisição de todos os equipamentos recebida");
    }

    
    private void listarPorId(HttpServletRequest request, HttpServletResponse response, int idEquipamento) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
        Equipamento equipamento = equipamentoDAO.listarPorId(idEquipamento);

        
        if (equipamento == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"message\": \"Equipamento não encontrado\"}");
        } else {
            
            String json = new Gson().toJson(equipamento);
            response.getWriter().write(json);
            System.out.println("Requisição de equipamento com ID " + idEquipamento + " recebida");
        }
    }
}