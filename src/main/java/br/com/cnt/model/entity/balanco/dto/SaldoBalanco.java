package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;

import br.com.cnt.model.entity.balanco.Conta;

public class SaldoBalanco implements Serializable, Comparable<SaldoBalanco>{
	
	private static final long serialVersionUID = 1L;
	
	private Conta conta;
	private ValorContabil[] valores;
	
	public Conta getConta() {
		return conta;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public ValorContabil[] getValores() {
		return valores;
	}
	
	public void setValores(ValorContabil[] valores) {
		this.valores = valores;
	}

	@Override
	public int compareTo(SaldoBalanco other) {
		return this.getConta().compareTo(other.getConta());
	}

}
