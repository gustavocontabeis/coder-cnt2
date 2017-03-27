package br.com.cnt.web.jsf.managedbeans;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.dto.Balancete;
import br.com.cnt.model.entity.balanco.dto.BalancoPatrimonial;
import br.com.cnt.model.entity.balanco.dto.SaldoContabil;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.web.jsf.managedbeans.app.BaseManagedBean;

//@ManagedBean @ViewScoped
@javax.inject.Named @javax.faces.view.ViewScoped //@javax.enterprise.context.RequestScoped
public class BalanceteManagedBean extends BaseManagedBean{
	
	private static final long serialVersionUID = 1L;
	
	private Balancete balancete;
	private BalancoPatrimonial bp;
		
	@Inject 
	private BalanceteDAO dao;
	
	@Inject
	private LancamentoDAO lancamentoDAO;
	@Inject
	private ExercicioDAO exercicioDAO;
	
	private List<SaldoContabil> backup;
	private List<SaldoContabil> selecionados;
	private int nivel = 0;
	private boolean retirarContasSemValor;
	
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
		
		HttpSession session = getSession();
		Exercicio exercicio = (Exercicio) session.getAttribute("exercicio");
		
		Date de = (Date) session.getAttribute("de");
		Date ate = (Date) session.getAttribute("ate");
		
		try {
			
			this.dao = new BalanceteDAO(lancamentoDAO, exercicioDAO);
			this.balancete = dao.buscarBalancete(exercicio, de, ate);
			this.backup = new ArrayList<>(balancete.getSaldos());
			
			int maiorNivel = 0;
			for (SaldoContabil sc : this.backup) {
				if(sc.getConta().getNivel() > maiorNivel){
					maiorNivel = sc.getConta().getNivel();
				}
			}
			nivel = maiorNivel;
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		//Identa o nome da conta pelo nível
		for(SaldoContabil sc : this.balancete.getSaldos()){
			Integer nivel = sc.getConta().getNivel();
			String nome = StringUtils.leftPad(sc.getConta().getNome(), nivel, " ");
			sc.getConta().setNome(nome);
		}
		
		if(retirarContasSemValor){
			retirarContasSemValor(null);
		}
		
		//getSession().setAttribute("balancete", balancete);
		
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
	
	public void exibirBalancoPatrimonial(ActionEvent evt){
		//this.nivel = 2;
		//exibirAteNivel(null);
		bp = new BalancoPatrimonial();
		
		List<SaldoContabil> saldos = this.balancete.getSaldos();
		Iterator<SaldoContabil> iterator = saldos.iterator();
		
		List<SaldoContabil> ativo = new ArrayList<>();
		List<SaldoContabil> passivo = new ArrayList<>();
		List<SaldoContabil> dre = new ArrayList<>();
		while (iterator.hasNext()) {
			SaldoContabil saldoContabil = (SaldoContabil) iterator.next();
			if(saldoContabil.getConta().getEstrutura().startsWith("1.")){
				ativo.add(saldoContabil);
			}else if(saldoContabil.getConta().getEstrutura().startsWith("2.")){
				passivo.add(saldoContabil);
			}else{
				dre.add(saldoContabil);
			}
		}
		
		int size = ativo.size();
		size = size<passivo.size()?passivo.size():size;
		System.out.println("\n\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		SaldoContabil scVazio = new SaldoContabil();
		scVazio.setConta(new Conta(null, ""));
		scVazio.setSaldoFinal(BigDecimal.ZERO);
		
		Iterator<SaldoContabil> iteratorAtivo = ativo.iterator();
		Iterator<SaldoContabil> iteratorPassivo = passivo.iterator();
		
		for (int i = 0; i < size; i++) {
			
			SaldoContabil saldoContabilAtivo = iteratorAtivo.hasNext() ? iteratorAtivo.next() : scVazio;
			SaldoContabil saldoContabilPassivo = iteratorPassivo.hasNext() ? iteratorPassivo.next() : scVazio;
			
			System.out.printf("%-80s | %10s || %-80s | %10s\n", 
					saldoContabilAtivo.getConta().getNome(), 
					!saldoContabilAtivo.getSaldoFinal().equals(BigDecimal.ZERO) ? saldoContabilAtivo.getSaldoFinalContabil():"",
					saldoContabilPassivo.getConta().getNome(), 
					!saldoContabilPassivo.getSaldoFinal().equals(BigDecimal.ZERO) ? saldoContabilPassivo.getSaldoFinalContabil() : ""
					);
			
			bp.addLinha(saldoContabilAtivo, saldoContabilPassivo);
			
		}
	}
	
	public void retirar(ActionEvent evt){
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String string = params.get("idConta");
		Long param1 = new Long(string);
		Iterator<SaldoContabil> iterator = this.balancete.getSaldos().iterator();
		SaldoContabil saldoContabil = null;
		boolean sintetico = false;
		while (iterator.hasNext()) {
			saldoContabil = (SaldoContabil) iterator.next();
			if(param1.equals(saldoContabil.getConta().getId())){
				if(saldoContabil.getConta().getContaTipo() == ContaTipo.SINTETICA){
					sintetico = true;
				}
				break;
			}
		}
		if(sintetico){
			iterator = this.balancete.getSaldos().iterator();
			while (iterator.hasNext()) {
				if(iterator.next().getConta().getEstrutura().equals(saldoContabil.getConta().getEstrutura())){
					iterator.remove();
				}
			}
		}else{
			iterator.remove();
		}
	}
	
	public void retirarContasSemValor(ActionEvent evt){
		Iterator<SaldoContabil> iterator = this.balancete.getSaldos().iterator();
		while (iterator.hasNext()) {
			SaldoContabil saldoContabil = (SaldoContabil) iterator.next();
			if(saldoContabil.getSaldoInicial().equals(BigDecimal.ZERO)
					&& saldoContabil.getDebito().equals(BigDecimal.ZERO)
					&& saldoContabil.getCredito().equals(BigDecimal.ZERO)){
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

	public List<SaldoContabil> getSelecionados() {
		return selecionados;
	}

	public void setSelecionados(List<SaldoContabil> selecionados) {
		this.selecionados = selecionados;
	}

	public boolean isRetirarContasSemValor() {
		return retirarContasSemValor;
	}

	public void setRetirarContasSemValor(boolean retirarContasSemValor) {
		this.retirarContasSemValor = retirarContasSemValor;
	}

	public BalancoPatrimonial getBp() { 
		return bp;
	}

	public void setBp(BalancoPatrimonial bp) {
		this.bp = bp;
	}
	
}
