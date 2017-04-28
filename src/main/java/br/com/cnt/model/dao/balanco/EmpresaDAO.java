package br.com.cnt.model.dao.balanco;

import java.util.List;

import javax.inject.Named;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;


import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.coder.arqprime.model.dao.app.BaseDAO;

@Named
public class EmpresaDAO extends BaseDAO<Empresa> {
	
 	private static final long serialVersionUID = 1L;
 	
 	@SuppressWarnings("unchecked")
	public List<Exercicio> buscarExercitios(Empresa empresa){
 		Session session = getSession();
 		Query query = session.createQuery("select ex from Empresa obj inner join obj.exercicios ex where obj.id = :id");
 		query.setLong("id", empresa.getId());
 		List list = query.list();
 		session.close();
		return list;
 	}
 	
 	@SuppressWarnings("unchecked")
	public List<Empresa>buscarTodasEmpresas(){
 		Session session = getSession();
 		Query query = session.createQuery("from Empresa obj order by obj.razaoSocial asc");
 		List list = query.list();
 		session.close();
		return list;
 	}

	public List<Empresa> buscarTodos() {
 		Session session = getSession();
 		Query query = session.createQuery("select obj from Empresa obj order by obj.razaoSocial asc");
 		query.setCacheable(true);
 		query.setCacheRegion("Empresa-buscarTodos");
 		List list = query.list();
 		session.close();
		return list;
	}
}
 