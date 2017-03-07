package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaOrigem;
import br.com.cnt.model.utils.ContaUtil;

public class SaldoContabil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Conta conta;
	private BigDecimal saldoInicial, debito, credito, saldoFinal;
	
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	public BigDecimal getDebito() {
		return debito;
	}
	public void setDebito(BigDecimal debito) {
		this.debito = debito;
	}
	public BigDecimal getCredito() {
		return credito;
	}
	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}
	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	/* ================================== */
	private ContaOrigem origem(BigDecimal valor) {
		if(valor.floatValue() < 0f ){
			if(conta.getContaOrigem() == ContaOrigem.DEVEDORA){
				return ContaOrigem.CREDORA;
			}else{
				return ContaOrigem.DEVEDORA;
			}
		}
		return conta.getContaOrigem();
	}
	public String getSaldoInicialContabil() {
		return ContaUtil.getValorContabil(saldoInicial, conta.getContaOrigem());
	}
	public String getDebitoContabil() {
		return ContaUtil.getValorContabil(debito, conta.getContaOrigem());
	}
	public String getCreditoContabil() {
		return ContaUtil.getValorContabil(credito, conta.getContaOrigem());
	}
	public String getSaldoFinalContabil() {
		return ContaUtil.getValorContabil(saldoFinal, conta.getContaOrigem());
	}
	/* ================================== */
	public boolean isSaldoInicialPositivo() {
		return ContaUtil.isValorContabilPositivo(saldoInicial.floatValue(), conta.getContaOrigem());
	}
	public boolean isDebitoPositivo() {
		return ContaUtil.isValorContabilPositivo(debito.floatValue(), conta.getContaOrigem());
	}
	public boolean isCreditoPositivo() {
		return ContaUtil.isValorContabilPositivo(credito.floatValue(), conta.getContaOrigem());
	}
	public boolean isSaldoFinalPositivo() {
		return ContaUtil.isValorContabilPositivo(saldoFinal.floatValue(), conta.getContaOrigem());
	}
	@Override
	public String toString() {
		return "SaldoContabil [conta=" + conta + ", saldoInicial=" + saldoInicial + ", debito=" + debito + ", credito="
				+ credito + ", saldoFinal=" + saldoFinal + "]";
	}

}
