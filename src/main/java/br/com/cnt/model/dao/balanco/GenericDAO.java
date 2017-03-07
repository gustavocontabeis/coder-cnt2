package br.com.cnt.model.dao.balanco;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

@SuppressWarnings("serial")
public class GenericDAO <T> implements Serializable{
	
	Session sessao;
	
	public Session getSession(){
		return HibernateUtility.getSession();
	}
	
	public void salvar(T t){
		sessao = HibernateUtility.getSession();
		Transaction transaction = sessao.beginTransaction();
		sessao.saveOrUpdate(t);
		transaction.commit();
		sessao.close();
	}
	
	public void excluir(T t){
		sessao = HibernateUtility.getSession();
		Transaction transaction = sessao.beginTransaction();
		sessao.delete(t);
		transaction.commit();
		sessao.close();
	}
	
	@SuppressWarnings("unchecked")
	public T buscar(Class<?> c, long id){
		sessao = HibernateUtility.getSession();
		//Transaction transaction = sessao.beginTransaction();
		T o = (T) sessao.get(c, id);
		//sessao.flush();
		//transaction.commit();
		sessao.close();
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscarHQL(String hql){
		sessao = HibernateUtility.getSession();
		Transaction transaction = sessao.beginTransaction();
		List<T> lista = sessao.createQuery(hql).list();
		transaction.commit();
		sessao.close();
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscarCriteria(Criteria cr){
		Session sessao = HibernateUtility.getSession();
		Transaction transaction = sessao.beginTransaction();
		List<T> lista = cr.list();
		transaction.commit();
		sessao.close();
		return lista;
	}
}