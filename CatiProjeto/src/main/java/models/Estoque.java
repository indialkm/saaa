package models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Estoque {

	 private int idEstoque;
	 private List<Equipamento> equipamentos = new ArrayList<>();
	    private List<Aluguel> alugueis;
	    private LocalDate dtEntrada;
	    private int quantidade;
	    private LocalDate dtFabricacao;
	    private LocalDate dtVencimento;
	    private String nfCompra;
	    private Double precoCompra;
	    private Double icmsCompra;
	    private Double precoVenda;
	    private int qtdVendida;
		public int getIdEstoque() {
			return idEstoque;
		}
		public void setIdEstoque(int idEstoque) {
			this.idEstoque = idEstoque;
		}
		
		public List<Equipamento> getEquipamentos() {
			return equipamentos;
		}
		public void setEquipamentos(List<Equipamento> equipamentos) {
			this.equipamentos = equipamentos;
		}
		public List<Aluguel> getAlugueis() {
			return alugueis;
		}
		public void setAlugueis(List<Aluguel> alugueis) {
			this.alugueis = alugueis;
		}
		public LocalDate getDtEntrada() {
			return dtEntrada;
		}
		public void setDtEntrada(LocalDate dtEntrada) {
			this.dtEntrada = dtEntrada;
		}
		public int getQuantidade() {
			return quantidade;
		}
		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}
		public LocalDate getDtFabricacao() {
			return dtFabricacao;
		}
		public void setDtFabricacao(LocalDate dtFabricacao) {
			this.dtFabricacao = dtFabricacao;
		}
		public LocalDate getDtVencimento() {
			return dtVencimento;
		}
		public void setDtVencimento(LocalDate dtVencimento) {
			this.dtVencimento = dtVencimento;
		}
		public String getNfCompra() {
			return nfCompra;
		}
		public void setNfCompra(String nfCompra) {
			this.nfCompra = nfCompra;
		}
		public Double getPrecoCompra() {
			return precoCompra;
		}
		public void setPrecoCompra(Double precoCompra) {
			this.precoCompra = precoCompra;
		}
		public Double getIcmsCompra() {
			return icmsCompra;
		}
		public void setIcmsCompra(Double icmsCompra) {
			this.icmsCompra = icmsCompra;
		}
		public Double getPrecoVenda() {
			return precoVenda;
		}
		public void setPrecoVenda(Double precoVenda) {
			this.precoVenda = precoVenda;
		}
		public int getQtdVendida() {
			return qtdVendida;
		}
		public void setQtdVendida(int qtdVendida) {
			this.qtdVendida = qtdVendida;
		}
		
	 
		public void addEquipamento(Equipamento equipamento) {
	        this.equipamentos.add(equipamento);
	    }
	    
	    
}
