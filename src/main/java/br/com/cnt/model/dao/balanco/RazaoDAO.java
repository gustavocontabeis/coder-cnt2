package br.com.cnt.model.dao.balanco;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.dto.Razao;
import br.com.cnt.model.entity.balanco.dto.SaldoRazao;
import br.com.cnt.model.utils.ContaUtil;

public class RazaoDAO implements Serializable { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Razao retornarRazao(Conta conta, Date de, Date ate){
		LancamentoDAO dao = new LancamentoDAO();
		
		SaldoRazao saldoInicial = dao.retornarSaldoInicialRazaoDebido(conta, de);
		SaldoRazao saldoInicialRazaoCredito = dao.retornarSaldoInicialRazaoCredito(conta, de);
		saldoInicial.setVlrCredito(saldoInicialRazaoCredito.getVlrCredito());
		
		List<SaldoRazao> saldosRazao = dao.retornarSaldosRazao(conta, de, ate);
		
		double valorSaldoFinal = saldoInicial.getVlrDebito().subtract(saldoInicial.getVlrCredito()).doubleValue();
		
		for (SaldoRazao saldoRazao : saldosRazao) {
			if(saldoRazao.getVlrDebito()==null){
				saldoRazao.setVlrDebito(BigDecimal.ZERO);
			}
			if(saldoRazao.getVlrCredito()==null){
				saldoRazao.setVlrCredito(BigDecimal.ZERO);
			}
			BigDecimal subtract = saldoRazao.getVlrDebito().subtract(saldoRazao.getVlrCredito());
			valorSaldoFinal += subtract.doubleValue();
		}
		
		Razao razao = new Razao();
		razao.setConta(conta);
		razao.setDe(de);
		razao.setAte(ate);
		razao.setSaldoInicial(saldoInicial);
		razao.setSaldosRazao(saldosRazao);
		razao.setSaldoFinal(new BigDecimal(valorSaldoFinal));
		return razao;
	}

}
