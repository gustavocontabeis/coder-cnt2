package br.com.cnt.model.dao.balanco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.coder.arqprime.model.utils.Filtro;

public class ContaDAOTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContaDAOTest.class.getSimpleName());

	@Test
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
	public void testBuscar2() {
		Filtro<Conta> filtro = new Filtro<>(Conta.class, 0, 10, "estrutura", SortOrder.ASCENDING, new HashMap<String, Object>());
		ContaDAO dao = new ContaDAO();
		List<Conta> buscar2 = dao.buscar2(filtro);
		for (Conta conta : buscar2) {
			System.out.println(conta);
		}
	}

}
