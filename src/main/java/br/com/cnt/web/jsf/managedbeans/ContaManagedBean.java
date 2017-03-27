package br.com.cnt.web.jsf.managedbeans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ReorderEvent;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cnt.model.dao.balanco.ContaDAO;
import br.com.cnt.model.dao.balanco.EmpresaDAO;
import br.com.cnt.model.dao.balanco.ExercicioDAO;
import br.com.cnt.model.dao.balanco.PlanoContasDAO;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaOrigem;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.PlanoContas;
import br.com.cnt.model.utils.ContaUtil;
import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.web.jsf.filters.SegurancaFilter;
import br.com.coder.arqprime.web.jsf.managedbeans.app.CrudManagedBean;

@Named @ViewScoped
public class ContaManagedBean extends CrudManagedBean<Conta, ContaDAO> {
	
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Empresa> empresas;
	private PlanoContas planoconta;
	private List<PlanoContas> planocontas;
	private Exercicio exercicio;
	private List<Exercicio>exercicios;
	private Conta filtro;

	@Inject private ContaDAO dao;
	@Inject private EmpresaDAO empresaDAO;
	@Inject private PlanoContasDAO planoContasDAO;
	@Inject private ExercicioDAO exercicioDAO;

	@PostConstruct
	private void init() {
		System.out.println("ContaManagedBean.init() ");
		novo(null);
		empresas = getPopularComboEmpresa();
		planocontas = getPopularComboPlanoContas();
		//exercicios = getPopularComboExercicio();
		filtro = new Conta();
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
	
//	@Override
//	protected Map<String, Object> getFilters() {
//		if(exercicio !=null){
//			Map<String, Object> map = new HashMap<>();
//			map.put("empresa.id", exercicio.getEmpresa().getId());
//			map.put("planoContas.id", exercicio.getPlanoContas().getId());
//			return map;
//		}
//		return super.getFilters();
//	}

	@Override
	protected Integer getQuantidade2() {
		try {
			if(filtro != null 
					&& ((filtro.getEmpresa()!=null && filtro.getEmpresa().getId()!=null) 
							|| ((filtro.getPlanoContas()!=null && filtro.getPlanoContas().getId()!=null) ))){
				return dao.getQuantidade2(filtro);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected List<Conta> buscar2() {
		try {
			if(filtro != null 
					&& ((filtro.getEmpresa()!=null && filtro.getEmpresa().getId()!=null) 
							|| ((filtro.getPlanoContas()!=null && filtro.getPlanoContas().getId()!=null) ))){
				return dao.buscar2(filtro);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected String getSortField(String sortField) {
		return "estrutura asc, contaTipo desc, ordem asc";
	}
	
	public void onRowReorder(ReorderEvent event) throws DaoException {
		Object source = event.getSource();
		
		int fromIndex = event.getFromIndex();
		int toIndex = event.getToIndex();
		
		Conta contaFrom = list.get(fromIndex);
		
		Conta contaAcima = list.get(toIndex-1);
		Conta toConta = list.get(toIndex);
		
		LOGGER.debug("De {} para {}", fromIndex, toIndex);
		LOGGER.debug("Conta {} - {}", contaAcima.getEstrutura(), contaAcima.getNome());
		LOGGER.debug("Conta {} - {}", contaFrom.getEstrutura(), contaFrom.getNome());
		
		//se acima é sintetico e eu sou analitico, então coloca dentro
		//se acima é analitico e eu sou analitico, então coloca na mesma estrutura em uma ordem abaixo
		if(ContaTipo.SINTETICA == contaAcima.getContaTipo()
				&& ContaTipo.ANALITICA == contaFrom.getContaTipo()){
			//String estrutura = ContaUtil.retornarEstruturaFilho(contaAcima.getEstrutura());
			contaFrom.setEstrutura(contaAcima.getEstrutura());
			contaFrom.setOrdem(1);
		}
		if(ContaTipo.ANALITICA == contaAcima.getContaTipo()
				&& ContaTipo.ANALITICA == contaFrom.getContaTipo()){
			contaFrom.setEstrutura(contaAcima.getEstrutura());
			if(contaAcima.getOrdem()!=null){
				contaFrom.setOrdem(contaAcima.getOrdem()+1);
			}else{
				contaFrom.setOrdem(1);
			}
		}
		
		LOGGER.debug("Conta {} - {}", contaFrom.getEstrutura(), contaFrom.getNome());
		LOGGER.debug("-------");
		entity = contaFrom;
		salvar(null);
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

	public List<Exercicio> getPopularComboExercicio() {
		exercicios = exercicioDAO.buscarTodos();
		return exercicios;
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

	public Exercicio getExercicio() {
		return exercicio;
	}

	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}

	public List<Exercicio> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<Exercicio> exercicios) {
		this.exercicios = exercicios;
	}

	public PlanoContas getPlanoConta() {
		return planoconta;
	}

	public void setPlanoConta(PlanoContas planoConta) {
		this.planoconta = planoConta;
	}

	public Conta getFiltro() {
		return filtro;
	}

	public void setFiltro(Conta filtro) {
		this.filtro = filtro; 
	}

}
