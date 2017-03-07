package br.com.cnt.web.jsf.managedbeans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.web.jsf.managedbeans.app.BaseManagedBean;
import br.com.cnt.model.dao.balanco.BalanceteDAO;
import br.com.cnt.model.dao.balanco.ContaDAO;
import br.com.cnt.model.dao.balanco.ExercicioDAO;
import br.com.cnt.model.dao.balanco.LancamentoDAO;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.dto.Balancete;
import br.com.cnt.model.entity.balanco.dto.SaldoContabil;
import br.com.cnt.model.utils.ConstantesComuns;

//@ManagedBean @ViewScoped
@javax.inject.Named @javax.faces.view.ViewScoped //@javax.enterprise.context.RequestScoped
public class BalanceteManagedBean extends BaseManagedBean{
	
	private static final long serialVersionUID = 1L;
	
	private Balancete balancete;
	
	@Inject 
	private BalanceteDAO dao;
	
	@Inject
	private LancamentoDAO lancamentoDAO;
	@Inject
	private ExercicioDAO exercicioDAO;
	
	@PostConstruct
	private void init() {
		if(loginBean != null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			Exercicio exercicio = (Exercicio) session.getAttribute("exercicio");
			if(exercicio == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Selecione uma empresa para trabalhar."));
			}else{
				exibirBalancete(null);
			}
		}
	}
	
	public void exibirBalancete(ActionEvent evt){

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Exercicio exercicio = (Exercicio) session.getAttribute("exercicio");
		
		Date de = (Date) session.getAttribute("de");
		Date ate = (Date) session.getAttribute("ate");
		
		try {
			dao = new BalanceteDAO(lancamentoDAO, exercicioDAO);
			this.balancete = dao.buscarBalancete(exercicio, de, ate);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		for(SaldoContabil sc : this.balancete.getSaldos()){
			Integer nivel = sc.getConta().getNivel();
			String nome = StringUtils.leftPad(sc.getConta().getNome(), nivel, " ");
			sc.getConta().setNome(nome);
		}
		
	}

	public Balancete getBalancete() {
		return balancete;
	}

	public void setBalancete(Balancete balancete) {
		this.balancete = balancete;
	}

}
