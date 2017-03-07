package br.com.cnt.model.dao.balanco;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.Lancamento;
import br.com.cnt.model.entity.balanco.dto.SaldoContabil;
import br.com.cnt.model.entity.balanco.dto.SaldoRazao;
import br.com.cnt.model.utils.ContaUtil;

@javax.inject.Named @javax.faces.view.ViewScoped
public class LancamentoDAO extends BaseDAO<Lancamento> {
	
 	private static final long serialVersionUID = 1L;
 	
 	public static final Logger LOGGER = LoggerFactory.getLogger(LancamentoDAO.class);
 	
 	@SuppressWarnings("unchecked")
	public List<Lancamento>buscarLancamentos(Exercicio exercicio, Date de, Date ate){
 		
 		Session session = getSession();
 		Query query = session.createQuery("select lanc.id, lanc.date, debito.id, debito.nome, credito.id, credito.nome, lanc.valor from Lancamento lanc inner join lanc.debito debito inner join lanc.credito credito inner join lanc.exercicio ex where ex.empresa.id=:empresa and ex.ano = :ano and lanc.date between :de and :ate ");
 		
 		query.setLong("empresa", exercicio.getEmpresa().getId());
 		query.setInteger("ano", exercicio.getAno());
 		query.setDate("de", de);
 		query.setDate("ate", ate);
 		
 		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				Lancamento l = new Lancamento();
				l.setId((Long) tuple[0]);
				l.setDate((Date) tuple[1]);
				l.setDebito(new Conta((Long)tuple[2], (String)tuple[3]));
				l.setCredito(new Conta((Long)tuple[4], (String)tuple[5]));
				l.setValor((Float)tuple[6]);
				return l;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
 		return query.list();
 	}
 	
 	@SuppressWarnings("unchecked")
	public List<SaldoContabil>buscarSaldoInicialDebito(Exercicio exercicio, Date de){
 		Session session = getSession();
 		Query query = session.createQuery("select distinct lanc.debito.id, sum(lanc.valor) as valor from Lancamento lanc where lanc.debito.id is not null and lanc.exercicio.empresa.id = :empresa and lanc.date < :de group by lanc.debito.id");
 		
 		query.setLong("empresa", exercicio.getEmpresa().getId());
 		query.setDate("de", de);
 		
 		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				SaldoContabil sc = new SaldoContabil();
				sc.setConta(new Conta((Long) tuple[0], null));
				sc.setSaldoInicial(new BigDecimal(tuple[1].toString()));
				return sc;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
 		return query.list();
 	}
 	
 	@SuppressWarnings("unchecked")
	public List<SaldoContabil>buscarSaldoInicialCredito(Exercicio exercicio, Date de){
 		Session session = getSession();
 		Query query = session.createQuery("select distinct lanc.credito.id, sum(lanc.valor) as valor from Lancamento lanc where lanc.credito.id is not null and lanc.exercicio.empresa.id = :empresa and lanc.date < :de group by lanc.credito.id");
 		
 		query.setLong("empresa", exercicio.getEmpresa().getId());
 		query.setDate("de", de);
 		
 		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				SaldoContabil sc = new SaldoContabil();
				sc.setConta(new Conta((Long) tuple[0], null));
				sc.setSaldoInicial(new BigDecimal(tuple[1].toString()));
				return sc;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
 		return query.list();
 	}
 	
	@SuppressWarnings("unchecked")
	public List<SaldoContabil>buscarSaldoDebito(Exercicio exercicio, Date de, Date ate){
 		Session session = getSession();
 		String hql = "select distinct lanc.debito.id, sum(lanc.valor) as valor from Lancamento lanc where lanc.debito.id is not null and lanc.date between :de and :ate and lanc.exercicio.id = :exercicio group by lanc.debito.id";
 		Query query = session.createQuery(hql);
 		
 		query.setLong("exercicio", exercicio.getId());
 		query.setDate("de", de);
 		query.setDate("ate", ate);
 		
 		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				SaldoContabil sc = new SaldoContabil();
				sc.setConta(new Conta((Long) tuple[0], null));
				sc.setDebito(new BigDecimal(tuple[1].toString()));
				return sc;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		
 		return query.list();
 	}
 	
