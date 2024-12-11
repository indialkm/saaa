package models;

import java.util.List;

public class Aluguel {

	private int idAluguel; 
	private Pedidos pedido; 
	private Estoque estoque; 
	private Reservas reserva; 
	private StatusAluguel status; 

	
	private String entregaEndereco;
	private String entregaNumero;
	private String entregaCompl;
	private String entregaBairro;
	private String entregaCidade;
	private String entregaUF;
	private String entregaCEP;
	private String entregaTelefone;
	private String entregaRefer;

	
	private double valorTotal; 
	private int diasAluguel; 
	
	public int getIdAluguel() {
		return idAluguel;
	}
	public void setIdAluguel(int idAluguel) {
		this.idAluguel = idAluguel;
	}
	public Pedidos getPedido() {
		return pedido;
	}
	public void setPedido(Pedidos pedido) {
		this.pedido = pedido;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Reservas getReserva() {
		return reserva;
	}
	public void setReserva(Reservas reserva) {
		this.reserva = reserva;
	}
	public StatusAluguel getStatus() {
		return status;
	}
	public void setStatus(StatusAluguel status) {
		this.status = status;
	}
	public String getEntregaEndereco() {
		return entregaEndereco;
	}
	public void setEntregaEndereco(String entregaEndereco) {
		this.entregaEndereco = entregaEndereco;
	}
	public String getEntregaNumero() {
		return entregaNumero;
	}
	public void setEntregaNumero(String entregaNumero) {
		this.entregaNumero = entregaNumero;
	}
	public String getEntregaCompl() {
		return entregaCompl;
	}
	public void setEntregaCompl(String entregaCompl) {
		this.entregaCompl = entregaCompl;
	}
	public String getEntregaBairro() {
		return entregaBairro;
	}
	public void setEntregaBairro(String entregaBairro) {
		this.entregaBairro = entregaBairro;
	}
	public String getEntregaCidade() {
		return entregaCidade;
	}
	public void setEntregaCidade(String entregaCidade) {
		this.entregaCidade = entregaCidade;
	}
	public String getEntregaUF() {
		return entregaUF;
	}
	public void setEntregaUF(String entregaUF) {
		this.entregaUF = entregaUF;
	}
	public String getEntregaCEP() {
		return entregaCEP;
	}
	public void setEntregaCEP(String entregaCEP) {
		this.entregaCEP = entregaCEP;
	}
	public String getEntregaTelefone() {
		return entregaTelefone;
	}
	public void setEntregaTelefone(String entregaTelefone) {
		this.entregaTelefone = entregaTelefone;
	}
	public String getEntregaRefer() {
		return entregaRefer;
	}
	public void setEntregaRefer(String entregaRefer) {
		this.entregaRefer = entregaRefer;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public int getDiasAluguel() {
		return diasAluguel;
	}
	public void setDiasAluguel(int diasAluguel) {
		this.diasAluguel = diasAluguel;
	}

	
	

}
