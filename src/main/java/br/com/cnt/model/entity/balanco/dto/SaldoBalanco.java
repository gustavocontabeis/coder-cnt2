package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;
import java.util.Arrays;

import br.com.cnt.model.entity.balanco.Conta;

public class SaldoBalanco implements Serializable, Comparable<SaldoBalanco>{
	
	private static final long serialVersionUID = 1L;
	
	private Conta conta;
	private Integer exercicio;
	private SaldoExercicio[] saldos;
	
	public SaldoBalanco() {
		super();
	}
	
	public SaldoBalanco(Conta conta, Integer exercicio, SaldoExercicio[] saldos) {
		super();
		this.conta = conta;
		this.exercicio = exercicio;
		this.saldos = saldos;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Integer getExercicio() {
		return exercicio;
	}

	public void setExercicio(Integer exercicio) {
		this.exercicio = exercicio;
	}

	public SaldoExercicio[] getSaldos() {
		return saldos;
	}

	public void setSaldos(SaldoExercicio[] saldos) {
		this.saldos = saldos;
	}
	
	@Override
	public int compareTo(SaldoBalanco other) {
		return this.getConta().compareTo(other.getConta());
	}

	@Override
	public String toString() {
		return "SaldoBalanco [conta=" + conta + ", saldos=" + Arrays.toString(saldos) + "]";
	}

}
