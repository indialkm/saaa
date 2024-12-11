import DAO.ReservasDAO;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ReservasDAO reservaDao = new ReservasDAO();
		
		int idAluguel = reservaDao.visualizarIdAluguelPorIdReserva(1);
		
		System.out.println("O Id do aluguel é :" + idAluguel);

	}

}
