package br.com.cnt.web.jsf.managedbeans;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import br.com.cnt.model.dao.balanco.EmpresaDAO;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.web.jsf.managedbeans.app.CrudManagedBean;

@Named @ViewScoped
public class EmpresaManagedBean extends CrudManagedBean<Empresa, EmpresaDAO> {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private List<Empresa> empresas;
	
	@Inject private EmpresaDAO empresaDAO;

	@PostConstruct
	private void init() {
		empresas = getPopularComboEmpresa();
	}

	@Override
	protected BaseDAO<Empresa> getDao() {
		return empresaDAO;
	}
	
	@Override
	protected Empresa novo() {
		entity = new Empresa();
		//entity.setContas(new ArrayList<Conta>());
		entity.setMatriz(new Empresa());
		return entity;
	}


	public List<Empresa> getPopularComboEmpresa() {
		return empresaDAO.buscarTodos();
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

}

