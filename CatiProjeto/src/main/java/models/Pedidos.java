package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Pedidos {

	private int idPedido;
	private Cliente cliente;
	private List<Aluguel> aluguel;
	private LocalDateTime dtPedido;
	private LocalDateTime dtPagamento;
	private LocalDateTime dtNotaFiscal;
	private String notaFiscal;
	private LocalDateTime dtEnvio;
	private LocalDateTime dtRecebimento;
	private int tipoFrete;
	private StatusPedido statusPedido;
	private Float valorTotal;
	private int qtdItems;
	private LocalDateTime dtCancelamento;
	private String motivoCancelamento;

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getDtPedido() {
		return dtPedido;
	}

	public void setDtPedido(LocalDateTime dtPedido) {
		this.dtPedido = dtPedido;
	}

	public LocalDateTime getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(LocalDateTime dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public LocalDateTime getDtNotaFiscal() {
		return dtNotaFiscal;
	}

	public void setDtNotaFiscal(LocalDateTime dtNotaFiscal) {
		this.dtNotaFiscal = dtNotaFiscal;
	}

	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public LocalDateTime getDtEnvio() {
		return dtEnvio;
	}

	public void setDtEnvio(LocalDateTime dtEnvio) {
		this.dtEnvio = dtEnvio;
	}

	public LocalDateTime getDtRecebimento() {
		return dtRecebimento;
	}

	public void setDtRecebimento(LocalDateTime dtRecebimento) {
		this.dtRecebimento = dtRecebimento;
	}

	public int getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(int tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public Float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public int getQtdItems() {
		return qtdItems;
	}

	public void setQtdItems(int qtdItems) {
		this.qtdItems = qtdItems;
	}

	public LocalDateTime getDtCancelamento() {
		return dtCancelamento;
	}

	public void setDtCancelamento(LocalDateTime dtCancelamento) {
		this.dtCancelamento = dtCancelamento;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public List<Aluguel> getAluguel() {
		return aluguel;
	}

	public void setAluguel(List<Aluguel> aluguel) {
		this.aluguel = aluguel;
	}

}
