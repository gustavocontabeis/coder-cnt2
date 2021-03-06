package br.com.cnt.web.jsf.managedbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.cnt.model.dao.balanco.BalanceteDAO;
import br.com.cnt.model.dao.balanco.ExercicioDAO;
import br.com.cnt.model.dao.balanco.LancamentoDAO;
import br.com.cnt.model.entity.balancete.dto.Balancete;
import br.com.cnt.model.entity.balancete.dto.SaldoContabil;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.dto.BalancoPatrimonial;
import br.com.cnt.model.entity.balanco.dto.LinhaBalanco;
import br.com.cnt.model.entity.balanco.dto.SaldoBalanco;
import br.com.cnt.model.entity.balanco.dto.SaldoExercicio;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.web.jsf.managedbeans.app.BaseManagedBean;

//@ManagedBean @ViewScoped
//@javax.enterprise.context.RequestScoped
@javax.inject.Named @javax.faces.view.ViewScoped 
public class BalanceteManagedBean extends BaseManagedBean{
	
	private static final long serialVersionUID = 1L;
	
	private Balancete balancete;
	private BalancoPatrimonial balanco;
		
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
	private int quantidadeDeExercicios;
	private int[] exercicios;
	
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
	
	public void exibirBalanceteListener(ComponentSystemEvent evt) throws AbortProcessingException{
		exibirBalancete(null);
	}
	
	public void exibirBalancoListener(ComponentSystemEvent evt) throws AbortProcessingException{
		exibirBalancoPatrimonial(null);
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
				
			//Identa o nome da conta pelo nível
			for(SaldoContabil sc : this.balancete.getSaldos()){
				Integer nivel = sc.getConta().getNivel();
				String nome = StringUtils.leftPad(sc.getConta().getNome(), nivel, " ");
				sc.getConta().setNome(nome);
			}
			
			if(retirarContasSemValor){
				retirarContasSemValor(null);
			}
			
		} catch (DaoException e) {
			message(e);
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
	
	public void exibirBalancoPatrimonial(ActionEvent evt){
		
		LancamentoDAO lancamentoDAO = new LancamentoDAO();
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Exercicio exercicio = (Exercicio) session.getAttribute("exercicio");

		if(quantidadeDeExercicios == 0){
			quantidadeDeExercicios = 3;
		}
		
		balanco = lancamentoDAO.buscarBalancoPatrimonial(exercicio, quantidadeDeExercicios);
		
		if(!balanco.getSaldos().isEmpty()){
			SaldoExercicio[] saldos = balanco.getSaldos().iterator().next().getSaldos();
			exercicios = new int[saldos.length];
			for (int i = 0; i < saldos.length; i++) {
				exercicios[i] = saldos[i].getAno().intValue();
			}
		}
		
		//printBalanco();
	}

	private void printBalanco() {
		for (SaldoBalanco saldoBalanco : balanco.getSaldos()) {
			Conta conta = saldoBalanco.getConta();
			System.out.printf("%-60s", StringUtils.repeat("-", conta.getNivel())+conta.getNome());
			System.out.print(conta.getContaTipo().name().substring(0,1)+" | ");
			
			SaldoExercicio[] saldosExercicios = saldoBalanco.getSaldos();
			for (SaldoExercicio saldoExercicio : saldosExercicios) {
				System.out.printf("%10s|", saldoExercicio.getValor());
			}
			System.out.println();
		}
		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		List<LinhaBalanco> list = balanco.getList();
		for (LinhaBalanco linhaBalanco : list) {
			SaldoBalanco ativo2 = linhaBalanco.getAtivo2();
			System.out.printf("%-40s | ", ativo2.getConta().getNome());
			SaldoExercicio[] saldosAtivo = ativo2.getSaldos();
			for (SaldoExercicio saldoExercicio : saldosAtivo) {
				System.out.printf("%s | ", saldoExercicio.getAno());
				System.out.printf("%10s | ", saldoExercicio.getValor());
			}
			SaldoBalanco passivo2 = linhaBalanco.getPassivo2();
			System.out.printf("%-40s | ", passivo2.getConta().getNome());
			SaldoExercicio[] saldosPassivo = passivo2.getSaldos();
			for (SaldoExercicio saldoExercicio : saldosPassivo) {
				System.out.printf("%s | ", saldoExercicio.getAno());
				System.out.printf("%10s | ", saldoExercicio.getValor());
			}
			System.out.println();
		}
		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::");
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

	public int getQuantidadeDeExercicios() {
		return quantidadeDeExercicios;
	}

	public void setQuantidadeDeExercicios(int quantidadeDeExercicios) {
		this.quantidadeDeExercicios = quantidadeDeExercicios;
	}

	public BalancoPatrimonial getBalanco() {
		return balanco;
	}

	public void setBalanco(BalancoPatrimonial balanco) {
		this.balanco = balanco;
	}

	public int[] getExercicios() {
		return exercicios;
	}

	public void setExercicios(int[] exercicios) {
		this.exercicios = exercicios;
	}
		
}
