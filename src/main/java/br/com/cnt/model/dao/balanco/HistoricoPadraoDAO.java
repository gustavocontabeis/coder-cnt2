package br.com.cnt.model.dao.balanco;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.cnt.model.entity.balanco.HistoricoPadrao;

public class HistoricoPadraoDAO extends BaseDAO<HistoricoPadrao> {
	
 	private static final long serialVersionUID = 1L;
 	
 	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
 	public HistoricoPadrao buscar(Long id) throws DaoException {
 		Session session = getSession();
 		Query query = session.getNamedQuery("HistoricoPadrao-porId");
 		query.setLong("id", id);
 		HistoricoPadrao singleResult = (HistoricoPadrao) query.getSingleResult();
 		session.close();
 		return singleResult;
 	}
 	
 	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
 	public List<HistoricoPadrao> buscarPorHistorico(String historico) throws DaoException {
 		Session session = getSession();
 		Query query = session.getNamedQuery("HistoricoPadrao-buscarPorHistorico");
 		query.setString("historico", "%"+historico+"%");
 		List resultList = query.getResultList();
 		session.close();
 		return resultList;
 	}
}
 