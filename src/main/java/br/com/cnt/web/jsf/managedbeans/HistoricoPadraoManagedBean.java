package br.com.cnt.web.jsf.managedbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;

import br.com.cnt.model.dao.balanco.HistoricoPadraoDAO;
import br.com.cnt.model.entity.balanco.HistoricoPadrao;
import br.com.coder.arqprime.web.jsf.managedbeans.app.CrudManagedBean;

@Named @ViewScoped
public class HistoricoPadraoManagedBean extends CrudManagedBean<HistoricoPadrao, HistoricoPadraoDAO> {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private List<HistoricoPadrao> list;

	@Inject private HistoricoPadraoDAO dao;

	@PostConstruct
	private void init() {
		System.out.println("ContaManagedBean.init() ");
		novo(null);
	}
	
	@Override
	protected HistoricoPadraoDAO getDao() {
		return dao;
	}

	public HistoricoPadrao novo() {
		entity = new HistoricoPadrao(); //refatorar. mao posso abrigar aqui a refetenciar yhis.entity.
		return entity;
	}
	
	public void clonar(ActionEvent evt) {
		try {
			HistoricoPadrao historicoPadrao = (HistoricoPadrao) BeanUtils.cloneBean(entity);
			historicoPadrao.setId(null);
			this.entity = historicoPadrao;
		} catch (Exception e) {
			e.printStackTrace();
			message(e);
		}
	}

}
