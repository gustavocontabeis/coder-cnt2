package br.com.cnt.model.entity.balanco.dto;

import java.math.BigDecimal;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaOrigem;
import br.com.cnt.model.utils.ContaUtil;

/**
 * @author caixa
 *
 */
public class ValorContabil {
	
	private Conta conta;
	private Integer exercicio;
	private BigDecimal valor;
	
	public ValorContabil() {
		super();
	}
	
	public ValorContabil(Conta conta, Integer exercicio, BigDecimal valor) {
		super();
		this.conta = conta;
		this.exercicio = exercicio;
		this.valor = valor;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
	public boolean isPositivo(){
		return ContaUtil.isValorContabilPositivo(valor.floatValue(), conta.getContaOrigem());
	}
	
	public ContaOrigem getSinalContabil(BigDecimal valor) {
		if(valor.floatValue() < 0f ){
			if(conta.getContaOrigem() == ContaOrigem.DEVEDORA){
				return ContaOrigem.CREDORA;
			}else{
				return ContaOrigem.DEVEDORA;
			}
		}
		return conta.getContaOrigem();
	}

	@Override
	public String toString() {
		return ContaUtil.getValorContabil(valor, conta.getContaOrigem());
	}

}
