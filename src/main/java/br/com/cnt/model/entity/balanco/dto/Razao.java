package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaOrigem;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.utils.ContaUtil;

public class Razao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Exercicio exercicio;
	private Conta conta;
	private Date de, ate;
	private SaldoRazao saldoInicial;
	private BigDecimal saldoFinal;
	private List<SaldoRazao>saldosRazao;
	
	public String getSaldoInicialContabil(){
		BigDecimal subtract = saldoInicial.getVlrDebito().subtract(saldoInicial.getVlrCredito());
		return ContaUtil.getValorContabil(subtract, conta.getContaOrigem());
	}
	
	public boolean isSaldoInicialPositivo() {
		BigDecimal subtract = saldoInicial.getVlrDebito().subtract(saldoInicial.getVlrCredito());
		return ContaUtil.isValorContabilPositivo(subtract.floatValue(), conta.getContaOrigem());
	}
	
	public String getSaldoFinalContabil(){
		return ContaUtil.getValorContabil(saldoFinal, conta.getContaOrigem());
	}
	
	public boolean isSaldoFinalPositivo(){
		return ContaUtil.isValorContabilPositivo(saldoFinal.floatValue(), conta.getContaOrigem());
	}
	
	public Exercicio getExercicio() {
		return exercicio;
	}
	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public Date getDe() {
		return de;
	}
	public void setDe(Date de) {
		this.de = de;
	}
	public Date getAte() {
		return ate;
	}
	public void setAte(Date ate) {
		this.ate = ate;
	}
	public List<SaldoRazao> getSaldosRazao() {
		return saldosRazao;
	}
	public void setSaldosRazao(List<SaldoRazao> saldosRazao) {
		this.saldosRazao = saldosRazao;
	}
	public SaldoRazao getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(SaldoRazao saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

}
