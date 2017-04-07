package br.com.cnt.model.dao.balanco;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.PlanoContas;
import br.com.coder.arqprime.model.dao.app.BaseDAO;

@Named
public class PlanoContasDAO extends BaseDAO<PlanoContas> {
 	
	private static final long serialVersionUID = 1L;
 	
	/**
	 * conta.empresa.id;
 	 * conta.planoContas.id;
 	 * 
	 * @param empresa
	 * @param exercicio
	 * @param planoContas
	 * @return
	 */
 	@SuppressWarnings("unchecked")
	public List<Conta>retornarContas(Exercicio exercicio){
 		
 		String hql = "from Conta conta where ";
 		if(exercicio.getEmpresa()!=null)
 			hql += "or conta.empresa.id = :empresa ";
 		if(exercicio.getPlanoContas()!=null)
 			hql += "or conta.planoContas.id = :planoContas ";
 		hql += " order by conta.estrutura, conta.contaTipo desc, conta.ordem, conta.nome ";
 		
 		Session session = getSession();
 		Query query = session.createQuery(hql.replace("where or", "where "));
 		
 		if(exercicio.getEmpresa()!=null)
 			query.setLong("empresa", exercicio.getEmpresa().getId());
 		if(exercicio.getPlanoContas()!=null)
 			query.setLong("planoContas", exercicio.getPlanoContas().getId());
 		
 		return query.list();
 	}

	public List<PlanoContas> buscarTodos() {
 		Session session = getSession();
 		Query query = session.createQuery("select obj from PlanoContas obj");
 		List list = query.list();
		session.close();
		return list;
	}
	
}
 