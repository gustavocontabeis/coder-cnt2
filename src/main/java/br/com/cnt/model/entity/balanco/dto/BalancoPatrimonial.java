package br.com.cnt.model.entity.balanco.dto;

import java.util.ArrayList;
import java.util.List;

public class BalancoPatrimonial {
	
	private List<LinhaBalanco> list = new ArrayList<>();

	public void addLinha(SaldoContabil saldoContabilAtivo, SaldoContabil saldoContabilPassivo) {
		list.add(new LinhaBalanco(saldoContabilAtivo, saldoContabilPassivo));
	}

	public List<LinhaBalanco> getList() {
		return list;
	}

	public void setList(List<LinhaBalanco> list) {
		this.list = list;
	}
	
}
