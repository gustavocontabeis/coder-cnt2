package br.com.cnt.model.dao.balanco;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Query;
import org.hibernate.Session;


import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.coder.arqprime.model.dao.app.BaseDAO;

@javax.inject.Named @javax.faces.view.ViewScoped
public class ExercicioDAO extends BaseDAO<Exercicio> {
 	private static final long serialVersionUID = 1L;
 	
 	public Exercicio buscarExercicio(Empresa empresa, Integer ano){
 		Session session = getSession();
 		Query query = session.createQuery("from Exercicio obj where obj.empresa.id = :empresa and obj.ano = :ano");
 		query.setLong("empresa", empresa.getId());
 		query.setInteger("ano", ano);
 		return (Exercicio) query.uniqueResult();
 	}

	public List<Exercicio> buscarTodos() {
 		Session session = getSession();
 		Query query = session.createQuery("select obj from Exercicio obj order by obj.empresa.razaoSocial, obj.ano");
 		List list = query.list();
		session.close();
		return list;
	}

	public List<Exercicio> buscarExercicio(Empresa empresa) {
 		Session session = getSession();
 		Query query = session.createQuery("select obj from Exercicio obj inner join fetch obj.empresa emp where emp.id = :empresa");
 		query.setCacheable(true);
 		query.setCacheRegion("Exercicio-buscarExercicio-"+empresa.getId());
 		query.setLong("empresa", empresa.getId());
 		List list = query.list();
 		session.close();
 		return list;
	}
}
 