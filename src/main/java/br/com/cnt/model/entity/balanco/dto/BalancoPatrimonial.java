package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.cnt.model.entity.balancete.dto.SaldoContabil;
import br.com.cnt.model.entity.balanco.Empresa;

public class BalancoPatrimonial implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Empresa empresa;
	
	private List<LinhaBalanco> list = new ArrayList<>();
	private List<SaldoBalanco> saldos = new ArrayList<>();

	@Deprecated
	public void addLinha(SaldoContabil saldoContabilAtivo, SaldoContabil saldoContabilPassivo) {
		list.add(new LinhaBalanco(saldoContabilAtivo, saldoContabilPassivo));
	}

	public void addLinha(SaldoBalanco saldoContabilAtivo, SaldoBalanco saldoContabilPassivo) {
		list.add(new LinhaBalanco(saldoContabilAtivo, saldoContabilPassivo));
	}

	public List<LinhaBalanco> getList() {
		return list;
	}

	public void setList(List<LinhaBalanco> list) {
		this.list = list;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<SaldoBalanco> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<SaldoBalanco> saldos) {
		this.saldos = saldos;
	}
	
}
