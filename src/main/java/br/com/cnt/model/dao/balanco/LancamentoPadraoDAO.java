package br.com.cnt.model.dao.balanco;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.LancamentoPadrao;
import br.com.cnt.model.entity.balanco.PlanoContas;

@Named
public class LancamentoPadraoDAO extends BaseDAO<LancamentoPadrao> { 
 	
	private static final long serialVersionUID = 1L;
 	
	@Override
	public LancamentoPadrao buscar(Long id) throws DaoException {
 		Session session = getSession();
 		Query query = session.getNamedQuery("LancamentoPadrao-buscar");
 		query.setLong("id", id);
 		LancamentoPadrao uniqueResult = (LancamentoPadrao) query.uniqueResult();
		session.close();
		return uniqueResult;
	}
	
	public List<LancamentoPadrao> buscarPorNome(String nome) throws DaoException {
 		Session session = getSession();
 		Query query = session.getNamedQuery("LancamentoPadrao-buscarPorNome");
 		query.setString("nome", "%"+nome+"%");
 		List resultList = query.getResultList();
		session.close();
		return resultList;
	}
	
}
 