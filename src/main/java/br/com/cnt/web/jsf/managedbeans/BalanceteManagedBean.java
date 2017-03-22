package br.com.cnt.web.jsf.managedbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.cnt.model.dao.balanco.BalanceteDAO;
import br.com.cnt.model.dao.balanco.ExercicioDAO;
import br.com.cnt.model.dao.balanco.LancamentoDAO;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.dto.Balancete;
import br.com.cnt.model.entity.balanco.dto.SaldoContabil;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.web.jsf.managedbeans.app.BaseManagedBean;

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
	
	private List<SaldoContabil> backup;
	private int nivel = 0;
	
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
			
			this.dao = new BalanceteDAO(lancamentoDAO, exercicioDAO);
			this.balancete = dao.buscarBalancete(exercicio, de, ate);
			this.backup = balancete.getSaldos();
			
			int maiorNivel = 0;
			for (SaldoContabil sc : this.backup) {
				//System.out.println(sc);
				if(sc.getConta().getNivel() > maiorNivel){
					maiorNivel = sc.getConta().getNivel();
				}
			}
			nivel = maiorNivel;
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		for(SaldoContabil sc : this.balancete.getSaldos()){
			Integer nivel = sc.getConta().getNivel();
			String nome = StringUtils.leftPad(sc.getConta().getNome(), nivel, " ");
			sc.getConta().setNome(nome);
		}
		
	}
	
	public void exibirAteNivel(ActionEvent evt){
		this.balancete.setSaldos(new ArrayList<>(backup));
		List<SaldoContabil> saldos = this.balancete.getSaldos();
		Iterator<SaldoContabil> iterator = saldos.iterator();
		while (iterator.hasNext()) {
			SaldoContabil saldoContabil = (SaldoContabil) iterator.next();
			if(saldoContabil.getConta().getNivel() > this.nivel){
				iterator.remove();
			}
		}
	}


	public Balancete getBalancete() {
		return balancete;
	}

	public void setBalancete(Balancete balancete) {
		this.balancete = balancete;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel; 
	}

}
