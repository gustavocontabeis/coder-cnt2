package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.cnt.model.utils.ContaUtil;

public class SaldoRazao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idLancamento;
	private Date data;
	private String historico;
	private BigDecimal vlrDebito, vlrCredito;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public BigDecimal getVlrDebito() {
		return vlrDebito;
	}
	public void setVlrDebito(BigDecimal vlrDebito) {
		this.vlrDebito = vlrDebito;
	}
	public BigDecimal getVlrCredito() {
		return vlrCredito;
	}
	public void setVlrCredito(BigDecimal vlrCredito) {
		this.vlrCredito = vlrCredito;
	}
	public Long getIdLancamento() {
		return idLancamento;
	}
	public void setIdLancamento(Long idLancamento) {
		this.idLancamento = idLancamento;
	}
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}

}
