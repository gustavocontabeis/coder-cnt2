package br.com.cnt.web.jsf.managedbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.SelectEvent;

import br.com.cnt.model.dao.balanco.ContaDAO;
import br.com.cnt.model.dao.balanco.ExercicioDAO;
import br.com.cnt.model.dao.balanco.HistoricoPadraoDAO;
import br.com.cnt.model.dao.balanco.LancamentoDAO;
import br.com.cnt.model.dao.balanco.LancamentoPadraoDAO;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.HistoricoPadrao;
import br.com.cnt.model.entity.balanco.Lancamento;
import br.com.cnt.model.entity.balanco.LancamentoPadrao;
import br.com.cnt.model.entity.balanco.LancamentoTipo;
import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.web.jsf.managedbeans.app.CrudManagedBean;

//@ManagedBean @ViewScoped
@javax.inject.Named
@javax.faces.view.ViewScoped
public class LancamentoManagedBean extends CrudManagedBean<Lancamento, LancamentoDAO> {

	/**
	 */
	private static final long serialVersionUID = 1L;

	//private Lancamento lancamento;
	private List<Conta> contas;
	private List<Exercicio> exercicios;
	private List<Lancamento> lancamentos;
	private LancamentoPadrao lancamentoPadrao;

	@Inject private LancamentoDAO dao;
	@Inject private LancamentoPadraoDAO lancamentoPadraoDAO;
	@Inject private ContaDAO contaDAO;
	@Inject private ExercicioDAO exercicioDAO;
	@Inject private HistoricoPadraoDAO historicoPadraoDAO;

	@PostConstruct
	private void init() {
		novo(null);
		//loadLazyModel();
		getPopularComboConta();
		getPopularComboExercicio();
	}

	public List<Conta> buscarConta(String param) {
		if (StringUtils.isNotBlank(param)) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			Exercicio exercicio = (Exercicio) session.getAttribute("exercicio");
			List<Conta> buscar2 = contaDAO.buscar(exercicio, param);
			return buscar2; 
		}
		return new ArrayList<Conta>();
	}
	
	@Override
	protected boolean excluirAntes(Lancamento entity) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Exercicio exercicio = (Exercicio) session.getAttribute("exercicio");
		if(exercicio.getFechado()){
			messageError(null, "Não é possível excluir lançamentos neste exercício pois "+exercicio.getAno()+" esta encerrado.");
			return false;
		}
		return super.excluirAntes(entity);
	}
	
	@Override
	protected boolean salvarAntes(Lancamento entity) { 
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Exercicio exercicio = (Exercicio) session.getAttribute("exercicio");
		if(exercicio.getFechado()){
			messageError(null, "Não é possível fazer ou alterar lançamentos neste exercício pois "+exercicio.getAno()+" esta encerrado.");
			return false;
		}
		
		if (entity.getDebito() != null && entity.getCredito() != null) {
			entity.setLancamentoTipo(LancamentoTipo.SIMPLES);
		} else {
			entity.setLancamentoTipo(LancamentoTipo.COMPOSTO);
		}
		return true;
	}
	
	public List<Conta> getPopularComboConta() {
		contas = contaDAO.buscarTodos();
		return contas;
	}

	public List<Exercicio> getPopularComboExercicio() {
		exercicios = exercicioDAO.buscarTodos();
		return exercicios;
	}

	public List<String> getPopularAutocompleteHistoricoPadrao(String historico) throws DaoException {
		if (StringUtils.isNotEmpty(historico) && historico.length() <= 6) {
			List<HistoricoPadrao> buscarPorHistorico = historicoPadraoDAO.buscarPorHistorico(historico);
			List<String> list = new ArrayList<>();
			for (HistoricoPadrao historicoPadrao : buscarPorHistorico) {
				list.add(historicoPadrao.getHistorico());
			}
			return list;
		} else {
			return null;
		}
	}

	public List<LancamentoPadrao> getPopularAutocompleteLancamentoPadrao(String nome) throws DaoException {
		List<LancamentoPadrao> buscarPorNome = lancamentoPadraoDAO.buscarPorNome(nome);
		return buscarPorNome;
	}

	public void onItemSelect(SelectEvent event) throws DaoException {
		lancamentoPadrao = lancamentoPadraoDAO.buscar(lancamentoPadrao.getId());
		entity.setDebito(lancamentoPadrao.getDebito());
		entity.setCredito(lancamentoPadrao.getCredito());
		entity.setHistorico(lancamentoPadrao.getHistoricoPadrao().getHistorico());
	}

	public LancamentoTipo[] getPopularComboLancamentoTipo() {
		return LancamentoTipo.values();
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public List<Exercicio> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<Exercicio> exercicios) {
		this.exercicios = exercicios;
	}

	public LancamentoPadrao getLancamentoPadrao() {
		return lancamentoPadrao;
	}

	public void setLancamentoPadrao(LancamentoPadrao lancamentoPadrao) {
		this.lancamentoPadrao = lancamentoPadrao;
	}

	@Override
	protected Lancamento novo() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		entity = new Lancamento();
		Exercicio exercicio = (Exercicio) session.getAttribute("exercicio");
		entity.setExercicio(exercicio);
		lancamentoPadrao = null;
		return entity;
	}

	@Override
	protected BaseDAO getDao() {
		return dao;
	}
	
}
