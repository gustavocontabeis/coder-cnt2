package br.com.cnt.web.jsf.managedbeans;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;

import br.com.cnt.model.dao.balanco.ContaDAO;
import br.com.cnt.model.dao.balanco.EmpresaDAO;
import br.com.cnt.model.dao.balanco.PlanoContasDAO;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaOrigem;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.PlanoContas;
import br.com.cnt.model.utils.ContaUtil;
import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.web.jsf.managedbeans.app.CrudManagedBean;

@Named @ViewScoped
public class ContaManagedBean extends CrudManagedBean<Conta, ContaDAO> {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private List<Empresa> empresas;
	private List<PlanoContas> planocontas;

	@Inject private ContaDAO dao;
	@Inject private EmpresaDAO empresaDAO;
	@Inject private PlanoContasDAO planoContasDAO;

	@PostConstruct
	private void init() {
		System.out.println("ContaManagedBean.init() ");
		novo(null);
		empresas = getPopularComboEmpresa();
		planocontas = getPopularComboPlanoContas();
	}
	
	@Override
	protected BaseDAO<Conta> getDao() {
		return dao;
	}

	public Conta novo() {
		entity = new Conta(); //refatorar. mao posso abrigar aqui a refetenciar yhis.entity.
		return entity;
	}
	
	@Override
	protected boolean salvarAntes(Conta entity) {
		entity.setNivel(ContaUtil.retornarNivel(entity));
		return true;
	}

	public void clonar(ActionEvent evt) {
		try {
			Conta conta = (Conta) BeanUtils.cloneBean(entity);
			conta.setId(null);
			this.entity = conta;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			message(e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			message(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			message(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			message(e);
		}
	}

	public ContaOrigem[] getPopularComboContaOrigem() {
		return ContaOrigem.values();
	}

	public ContaTipo[] getPopularComboContaTipo() {
		return ContaTipo.values();
	}

	public List<Empresa> getPopularComboEmpresa() {
		return empresaDAO.buscarTodos();
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

}
