package br.com.cnt.model.entity.balanco;


public enum ContaOrigem {
	
	DEVEDORA("D"), CREDORA("C");
	
	public String codigo;
	
	ContaOrigem(String codigo){
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

}