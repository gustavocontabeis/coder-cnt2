package br.com.cnt.model.entity.balanco;

/**
 * 	ANALITICA("A"), SINTETICA("S");
 * @author gustavo
 */
public enum ContaTipo {
	
	ANALITICA("A"), SINTETICA("S");
	
	String codigo;
	
	private ContaTipo(String codigo){
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

}