package br.com.cnt.model.dao.balanco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Ignore;
import org.junit.Test;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.PlanoContas;
import br.com.coder.arqprime.model.utils.Filtro;
import br.com.coder.arqprime.model.utils.HibernateUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class ContaDAOTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContaDAOTest.class.getSimpleName());

	@Test
	@Ignore
	public void testBuscar() {

		Session session = br.com.coder.arqprime.model.utils.HibernateUtil.getSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Conta> query = criteriaBuilder.createQuery(Conta.class);
		Root<Conta> routeRoot = query.from(Conta.class);
		query.select(routeRoot);

		List<Order> orderList = new ArrayList<>();
		orderList.add(criteriaBuilder.desc(routeRoot.get("ordem")));
		query.orderBy(orderList);

		Query<Conta> createQuery = session.createQuery(query);
		List<Conta> resultList = createQuery.getResultList();
		for (Conta conta : resultList) {
			System.out.println(conta);
		}

	}

	@Test
	@Ignore
	public void testBuscar2() {
		Filtro<Conta> filtro = new Filtro<>(Conta.class, 0, 10, "estrutura", SortOrder.ASCENDING,
				new HashMap<String, Object>());
		ContaDAO dao = new ContaDAO();
		List<Conta> buscar2 = dao.buscar2(filtro);
		for (Conta conta : buscar2) {
			System.out.println(conta);
		}
	}

	@Test
	//@Ignore
	public void testCache() {
		
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		Exercicio conta = session.get(Exercicio.class, 1L);
		transaction.commit();
		//session.close();
		System.out.println(Objects.toString(conta));
		
		Transaction transaction2 = session.beginTransaction();
		Exercicio conta2 = session.get(Exercicio.class, 1L);
		transaction2.commit();
		session.close();
		
		System.out.println(Objects.toString(conta2));
		
//		net.sf.ehcache.hibernate.EhCacheRegionFactory a ;
//		net.sf.ehcache.hibernate.EhCacheRegionFactory b;
//		org.hibernate.cache.spi.RegionFactory c;
		
		
		//1. Create a cache manager
		CacheManager cm = CacheManager.create();
		
		//2. Get a cache called "cache1", declared in ehcache.xml
		Cache cache = cm.getCache("cache1");
		
		//3. Put few elements in cache
		cache.put(new Element("1","Jan"));
		cache.put(new Element("2","Feb"));
		cache.put(new Element("3","Mar"));
		
		//4. Get element from cache
		Element ele = cache.get("2");
		
		//5. Print out the element
		String output = (ele == null ? null : ele.getObjectValue().toString());
		System.out.println(output);
		
		//6. Is key in cache?
		System.out.println(cache.isKeyInCache("3"));
		System.out.println(cache.isKeyInCache("10"));
		
		//7. shut down the cache manager
		cm.shutdown();
		
	}

	@Test
	public void testFiltro() {

		ContaDAO dao = new ContaDAO();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("planoContas", new PlanoContas(1L));
		parameters.put("empresa", new Empresa(9L));

		Filtro<Conta> filtro = new Filtro<>(Conta.class, 0, 10, "", SortOrder.ASCENDING, parameters);
		List<Conta> buscar2 = dao.buscar2(filtro);
		for (Conta conta : buscar2) {
			System.out.println(conta);
		}

	}

	@Test
	public void testComparator() {
		
		PlanoContasDAO daoPlanoContas = new PlanoContasDAO();

		/* Busca todas as contas deste plano de contas */
		Exercicio exercicio = new Exercicio(1L);
		exercicio.setEmpresa(new Empresa(1L));
		exercicio.setAno(2017);
		exercicio.setPlanoContas(new PlanoContas(1L));

		List<Conta> contas = daoPlanoContas.retornarContas(exercicio);

		for (Conta conta : contas) {
			System.out.println(conta);
		}
		
		System.out.println("---------------------------");
		
		Collections.sort(contas);
		
		for (Conta conta : contas) {
			System.out.println(conta);
		}
		
		System.out.println("---------------------------");
		
		Collections.reverse(contas);
		
		for (Conta conta : contas) {
			System.out.println(conta);
		}
		
		
		System.out.println("---------------------------");
		
		Collections.sort(contas);
		
		for (Conta conta : contas) {
			System.out.println(conta);
		}
		
		
	}
}
