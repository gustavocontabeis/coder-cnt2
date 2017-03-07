package br.com.cnt.model.entity.balanco;


public enum LancamentoTipo {
	
	SIMPLES("S"), COMPOSTO("C");
	
	String codigo;
	private LancamentoTipo(String codigo) {
		this.codigo = codigo;
	}

}