package br.com.cnt.model.dao.balanco;

import static org.junit.Assert.*;

import java.util.Objects;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.Session;
import org.junit.Test;

import br.com.cnt.model.entity.balanco.Empresa;

public class EmpresaDAOTest {

	@Test
	public void testBuscarExercitios() {
		//fail("Not yet implemented");
	}

	@Test
	public void testBuscarTodasEmpresas() {
		//fail("Not yet implemented");
	}

	@Test
	public void testBuscarTodos() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetSession() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSalvar() {
		//fail("Not yet implemented");
	}

	@Test
	public void testExcluir() {
		//fail("Not yet implemented");
	}

	@Test
	public void testBuscarTLong() {
		//fail("Not yet implemented");
	}

	@Test
	public void testBuscarLong() {
		//fail("Not yet implemented");
	}

	@Test
	public void testBuscarStringObjectArray() {
		//fail("Not yet implemented");
	}

	@Test
	public void testBuscarFiltroOfT() {
		//fail("Not yet implemented");
	}

	@Test
	public void testBuscar2() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetQuantidade() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetQuantidade2() {
		//fail("Not yet implemented");
	}

	@Test
	public void testCriarCriteriaParaFiltro2() {
		//fail("Not yet implemented");
	}

	@Test
	public void testList() {
		//fail("Not yet implemented");
		
		Session session = HibernateUtility.getSession();
		
		System.out.println("-> 1 ");
		Empresa load = session.load(Empresa.class, 1L);
		System.out.println(load);
		
		System.out.println("-> 2 ");
		load = session.load(Empresa.class, 1L);
		System.out.println(load);
		
		System.out.println("-> 3 ");
		session.evict(load);
		System.out.println(load);
		
		System.out.println("-> 4 ");
		load = session.load(Empresa.class, 1L);
		System.out.println(load);
		
		System.out.println("-> 5 ");
		
		session.close();
	}

}
