package br.com.cnt.web.jsf.managedbeans;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import br.com.cnt.model.dao.balanco.PlanoContasDAO;
import br.com.cnt.model.entity.balanco.ContaModelo;
import br.com.cnt.model.entity.balanco.PlanoContas;
import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.web.jsf.managedbeans.app.CrudManagedBean;

//@ManagedBean @ViewScoped
@javax.inject.Named 
@javax.faces.view.ViewScoped
public class PlanoContasManagedBean extends CrudManagedBean<PlanoContas, PlanoContasDAO> {

	private static final long serialVersionUID = 1L;

	private ContaModelo conta;
	
	@Inject 
	private PlanoContasDAO dao;

	@PostConstruct
	private void init() {
		
	}

	@Override
	public void novo(ActionEvent evt) {
	}

	@Override
	protected PlanoContas novo() {
		entity = new PlanoContas();
		return entity;
	}

	@Override
	protected BaseDAO<PlanoContas> getDao() {
		return dao;
	}

}

