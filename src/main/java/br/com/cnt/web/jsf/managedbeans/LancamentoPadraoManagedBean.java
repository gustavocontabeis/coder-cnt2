package br.com.cnt.web.jsf.managedbeans;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;

import br.com.cnt.model.dao.balanco.ContaDAO;
import br.com.cnt.model.dao.balanco.HistoricoPadraoDAO;
import br.com.cnt.model.dao.balanco.LancamentoPadraoDAO;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.HistoricoPadrao;
import br.com.cnt.model.entity.balanco.LancamentoPadrao;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.model.utils.Filtro;
import br.com.coder.arqprime.web.jsf.managedbeans.app.CrudManagedBean;

@Named
@ViewScoped
public class LancamentoPadraoManagedBean extends CrudManagedBean<LancamentoPadrao, LancamentoPadraoDAO> {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private List<Conta> contas;
	
	@Inject private LancamentoPadraoDAO dao;
	@Inject private ContaDAO contaDAO;
	@Inject private HistoricoPadraoDAO historicoPadraoDAO;

	@PostConstruct
	private void init() {
		//novo(null);
		//loadLazyModel();
		getPopularComboConta();
	}
	
	@Override
	protected Filtro getFiltro(Filtro filtro) {
		filtro.addFetch("debito", "credito", "historicoPadrao");
		return filtro;
	}

	@Override
	protected LancamentoPadraoDAO getDao() {
		return dao;
	}
	
	@Override
	protected LancamentoPadrao novo() {
		entity = new LancamentoPadrao();
		return entity;
	}

	public List<Conta> getPopularComboConta() {
		contas = contaDAO.buscarTodos();
		return contas;
	}
	public List<HistoricoPadrao> getPopularAutocompleteHistoricoPadrao(String historico) throws DaoException {
		return historicoPadraoDAO.buscarPorHistorico(historico);
	}
	
	public List<Conta> buscarConta(String param){
		Map<String, Object> filters = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(param)){
			
			if(param.length()>=2 && param.substring(0, 2).matches("\\d\\.")){
				filters.put("estrutura", param+"%");
			}else if(param.matches("\\d*.?")){
				filters.put("id", new Long(param)); 
			}else if(param.matches("\\d\\.\\d\\.\\d\\.\\d{2}\\.\\d{2}\\.\\d{2}\\.\\d{2}")){
				filters.put("estrutura", param);
			}else{
				filters.put("nome", param);
			}
			filters.put("contaTipo", ContaTipo.ANALITICA);
			List<Conta> buscar2 = contaDAO.buscar2(new Filtro<Conta>(Conta.class, 0, 100, null, null, filters));
			return buscar2;
		}
		return new ArrayList<Conta>();
	}
	
}

