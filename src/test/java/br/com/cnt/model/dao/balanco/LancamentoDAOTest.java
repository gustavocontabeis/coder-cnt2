package br.com.cnt.model.dao.balanco;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.cnt.model.entity.balancete.dto.Balancete;
import br.com.cnt.model.entity.balancete.dto.SaldoContabil;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.dto.BalancoPatrimonial;
import br.com.cnt.model.entity.balanco.dto.LinhaBalanco;
import br.com.cnt.model.entity.balanco.dto.SaldoBalanco;
import br.com.cnt.model.entity.balanco.dto.SaldoExercicio;
import br.com.cnt.model.entity.balanco.dto.ValorContabil;
import br.com.coder.arqprime.model.dao.app.DaoException;

public class LancamentoDAOTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuscarLancamentos() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuscarSaldoInicialDebito() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuscarSaldoInicialCredito() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuscarSaldoDebito() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuscarSaldoCredito() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuscarSaldosBalancete() throws DaoException {
		ExercicioDAO exercicioDAO = new ExercicioDAO();
		Exercicio exercicio = new Exercicio(1L);
		exercicio.setEmpresa(new Empresa(1L));
		exercicio = exercicioDAO.buscar(exercicio.getId());
		Empresa empresa = exercicio.getEmpresa();
		exercicio.setEmpresa(empresa);

		Balancete balancete = new Balancete();
		balancete.setExercicio(exercicio);
		Date de = new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime();
		balancete.setDe(de);
		Date ate = new GregorianCalendar(2017, Calendar.DECEMBER, 31).getTime();
		balancete.setAte(ate);

		List<SaldoContabil> saldosContabeis = new LancamentoDAO().buscarSaldosBalancete(exercicio, de, ate);
		balancete.setSaldos(saldosContabeis);
		for (SaldoContabil saldoContabil : balancete.getSaldos()) {
			Conta conta = saldoContabil.getConta();
			System.out.printf("%-20s | %-50s | %20s | %20s | %20s | %20s \n", conta.getEstrutura(), conta.getNome(),
					saldoContabil.getSaldoInicial(), saldoContabil.getDebito(), saldoContabil.getCredito(),
					saldoContabil.getSaldoFinal());
		}

	}

	@Test
	public void testBuscarLancamentoComposto() {
		fail("Not yet implemented");
	}

	@Test
	public void testRetornarSaldosRazao() {
		fail("Not yet implemented");
	}

	@Test
	public void testRetornarSaldoInicialRazaoDebido() {
		fail("Not yet implemented");
	}

	@Test
	public void testRetornarSaldoInicialRazaoCredito() {
		fail("Not yet implemented");
	}

	@Test
	public void testBalanco() throws DaoException {
		
		LancamentoDAO lancamentoDAO = new LancamentoDAO();
		
		Exercicio exercicio = new ExercicioDAO().buscar(153L);
		
		BalancoPatrimonial buscarSaldosBalanco = lancamentoDAO.buscarBalancoPatrimonial(exercicio, 1);
		
		System.out.println(buscarSaldosBalanco.getEmpresa().getRazaoSocial());
		
		List<SaldoBalanco> saldosBalanco = buscarSaldosBalanco.getSaldos();
		for (SaldoBalanco saldoBalanco : saldosBalanco) {
			Conta conta = saldoBalanco.getConta();
			System.out.printf("%-60s", StringUtils.repeat("-", conta.getNivel())+conta.getNome());
			System.out.print(conta.getContaTipo().name().substring(0,1)+" | ");
			
			SaldoExercicio[] saldosExercicios = saldoBalanco.getSaldos();
			for (SaldoExercicio saldoExercicio : saldosExercicios) {
				System.out.printf("%10s|", saldoExercicio.getValor());
			}
			System.out.println();
		}

	}

}
