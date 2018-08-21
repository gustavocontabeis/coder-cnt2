package br.com.cnt.model.entity.balanco.dto;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaOrigem;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.PlanoContas;

public class ValorContabilTest {

	@Test
	public void test() {
		
		Exercicio exercicio = new Exercicio();
		Empresa empresa = new Empresa();
		PlanoContas planoContas = new PlanoContas();
		
		Conta conta = new Conta(1L, "estrutura", 3, "nome", "descricao", ContaTipo.ANALITICA, ContaOrigem.DEVEDORA);
				
		ValorContabil valor = new ValorContabil(conta, exercicio.getAno(), new BigDecimal(123456.789));
		
		assertTrue("123.456,79 D".equals(valor.toString()));
	}

}
