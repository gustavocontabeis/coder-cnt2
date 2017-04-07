package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;

import br.com.cnt.model.entity.balancete.dto.SaldoContabil;

public class LinhaBalanco implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Deprecated
	private SaldoContabil ativo;
	@Deprecated
	private SaldoContabil passivo;
	
	private SaldoBalanco ativo2;
	private SaldoBalanco passivo2;
	
	@Deprecated
	public LinhaBalanco(SaldoContabil saldoContabilAtivo, SaldoContabil saldoContabilPassivo) {
		ativo = saldoContabilAtivo;
		passivo = saldoContabilPassivo;
	}
	
	public LinhaBalanco(SaldoBalanco saldoContabilAtivo, SaldoBalanco saldoContabilPassivo) {
		ativo2 = saldoContabilAtivo;
		passivo2 = saldoContabilPassivo;
	}
	
	@Deprecated
	public SaldoContabil getAtivo() {
		return ativo;
	}
	@Deprecated
	public void setAtivo(SaldoContabil ativo) {
		this.ativo = ativo;
	}
	@Deprecated
	public SaldoContabil getPassivo() {
		return passivo;
	}
	@Deprecated
	public void setPassivo(SaldoContabil passivo) {
		this.passivo = passivo;
	}

	public SaldoBalanco getAtivo2() {
		return ativo2;
	}

	public void setAtivo2(SaldoBalanco ativo2) {
		this.ativo2 = ativo2;
	}

	public SaldoBalanco getPassivo2() {
		return passivo2;
	}

	public void setPassivo2(SaldoBalanco passivo2) {
		this.passivo2 = passivo2;
	}
	
}
