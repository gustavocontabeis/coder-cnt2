package br.com.cnt.web.jsf.managedbeans;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.model.utils.DirecaoOrdenacao;
import br.com.coder.arqprime.model.utils.Filtro;
import br.com.coder.arqprime.web.jsf.managedbeans.app.CrudManagedBean;
import br.com.cnt.model.dao.balanco.EmpresaDAO;
import br.com.cnt.model.dao.balanco.ExercicioDAO;
import br.com.cnt.model.dao.balanco.PlanoContasDAO;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.PlanoContas;

//@ManagedBean @ViewScoped
@javax.inject.Named @javax.faces.view.ViewScoped
public class ExercicioManagedBean extends CrudManagedBean<Exercicio, ExercicioDAO> {

	/**
	 */
	private static final long serialVersionUID = 1L;

	//private Exercicio exercicio;
	//protected LazyDataModel<Exercicio> model;
	//protected Filtro filtro;
	
	@Inject private ExercicioDAO dao;
	@Inject private EmpresaDAO empresaDAO;
	@Inject private PlanoContasDAO planoContasDAO;

	private List<Empresa> empresas;
	private List<PlanoContas> planosContas;
	private List<PlanoContas> planocontas;

	@PostConstruct
	private void init() {
		//novo(null);
		//loadLazyModel();
		getPopularComboEmpresa();
		getPopularComboPlanoContas();
	}
	
	@Override
	protected Filtro getFiltro(Filtro filtro) {
		filtro.addFetch("empresa");
		filtro.addOrder("empresa.razaoSocial");
		filtro.addOrder("ano", DirecaoOrdenacao.DESC);
		return filtro;
	}

	public List<Empresa> getPopularComboEmpresa() {
		empresas = empresaDAO.buscarTodos();
		return empresas;
	}

	public List<PlanoContas> getPlanosContas() {
		return planosContas;
	}

	public List<PlanoContas> getPopularComboPlanoContas() {
		planocontas = planoContasDAO.buscarTodos();
		return planocontas;
	}
	
	public List<PlanoContas> getPlanocontas() {
		return planocontas;
	}
	
	public void setPlanocontas(List<PlanoContas> planocontas) {
		this.planocontas = planocontas;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	@Override
	protected Exercicio novo() {
		entity = new Exercicio();
		return entity;
	}

	@Override
	protected BaseDAO<Exercicio> getDao() {
		return dao;
	}

}