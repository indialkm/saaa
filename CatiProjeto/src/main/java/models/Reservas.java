package models;

import java.time.LocalDate;

public class Reservas {
    private int id;
    private Estoque estoque;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Aluguel aluguel;
    private StatusReserva status;  // Adicionando o campo 'status'

    // Getters e Setters para o campo 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters e Setters para o campo 'dataInicio'
    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    // Getters e Setters para o campo 'dataFim'
    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    // Getters e Setters para o campo 'aluguel'
    public Aluguel getAluguel() {
        return aluguel;
    }

    public void setAluguel(Aluguel aluguel) {
        this.aluguel = aluguel;
    }

	public StatusReserva getStatus() {
		return status;
	}

	public void setStatus(StatusReserva status) {
		this.status = status;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

    
}
