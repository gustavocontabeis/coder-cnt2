package br.com.cnt.model.dao.balanco;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;

import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaOrigem;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.PlanoContas;
import br.com.coder.arqprime.model.dao.app.BaseDAO;

public class ContaDAO extends BaseDAO<Conta> {
	
 	private static final long serialVersionUID = 1L;
 	
 	@SuppressWarnings("unchecked")
	public List<Conta>buscarTodasContas(Conta conta){
 		
 		Map<String, Object> param = new HashMap<String, Object>();
 		
 		String hql = "" +
 				"select " +
 					"conta.id, " +
 					"conta.estrutura, " +
 					"conta.nivel, " +
 					"conta.contaTipo, " +
 					"conta.nome, " +
 					"conta.contaOrigem, " +
 					"conta.descricao" +
 				"from " +
 					"Conta conta " +
 				"where ";
 				
				hql = hql.replace("where  and ", "where ").replace("where  or", "where ");
				if(hql.endsWith("where ")){
					hql = hql.replace("where ", "");
				}
 					
 		
 		Session session = getSession();
 		Query query = session.createQuery(hql + " order by conta.estrutura asc ");
 		query.setProperties(param);
 		
 		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				Conta conta = new Conta();
				conta.setId((Long)tuple[0]);
				conta.setEstrutura((String)tuple[1]);
				conta.setNivel((Integer)tuple[2]);
				conta.setContaTipo((ContaTipo)tuple[3]);
				conta.setNome((String)tuple[4]);
				conta.setContaOrigem((ContaOrigem)tuple[5]);
				conta.setDescricao(tuple[6]!=null?(String)tuple[6]:null);
				return conta;
			}
			@SuppressWarnings("rawtypes")
			@Override
			public List<?> transformList(List collection) {
				return collection;
			}
		});
		
 		return query.list();
 	}
 	
// 	@Override
// 	public Conta buscar(Long id) throws DaoException {
// 		Session session = getSession();
// 		Query query = session.getNamedQuery("Conta-porId");
// 		query.setLong("id", id);
// 		Conta singleResult = (Conta) query.getSingleResult();
// 		session.close();
// 		return singleResult;
// 	}

	public List<Conta> buscarTodos() {
		Session session = getSession();
		List list = session.createCriteria(Conta.class).list();
		session.close();
		return list;
	}

	public List<Conta> buscar(Exercicio exercicio, String param) {
		
		Map<String, Object> params = new HashMap<>();
		
		StringBuilder sb = new StringBuilder(); 
		sb.append("select obj from Conta obj ");
		sb.append("left join obj.empresa emp ");
		sb.append("where 1=1 ");
		
		if (param.length() >= 2 && param.substring(0, 2).matches("\\d\\.")) {
			params.put("estrutura", param + "%");
			sb.append(" and estrutura like :estrutura");
		} else if (param.matches("\\d*.?")) {
			params.put("id", new Long(param));
			sb.append(" and obj.id = :id");
		} else {
			params.put("nome", '%'+param.toUpperCase()+'%');
			sb.append(" and upper(obj.nome) like :nome");
		}
		
		params.put("contaTipo", ContaTipo.ANALITICA);
		sb.append(" and obj.contaTipo = :contaTipo ");
		
		Session session = getSession();
		Query query = session.createQuery(sb.toString());
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			query.setParameter(key, params.get(key));
		}
		List list = query.list();
		session.close();
		return list;
	}

	public Integer getQuantidade2(Conta conta) throws DaoException {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(obj) from Conta obj ");
		sb.append(" left join obj.planoContas pc ");
		sb.append(" left join obj.empresa emp ");
		
		Session session = getSession();
		Query query = session.createQuery(sb.toString());
		
		List<Long> list = query.list();
		session.close();
		
		if(!list.isEmpty()){
			Long next = list.iterator().next();
			return next.intValue();
		}
		
		return null;
	}

	public List<Conta> buscar2(Conta conta) throws DaoException {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select obj from Conta obj ");
		sb.append(" left join fetch obj.planoContas pc ");
		sb.append(" left join fetch obj.empresa emp ");
		sb.append(" order by obj.estrutura, obj.contaTipo desc, obj.ordem");
		
		Session session = getSession();
		Query query = session.createQuery(sb.toString());
		
		List<Conta> list = query.list();
		session.close();
		
		return list;
	}
}
 