	@SuppressWarnings("unchecked")
	public List<SaldoContabil>buscarSaldoCredito(Exercicio exercicio, Date de, Date ate){
 		Session session = getSession();
 		Query query = session.createQuery("select distinct lanc.credito.id, sum(lanc.valor) as valor from Lancamento lanc where lanc.credito.id is not null and lanc.exercicio.id = :exercicio and lanc.date between :de and :ate group by lanc.credito.id");
 		
 		query.setLong("exercicio", exercicio.getId());
 		query.setDate("de", de);
 		query.setDate("ate", ate);
 		
 		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				SaldoContabil sc = new SaldoContabil();
				sc.setConta(new Conta((Long) tuple[0], null));
				sc.setCredito(new BigDecimal(tuple[1].toString()));
				return sc;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
 		return query.list();
 	}
 	
 	public List<SaldoContabil> buscarSaldosBalancete(Exercicio exercicio, Date de, Date ate){
		LancamentoDAO daoLancamento = new LancamentoDAO();
		PlanoContasDAO daoPlanoContas = new PlanoContasDAO();
		
		/* Busca todas as contas deste plano de contas */
		List<Conta> contas = daoPlanoContas.retornarContas(exercicio);
		
		/* Gera uma lista de Saldos Contabeis */
		List<SaldoContabil> saldosContabeis = new ArrayList<SaldoContabil>();
		for (Conta conta : contas) {
			SaldoContabil sc = new SaldoContabil();
			sc.setConta(conta);
			sc.setSaldoInicial(BigDecimal.ZERO);
			sc.setDebito(BigDecimal.ZERO);
			sc.setCredito(BigDecimal.ZERO);
			sc.setSaldoFinal(BigDecimal.ZERO);
			saldosContabeis.add(sc);
		}
		
		/* Busca do banco os valores dos saldos iniciais e movimento */
		List<SaldoContabil> listSID = daoLancamento.buscarSaldoInicialDebito(exercicio, de);
		for (SaldoContabil saldoContabil : listSID) {
			SaldoContabil sc = (SaldoContabil) CollectionUtils.find(saldosContabeis, new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
			sc.setSaldoInicial(saldoContabil.getSaldoInicial());
		}
		
		List<SaldoContabil> listSIC = daoLancamento.buscarSaldoInicialCredito(exercicio, de);
		for (SaldoContabil saldoContabil : listSIC) {
			SaldoContabil sc = (SaldoContabil) CollectionUtils.find(saldosContabeis, new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
			BigDecimal subtract = sc.getSaldoInicial().subtract(saldoContabil.getSaldoInicial());
			sc.setSaldoInicial(subtract);
		}
		
		List<SaldoContabil> listD = daoLancamento.buscarSaldoDebito(exercicio, de, ate);
		for (SaldoContabil saldoContabil : listD) {
			SaldoContabil sc = (SaldoContabil) CollectionUtils.find(saldosContabeis, new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
			sc.setDebito(saldoContabil.getDebito());
		}
		
		List<SaldoContabil> listC = daoLancamento.buscarSaldoCredito(exercicio, de, ate);
		for (SaldoContabil saldoContabil : listC) {
			SaldoContabil sc = (SaldoContabil) CollectionUtils.find(saldosContabeis, new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
			sc.setCredito(saldoContabil.getCredito());
		}
		
		/* Calcula o saldo final */
		for (SaldoContabil sc : saldosContabeis) {
			inicializarNulos(sc);
			BigDecimal saldoFinal = sc.getSaldoInicial().add(sc.getDebito()).subtract(sc.getCredito());
			sc.setSaldoFinal(saldoFinal);
		}
		
		//calculandoBalancete1(saldosContabeis);
		calculandoBalancete2(saldosContabeis);
		
		return saldosContabeis;
 	}

	private void calculandoBalancete2(List<SaldoContabil> saldosContabeis) {
		/* Ordenando em order crescente de estrutura */
		Collections.sort(saldosContabeis, new Comparator<SaldoContabil>() {
			@Override
			public int compare(SaldoContabil sc1, SaldoContabil sc2) {
				return sc1.getConta().getEstrutura().compareTo(sc2.getConta().getEstrutura());
			}
		});
		/* Fazendo os somatórios */
		for (int nivel = 6; nivel > 0; nivel--) {
			LOGGER.debug("Calculando todos os de nivel "+nivel);
			List<SaldoContabil> saldosNivel = retornarSaldosContabeisDoNivel(saldosContabeis, nivel);
			LOGGER.debug("Foram encontrados {} contas no nível {}.",saldosNivel.size(), nivel);
			for (SaldoContabil saldoContabil : saldosNivel) {
				List<SaldoContabil> filhos = retornarSaldosNivelInferior(saldosContabeis, saldoContabil);
				if(filhos.size()>0){
					LOGGER.debug(String.format("   Calculando:  %s-%s (%d contas)", saldoContabil.getConta().getEstrutura(), saldoContabil.getConta().getNome(), filhos.size()));
					SaldoContabil total = retornarSoma(filhos);
					saldoContabil.setSaldoInicial(total.getSaldoInicial());
					saldoContabil.setDebito(total.getDebito());
					saldoContabil.setCredito(total.getCredito());
					saldoContabil.setSaldoFinal(total.getSaldoFinal());
					LOGGER.debug(String.format("Valor da conta: %s-%-50s (%s - %s - %s - %s)", 
							saldoContabil.getConta().getEstrutura(), 
							saldoContabil.getConta().getNome(), 
							saldoContabil.getSaldoInicial().floatValue(), 
							saldoContabil.getDebito().floatValue(), 
							saldoContabil.getCredito().floatValue(), 
							saldoContabil.getSaldoFinal().floatValue()
					));
				}else{
					System.out.printf("Conta %s-%s não possui filhos\n", saldoContabil.getConta().getEstrutura(), saldoContabil.getConta().getNome(), filhos.size());				
				}
					
			}
		}
	}

	private void calculandoBalancete1(List<SaldoContabil> saldosContabeis) {
		
		/* Ordenando em order decrescente de estrutura */
		Collections.sort(saldosContabeis, new Comparator<SaldoContabil>() {
			@Override
			public int compare(SaldoContabil sc1, SaldoContabil sc2) {
				return sc2.getConta().getEstrutura().compareTo(sc1.getConta().getEstrutura());
			}
		});
		
		SaldoContabil sc1 = null;
		for (SaldoContabil sc2 : saldosContabeis) {
			LOGGER.debug("--------------------------");
			LOGGER.debug("{}", sc1);
			LOGGER.debug("{}", sc2);
			if(sc1 == null){
				sc1 = sc2;
				continue;
			}
			if(ContaTipo.ANALITICA == sc2.getConta().getContaTipo()){// && ContaUtil.compararNivel(sc1.getConta(), sc2.getConta()) == 0
				//Soma
				sc1.setSaldoInicial( sc1.getSaldoInicial().add(sc2.getSaldoInicial()) );
				sc1.setDebito( sc1.getDebito().add(sc2.getDebito()) );
				sc1.setCredito( sc1.getCredito().add(sc2.getCredito()) );
				sc1.setSaldoFinal( sc1.getSaldoFinal().add(sc2.getSaldoFinal()) );
			}else{
				sc2.setSaldoInicial( new BigDecimal(sc1.getSaldoInicial().floatValue()) );
				sc2.setDebito( new BigDecimal(sc1.getDebito().floatValue()) );
				sc2.setCredito( new BigDecimal(sc1.getCredito().floatValue()) );
				sc2.setSaldoFinal( new BigDecimal(sc1.getSaldoFinal().floatValue()) );
			}
			
			LOGGER.debug("{}", ContaUtil.retornarNivel(sc1.getConta()));
			LOGGER.debug("{}", ContaUtil.retornarNivel(sc1.getConta()));
			
			//Soma todas as analiticas
			//Soma as sintéticas
			
//			if(ContaUtil.compararNivel(sc1.getConta(), sc2.getConta()) > 0){
//				//Zera valores
//				sc1.setSaldoInicial( BigDecimal.ZERO );
//				sc1.setDebito( BigDecimal.ZERO );
//				sc1.setCredito( BigDecimal.ZERO );
//				sc1.setSaldoFinal( BigDecimal.ZERO );
//			}
			
		}
		
		/* Ordenando em order crescente de estrutura */
		Collections.sort(saldosContabeis, new Comparator<SaldoContabil>() {
			@Override
			public int compare(SaldoContabil sc1, SaldoContabil sc2) {
				return sc1.getConta().getEstrutura().compareTo(sc2.getConta().getEstrutura());
			}
		});
	}

	private SaldoContabil retornarSoma(List<SaldoContabil> filhos) {
		SaldoContabil soma = new SaldoContabil();
		inicializarNulos(soma);
		for (SaldoContabil saldoContabil : filhos) {
			LOGGER.debug(String.format("              ->%s-%-50s (%s - %s - %s - %s)", 
					saldoContabil.getConta().getEstrutura(), 
					saldoContabil.getConta().getNome(), 
					saldoContabil.getSaldoInicial().floatValue(), 
					saldoContabil.getDebito().floatValue(), 
					saldoContabil.getCredito().floatValue(), 
					saldoContabil.getSaldoFinal().floatValue())
			);
			soma.setSaldoInicial(saldoContabil.getSaldoInicial().add(soma.getSaldoInicial()));
			soma.setDebito(saldoContabil.getDebito().add(soma.getDebito()));
			soma.setCredito(saldoContabil.getCredito().add(soma.getCredito()));
			soma.setSaldoFinal(saldoContabil.getSaldoFinal().add(soma.getSaldoFinal()));
		}
		return soma;
	}

	private List<SaldoContabil> retornarSaldosNivelInferior(List<SaldoContabil> list, SaldoContabil saldoContabil) {
		List<SaldoContabil> retorno = new ArrayList<SaldoContabil>();
		int nivel = saldoContabil.getConta().getNivel()+1;
		String estrutura = ContaUtil.estruturaSemZeros(saldoContabil.getConta());
		for (SaldoContabil sc : list) {
			if(nivel == sc.getConta().getNivel().intValue() && sc.getConta().getEstrutura().startsWith(estrutura)){
				retorno.add(sc);
			}
		}
		return retorno;
	}

	private List<SaldoContabil> retornarSaldosContabeisDoNivel(List<SaldoContabil> list, int nivel) {
		List<SaldoContabil> retorno = new ArrayList<SaldoContabil>();
		for (SaldoContabil saldoContabil : list) {
			if(saldoContabil.getConta().getNivel().intValue() == nivel &&
					saldoContabil.getConta().getContaTipo() == ContaTipo.SINTETICA){
				retorno.add(saldoContabil);
			}
		}
		return retorno;
	}

	private void inicializarNulos(SaldoContabil sc) {
		
		if(sc.getSaldoInicial()==null)
			sc.setSaldoInicial(BigDecimal.ZERO);
		
		if(sc.getDebito()==null)
			sc.setDebito(BigDecimal.ZERO);
		
		if(sc.getCredito()==null)
			sc.setCredito(BigDecimal.ZERO);
		
		if(sc.getSaldoFinal()==null)
			sc.setSaldoFinal(BigDecimal.ZERO);
	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> buscarLancamentoComposto(Lancamento lanc) {
 		Session session = getSession();
 		Query query = session.createQuery("from Lancamento obj where obj.lancamentoPrincipal.id = :id");
 		query.setLong("id", lanc.getId());
 		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SaldoRazao>retornarSaldosRazao(final Conta conta, Date de, Date ate){
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(" select \n "));
		sb.append(String.format(" 	obj.id, \n "));
		sb.append(String.format(" 	obj.date, \n "));
		sb.append(String.format(" 	obj.historico, \n "));
		sb.append(String.format(" 	obj.debito.id, \n "));
		sb.append(String.format(" 	obj.credito.id, \n "));
		sb.append(String.format(" 	obj.valor\n "));
		sb.append(String.format(" from Lancamento obj \n "));
		sb.append(String.format(" where ((obj.debito.id is not null and obj.debito.id = :conta ) \n "));
		sb.append(String.format("    or (obj.credito.id is not null and obj.credito.id = :conta ))\n "));
		sb.append(String.format("   and (obj.date between :de and :ate)\n "));
		
 		Session session = getSession();
 		Query query = session.createQuery(sb.toString());
 		query.setLong("conta", conta.getId());
 		query.setDate("de", de);
 		query.setDate("ate", ate);
 		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				SaldoRazao obj = new SaldoRazao();
				obj.setIdLancamento((Long)tuple[0]);
				obj.setData((Date)tuple[1]);
				obj.setHistorico((String)tuple[2]);
				if(tuple[3] != null && new Long(tuple[3].toString()).equals( conta.getId() ) )
					obj.setVlrDebito(new BigDecimal((Float)tuple[5]));
				if(tuple[4] != null && new Long(tuple[4].toString()).equals( conta.getId() ) )
					obj.setVlrCredito(new BigDecimal((Float)tuple[5]));
				return obj;
			}
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
 		@SuppressWarnings("rawtypes")
		List list = query.list();
 		return list;
	}

	public Lancamento buscar(Long id) {
		Session session = getSession();
		Query query = session.getNamedQuery("Lancamento-buscar");
		query.setLong("id", id);
		Object uniqueResult = query.uniqueResult();
		session.close();
		return (Lancamento) uniqueResult;
	}

	public SaldoRazao retornarSaldoInicialRazaoDebido(final Conta conta, Date de) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(" select \n "));
		sb.append(String.format(" 	sum(obj.valor)\n "));
		sb.append(String.format(" from Lancamento obj \n "));
		sb.append(String.format(" where (obj.debito.id = :conta ) \n "));
		sb.append(String.format("   and (obj.date < :de)\n "));
		
 		Session session = getSession();
 		Query query = session.createQuery(sb.toString());
 		query.setLong("conta", conta.getId());
 		query.setDate("de", de);
 		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				SaldoRazao obj = new SaldoRazao();
				obj.setVlrDebito(new BigDecimal(tuple[0]!=null?(Double)tuple[0]:0f));
				return obj;
			}
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
 		@SuppressWarnings("rawtypes")
		SaldoRazao uniqueResult = (SaldoRazao) query.uniqueResult();
 		return uniqueResult;
	}

	public SaldoRazao retornarSaldoInicialRazaoCredito(Conta conta, Date de) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(" select \n "));
		sb.append(String.format(" 	sum(obj.valor)\n "));
		sb.append(String.format(" from Lancamento obj \n "));
		sb.append(String.format(" where (obj.credito.id = :conta ) \n "));
		sb.append(String.format("   and (obj.date < :de)\n "));
		
 		Session session = getSession();
 		Query query = session.createQuery(sb.toString());
 		query.setLong("conta", conta.getId());
 		query.setDate("de", de);
 		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				SaldoRazao obj = new SaldoRazao();
				obj.setVlrCredito(new BigDecimal(tuple[0]!=null?(Double)tuple[0]:0f));
				return obj;
			}
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
 		@SuppressWarnings("rawtypes")
		SaldoRazao uniqueResult = (SaldoRazao) query.uniqueResult();
 		return uniqueResult;
	}

}
 