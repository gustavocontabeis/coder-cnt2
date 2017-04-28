package br.com.cnt.model.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
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
import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.model.dao.app.CriteriaDTO;
import br.com.coder.arqprime.model.utils.Filtro;

public class BaseDAOTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDAOTest.class.getSimpleName());
	
	
	@Test
	public void testCriarCriteriaParaFiltro2(){
		
		HashMap<String, Object> filters = new HashMap<String, Object>();
		
		Filtro filtro = new Filtro<>(Conta.class, 0, 10, null, SortOrder.ASCENDING, filters);
		BaseDAO<Conta>dao = new BaseDAO<>();
		CriteriaDTO dto = dao.criarCriteriaParaFiltro2(filtro);
		
	}

	@Test
	public void testWhere() {

		Session session = br.com.coder.arqprime.model.utils.HibernateUtil.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Conta> criteria = builder.createQuery(Conta.class);

		Root<Conta> from = criteria.from(Conta.class);
		criteria.where(builder.equal(from.get("id"), 1));

		Query<Conta> query = session.createQuery(criteria);
		List<Conta> resultList = query.getResultList();
		// print(resultList);
	}

	/**
	 * Joins e vários where
	 */
	@Test
	public void testJoin() {

		Session session = br.com.coder.arqprime.model.utils.HibernateUtil.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Conta> criteria = builder.createQuery(Conta.class);

		Root<Conta> from = criteria.from(Conta.class);
		Join<Object, Object> pai = from.join("pai");
		Join<Object, Object> planoContas = pai.join("planoContas");

		criteria.where(builder.equal(from.get("id"), 1), builder.equal(pai, 1),
				builder.equal(planoContas.get("id"), 1));

		Query<Conta> query = session.createQuery(criteria);
		List<Conta> resultList = query.getResultList();
		// print(resultList);
	}

	@Test
	public void testJoinAlinhado() {

		Session session = br.com.coder.arqprime.model.utils.HibernateUtil.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Conta> criteria = builder.createQuery(Conta.class);

		Join<Object, Object> pc = criteria.from(Conta.class).join("pai").join("planoContas");

		criteria.where(builder.equal(pc.get("id"), 1));

		Query<Conta> query = session.createQuery(criteria);
		List<Conta> resultList = query.getResultList();
		// print(resultList);
	}

	/**
	 * Join alinhado no where
	 */
	@Test
	public void testJoinAlinhadoNoWhere() {

		Session session = br.com.coder.arqprime.model.utils.HibernateUtil.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Conta> criteria = builder.createQuery(Conta.class);

		criteria.where(builder.equal(criteria.from(Conta.class).join("pai").join("planoContas").get("id"), 1));

		Query<Conta> query = session.createQuery(criteria);
		List<Conta> resultList = query.getResultList();
		// print(resultList);
	}

	/**
	 * Join alinhado no where
	 */
	@Test
	public void testteste() {

		Session session = br.com.coder.arqprime.model.utils.HibernateUtil.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Conta> criteria = builder.createQuery(Conta.class);
		Map<String, Object> joins = new HashMap<>();
		Root<Conta> from = criteria.from(Conta.class);
		joins.put("from", from);

		String x = "pai.planoContas.id";
		String[] split = x.split("\\.");
		String pai = "from";
		for (String string : split) {
			System.out.println(string);
			Object object = joins.get(pai);

			// joins.put(string, object.get(string));
			pai = string;
		}

		// criteria.where(
		// builder.equal(joins.get("planoContas").get("id"), 1)
		// );

		Query<Conta> query = session.createQuery(criteria);
		List<Conta> resultList = query.getResultList();
		// print(resultList);
	}

	@Test
	public void testOrderBy() {

		Session session = br.com.coder.arqprime.model.utils.HibernateUtil.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Conta> query = builder.createQuery(Conta.class);
		Root<Conta> routeRoot = query.from(Conta.class);
		query.select(routeRoot);

		List<Order> orderList = new ArrayList<>();
		orderList.add(builder.desc(routeRoot.get("ordem")));
		query.orderBy(orderList);

		Query<Conta> createQuery = session.createQuery(query);
		List<Conta> resultList = createQuery.getResultList();
		for (Conta conta : resultList) {
			System.out.println(conta);
		}

	}

	private void print(List<Conta> resultList) {
		for (Conta conta : resultList) {
			System.out.println(conta);
		}
	}

	@Test
	public void testPimba() {
		
		Locale ptBR = new Locale("pt","BR");
		DateFormat dfMesAno = new SimpleDateFormat("MM/yyyy");

		Calendar de = new GregorianCalendar(2017, Calendar.MARCH, 1);

		Calendar ate = new GregorianCalendar();
		ate.setTimeInMillis(de.getTimeInMillis());
		ate.add(Calendar.MONTH, 1);
		ate.add(Calendar.DAY_OF_MONTH, -1);

		System.out.println(dfMesAno.format(de.getTime()));
		
		//Monta uma fila de datas do mês
		Queue<Calendar> datasMes = new LinkedList<>();
		while (de.before(ate) || de.equals(ate)) {
			Calendar c = new GregorianCalendar();
			c.setTime(de.getTime());
			datasMes.add(c);
			de.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		
		for (int iSemana = de.get(Calendar.WEEK_OF_MONTH); iSemana <= ate.get(Calendar.WEEK_OF_MONTH); iSemana++) {
			//System.out.println("Semana -> " + iSemana);

			for (int iDiaSemana = Calendar.SUNDAY; iDiaSemana <= Calendar.SATURDAY; iDiaSemana++) {
				//System.out.print(iDiaSemana + "-");

				// Procura a data pela semana e dia da semana
				Calendar dia = null;
				Iterator<Calendar> iterator = datasMes.iterator();
				ok:
				while (iterator.hasNext()) {
					Calendar find = (Calendar) iterator.next();
					if (find.get(Calendar.WEEK_OF_MONTH) == iSemana) {
						if (find.get(Calendar.DAY_OF_WEEK) == iDiaSemana) {
							dia = find;
							iterator.remove();
							break ok;
						}
					}
				}
				System.out.printf(" %3s | ", (dia != null ? dia.get(Calendar.DAY_OF_MONTH) : "  "));
			}
			System.out.println();
		}

	}
}
