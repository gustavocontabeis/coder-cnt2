package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;

public class SaldoExercicio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer ano;
	private ValorContabil valor;
	
	public SaldoExercicio() {
		super();
	}
	public SaldoExercicio(Integer ano, ValorContabil valor) {
		super();
		this.ano = ano;
		this.valor = valor;
	}
	
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public ValorContabil getValor() {
		return valor;
	}
	public void setValor(ValorContabil valor) {
		this.valor = valor;
	}

}
