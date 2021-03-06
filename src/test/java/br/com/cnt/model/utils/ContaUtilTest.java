package br.com.cnt.model.utils;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaOrigem;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Empresa;

public class ContaUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetornarNivel() {
		assertTrue(1 == ContaUtil.retornarNivel(new Conta(1L, "1.0.0.00.00.00.00", 0, "nome", "descricao", ContaTipo.ANALITICA, ContaOrigem.CREDORA)));
		assertTrue(2 == ContaUtil.retornarNivel(new Conta(1L, "1.1.0.00.00.00.00", 0, "nome", "descricao", ContaTipo.ANALITICA, ContaOrigem.CREDORA)));
	}

	@Test
	public void testEstruturaSemZeros() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetValorContabil() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValorContabilPositivo() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompararNivel() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsFilho() {
		Conta conta1 = buildConta(1L, "1.1.01.01.01.00", 4, ContaTipo.ANALITICA);
		Conta conta2 = buildConta(2L, "1.1.01.01.01.00", 4, ContaTipo.ANALITICA);
		assertTrue(ContaUtil.compararNivel(conta1, conta2) == 0);
		
		conta1 = buildConta(1L, "1.1.01.01.01.00", 4, ContaTipo.ANALITICA);
		conta2 = buildConta(2L, "1.1.01.01.02.00", 4, ContaTipo.ANALITICA);
		assertTrue(ContaUtil.compararNivel(conta1, conta2) == 0);
		
		conta1 = buildConta(1L, "1.1.01.01.01.00", 4, ContaTipo.ANALITICA);
		conta2 = buildConta(2L, "1.1.01.01.01.01", 4, ContaTipo.ANALITICA);
		assertTrue(ContaUtil.compararNivel(conta1, conta2) == -1);
		
		conta1 = buildConta(1L, "1.1.01.01.01.00", 4, ContaTipo.ANALITICA);
		conta2 = buildConta(2L, "1.1.01.02.00.00", 4, ContaTipo.ANALITICA);
		assertTrue(ContaUtil.compararNivel(conta1, conta2) == 1);
	}

	private Conta buildConta(long id, String estrutura, int nuvel, ContaTipo tipo) {
		return new Conta(id, estrutura, 4, null, null, tipo, null);
	}

	@Test
	public void testIsPai() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRetornarEstruturaFilho() {
		assertTrue("1.1.0.00.00.00".equals(ContaUtil.retornarEstruturaFilho("1.0.0.00.00.00")));
		assertTrue("1.1.1.00.00.00".equals(ContaUtil.retornarEstruturaFilho("1.1.0.00.00.00")));
		assertTrue("2.1.1.00.00.00".equals(ContaUtil.retornarEstruturaFilho("2.1.0.00.00.00")));
		assertTrue("1.1.1.01.00.00".equals(ContaUtil.retornarEstruturaFilho("1.1.1.00.00.00")));
		assertTrue("1.1.1.03.01.00".equals(ContaUtil.retornarEstruturaFilho("1.1.1.03.00.00")));
	}

	@Test
	public void testRetornarEstruturaAbaixo() {
		assertTrue("2.0.0.00.00.00".equals(ContaUtil.retornarEstruturaAbaixo("1.0.0.00.00.00")));
		assertTrue("1.2.0.00.00.00".equals(ContaUtil.retornarEstruturaAbaixo("1.1.0.00.00.00")));
		assertTrue("2.2.0.00.00.00".equals(ContaUtil.retornarEstruturaAbaixo("2.1.0.00.00.00")));
		assertTrue("1.1.2.00.00.00".equals(ContaUtil.retornarEstruturaAbaixo("1.1.1.00.00.00")));
		assertTrue("1.1.1.04.00.00".equals(ContaUtil.retornarEstruturaAbaixo("1.1.1.03.00.00")));
	}

}
