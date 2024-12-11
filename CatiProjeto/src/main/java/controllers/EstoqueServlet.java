package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import DAO.EstoqueDAO;
import models.Equipamento;

/**
 * Servlet implementation class EstoqueServlet
 */
@WebServlet("/estoques")
public class EstoqueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EstoqueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int idEquipamento = Integer.parseInt(request.getParameter("idEquipamento"));
       
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        
        int idEstoque = estoqueDAO.buscarIdEstoquePorEquipamento(idEquipamento);
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("idEstoque", idEstoque);
        
        response.setContentType("application/json");
        
        response.getWriter().write(jsonResponse.toString());
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
