package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;

import br.com.cnt.model.entity.balancete.dto.SaldoContabil;

public class LinhaBalanco implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SaldoContabil ativo;
	private SaldoContabil passivo;
	
	public LinhaBalanco(SaldoContabil saldoContabilAtivo, SaldoContabil saldoContabilPassivo) {
		ativo = saldoContabilAtivo;
		passivo = saldoContabilPassivo;
	}
	
	public SaldoContabil getAtivo() {
		return ativo;
	}
	public void setAtivo(SaldoContabil ativo) {
		this.ativo = ativo;
	}
	public SaldoContabil getPassivo() {
		return passivo;
	}
	public void setPassivo(SaldoContabil passivo) {
		this.passivo = passivo;
	}
}
