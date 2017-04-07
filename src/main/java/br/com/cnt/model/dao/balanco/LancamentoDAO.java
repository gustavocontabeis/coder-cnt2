package br.com.cnt.model.dao.balanco;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.coder.arqprime.model.dao.app.BaseDAO;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.cnt.model.entity.balancete.dto.SaldoContabil;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaTipo;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.Lancamento;
import br.com.cnt.model.entity.balanco.dto.BalancoPatrimonial;
import br.com.cnt.model.entity.balanco.dto.SaldoBalanco;
import br.com.cnt.model.entity.balanco.dto.SaldoExercicio;
import br.com.cnt.model.entity.balanco.dto.SaldoRazao;
import br.com.cnt.model.entity.balanco.dto.ValorContabil;
import br.com.cnt.model.utils.ContaUtil;

@javax.inject.Named
@javax.faces.view.ViewScoped
public class LancamentoDAO extends BaseDAO<Lancamento> {

	private static final long serialVersionUID = 1L;

	public static final Logger LOGGER = LoggerFactory.getLogger(LancamentoDAO.class);

	@SuppressWarnings("unchecked")
	public List<Lancamento> buscarLancamentos(Exercicio exercicio, Date de, Date ate) {

		Session session = getSession();
		Query query = session.createQuery(
				"select lanc.id, lanc.date, debito.id, debito.nome, credito.id, credito.nome, lanc.valor from Lancamento lanc inner join lanc.debito debito inner join lanc.credito credito inner join lanc.exercicio ex where ex.empresa.id=:empresa and ex.ano = :ano and lanc.date between :de and :ate ");

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
				l.setDebito(new Conta((Long) tuple[2], (String) tuple[3]));
				l.setCredito(new Conta((Long) tuple[4], (String) tuple[5]));
				l.setValor((Float) tuple[6]);
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
	public List<SaldoContabil> buscarSaldoInicialDebito(Exercicio exercicio, Date de) {
		Session session = getSession();
		Query query = session.createQuery(
				"select distinct lanc.debito.id, sum(lanc.valor) as valor from Lancamento lanc where lanc.debito.id is not null and lanc.exercicio.empresa.id = :empresa and lanc.date < :de group by lanc.debito.id");

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
	public List<SaldoContabil> buscarSaldoInicialCredito(Exercicio exercicio, Date de) {
		Session session = getSession();
		Query query = session.createQuery(
				"select distinct lanc.credito.id, sum(lanc.valor) as valor from Lancamento lanc where lanc.credito.id is not null and lanc.exercicio.empresa.id = :empresa and lanc.date < :de group by lanc.credito.id");

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
	public List<SaldoContabil> buscarSaldoDebito(Exercicio exercicio, Date de, Date ate) {
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
	public List<SaldoContabil> buscarSaldoCredito(Exercicio exercicio, Date de, Date ate) {
		Session session = getSession();
		Query query = session.createQuery(
				"select distinct lanc.credito.id, sum(lanc.valor) as valor from Lancamento lanc where lanc.credito.id is not null and lanc.exercicio.id = :exercicio and lanc.date between :de and :ate group by lanc.credito.id");

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

	public List<SaldoContabil> buscarSaldosBalancete(Exercicio exercicio, Date de, Date ate) {

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
		List<SaldoContabil> listSID = buscarSaldoInicialDebito(exercicio, de);
		for (SaldoContabil saldoContabil : listSID) {
			SaldoContabil sc = (SaldoContabil) CollectionUtils.find(saldosContabeis,
					new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
			sc.setSaldoInicial(saldoContabil.getSaldoInicial());
		}

		List<SaldoContabil> listSIC = buscarSaldoInicialCredito(exercicio, de);
		for (SaldoContabil saldoContabil : listSIC) {
			SaldoContabil sc = (SaldoContabil) CollectionUtils.find(saldosContabeis,
					new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
			BigDecimal subtract = sc.getSaldoInicial().subtract(saldoContabil.getSaldoInicial());
			sc.setSaldoInicial(subtract);
		}

		List<SaldoContabil> listD = buscarSaldoDebito(exercicio, de, ate);
		for (SaldoContabil saldoContabil : listD) {
			SaldoContabil sc = (SaldoContabil) CollectionUtils.find(saldosContabeis,
					new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
			sc.setDebito(saldoContabil.getDebito());
		}

		List<SaldoContabil> listC = buscarSaldoCredito(exercicio, de, ate);
		for (SaldoContabil saldoContabil : listC) {
			SaldoContabil sc = (SaldoContabil) CollectionUtils.find(saldosContabeis,
					new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
			sc.setCredito(saldoContabil.getCredito());
		}

		/* Calcula o saldo final */
		for (SaldoContabil sc : saldosContabeis) {
			inicializarNulos(sc);
			BigDecimal saldoFinal = sc.getSaldoInicial().add(sc.getDebito()).subtract(sc.getCredito());
			sc.setSaldoFinal(saldoFinal);
		}

		calculandoBalancete(saldosContabeis);

		return saldosContabeis;
	}

	/*
	 * Coloca em ordem de estrutura decrescente Busca o maior nível Vai somando
	 * as contas do menor nível e colocalndo o saldo na próxima conta analitica
	 * que deverá ser a de um nível acima. As contas já utilizadas no calculo
	 * deverão ser retiradas da lista atual e colocada em outra lista. Segue a
	 * mesma lógica para os níveis menores até cabar a lista. Ordenas a lista
	 * que agora esta com as contas Atribuir à referencia da lista anterior
	 */
	private void calculandoBalancete(List<SaldoContabil> saldosContabeis) {

		List<SaldoContabil> jaSomados = new ArrayList<>();

		/* Ordenando em order decrescente de estrutura */
		Collections.sort(saldosContabeis);
		Collections.reverse(saldosContabeis);

		// System.out.println("----------- ordem decrescente
		// ---------------------------------");

		int maiorNivel = buscarMaiorNivel(saldosContabeis);

		// System.out.println("--------------soma------------------------------");

		int nivelAtual = maiorNivel;
		while (nivelAtual >= 1) {

			SaldoContabil soma = null;// retirar este aqui
			for (Iterator iterator = saldosContabeis.iterator(); iterator.hasNext();) {
				SaldoContabil saldoContabil = (SaldoContabil) iterator.next();

				// System.out.println("Tentando calcular: ");
				// System.out.println(" "+saldoContabil);
				if (saldoContabil.getConta().getNivel().intValue() != nivelAtual) {
					// System.out.println("É do nível "+saldoContabil.getConta()
					// .getNivel().intValue() +", não do nível "+nivelAtual);
					continue;
				}

				SaldoContabil saldoContabilPai = buscarContaPai(saldosContabeis, saldoContabil);
				// if(saldoContabil == null){
				// System.out.println("Não tem Pai.");
				// }

				if (saldoContabilPai != null) {
					// System.out.println("Antes: "+saldoContabilPai);
					saldoContabilPai.setCredito(saldoContabilPai.getCredito().add(saldoContabil.getCredito()));
					saldoContabilPai.setDebito(saldoContabilPai.getDebito().add(saldoContabil.getDebito()));
					saldoContabilPai.setSaldoFinal(saldoContabilPai.getSaldoFinal().add(saldoContabil.getSaldoFinal()));
					saldoContabilPai
							.setSaldoInicial(saldoContabilPai.getSaldoInicial().add(saldoContabil.getSaldoInicial()));
					// System.out.println("Depois: "+saldoContabilPai);
					jaSomados.add(saldoContabil);
					iterator.remove();
				}

			}
			nivelAtual--;
		}

		saldosContabeis.addAll(jaSomados);
		Collections.sort(saldosContabeis);

	}

	/**
	 * 
	 * @param saldosBalanco
	 */
	private void calculandoBalanco(List<SaldoBalanco> saldosBalanco) {
		
		// List<SaldoBalanco> jaSomados = new ArrayList<>();
		
		/* Ordenando em order decrescente de estrutura */
		
		System.out.println("----------- ordem decrescente------------------");
		Collections.sort(saldosBalanco);
		for (SaldoBalanco saldoBalanco : saldosBalanco) {
			System.out.println(saldoBalanco);
		}
		
		System.out.println("----------- ordem decrescente------------------");
		Collections.reverse(saldosBalanco);
		for (SaldoBalanco saldoBalanco : saldosBalanco) {
			System.out.println(saldoBalanco);
		}

		/* ------------------------------------------- */
		
		int maiorNivel = buscarMaiorNivelSaldoBalanco(saldosBalanco);

		// System.out.println("--------------soma------------------------------");

		int length = saldosBalanco.iterator().next().getSaldos().length;

		for (int indiceAno = 0; indiceAno < length; indiceAno++) {
			System.out.println("\nIndice do ano: "+indiceAno);
			int nivelAtual = maiorNivel;
			while (nivelAtual >= 1) {
				
				System.out.println("\n  Calculando nível:"+nivelAtual+"\n");

				// SaldoBalanco soma = null;// retirar este aqui
				for (Iterator<SaldoBalanco> iterator = saldosBalanco.iterator(); iterator.hasNext();) {
					SaldoBalanco saldoContabil = iterator.next();

					System.out.println("   Tentando calcular: "+saldoContabil.getConta());
					System.out.println("      " + saldoContabil);
					if (saldoContabil.getConta().getNivel().intValue() != nivelAtual) {
						System.out.println("      É do nível " + saldoContabil.getConta().getNivel().intValue() + ", não do nível " + nivelAtual);
						continue;
					}

					SaldoBalanco saldoContabilPai = buscarContaPai(saldosBalanco, saldoContabil);
					if (saldoContabil == null) {
						System.out.println("Não tem Pai.");
					}

					if (saldoContabilPai != null) {
						System.out.println("         Antes: " + saldoContabilPai);
						saldoContabilPai.getSaldos()[indiceAno].getValor().setValor(saldoContabilPai.getSaldos()[indiceAno].getValor().getValor().add(saldoContabil.getSaldos()[indiceAno].getValor().getValor()));
						System.out.println("         Depois: " + saldoContabilPai);
						// jaSomados.add(saldoContabil);
						// iterator.remove();
					}

				}
				nivelAtual--;
				// saldosBalanco.addAll(jaSomados);
				Collections.sort(saldosBalanco);
			}
		}

	}

	private SaldoBalanco buscarContaPai(List<SaldoBalanco> saldosBalanco, SaldoBalanco saldoContabil) {
		Conta pai = saldoContabil.getConta().getPai();
		if (pai != null) {
			for (SaldoBalanco sc : saldosBalanco) {
				if (sc.getConta().getId().equals(pai.getId())) {
					return sc;
				}
			}
		}
		return null;
	}

	private SaldoContabil buscarContaPai(List<SaldoContabil> saldosContabeis, SaldoContabil saldoContabil) {
		Conta pai = saldoContabil.getConta().getPai();
		if (pai != null) {
			for (SaldoContabil sc : saldosContabeis) {
				if (sc.getConta().getId().equals(pai.getId())) {
					return sc;
				}
			}
		}
		return null;
	}

	private int buscarMaiorNivelSaldoBalanco(List<SaldoBalanco> saldosBalanco) {
		int maiorNivel = 0;
		for (SaldoBalanco sc : saldosBalanco) {
			// System.out.println(sc);
			if (sc.getConta().getNivel() > maiorNivel) {
				maiorNivel = sc.getConta().getNivel();
			}
		}
		return maiorNivel;
	}

	private int buscarMaiorNivel(List<SaldoContabil> saldosContabeis) {
		int maiorNivel = 0;
		for (SaldoContabil sc : saldosContabeis) {
			// System.out.println(sc);
			if (sc.getConta().getNivel() > maiorNivel) {
				maiorNivel = sc.getConta().getNivel();
			}
		}
		return maiorNivel;
	}

	/**
	 * 
	 * @param de
	 * @param para
	 */
	private void atribuiValores(SaldoContabil de, SaldoContabil para) {
		para.setCredito(de.getCredito());
		para.setDebito(de.getDebito());
		para.setSaldoFinal(de.getSaldoFinal());
		para.setSaldoInicial(de.getSaldoInicial());
	}

	private SaldoContabil retornarSoma(List<SaldoContabil> filhos) {
		SaldoContabil soma = new SaldoContabil();
		inicializarNulos(soma);
		for (SaldoContabil saldoContabil : filhos) {
			LOGGER.debug(String.format("              ->%s-%-50s (%s - %s - %s - %s)",
					saldoContabil.getConta().getEstrutura(), saldoContabil.getConta().getNome(),
					saldoContabil.getSaldoInicial().floatValue(), saldoContabil.getDebito().floatValue(),
					saldoContabil.getCredito().floatValue(), saldoContabil.getSaldoFinal().floatValue()));
			soma.setSaldoInicial(saldoContabil.getSaldoInicial().add(soma.getSaldoInicial()));
			soma.setDebito(saldoContabil.getDebito().add(soma.getDebito()));
			soma.setCredito(saldoContabil.getCredito().add(soma.getCredito()));
			soma.setSaldoFinal(saldoContabil.getSaldoFinal().add(soma.getSaldoFinal()));
		}
		return soma;
	}

	private List<SaldoContabil> retornarSaldosNivelInferior(List<SaldoContabil> list, SaldoContabil saldoContabil) {
		List<SaldoContabil> retorno = new ArrayList<SaldoContabil>();
		int nivel = saldoContabil.getConta().getNivel() + 1;
		String estrutura = ContaUtil.estruturaSemZeros(saldoContabil.getConta());
		for (SaldoContabil sc : list) {
			if (nivel == sc.getConta().getNivel().intValue() && sc.getConta().getEstrutura().startsWith(estrutura)) {
				retorno.add(sc);
			}
		}
		return retorno;
	}

	private List<SaldoContabil> retornarSaldosContabeisDoNivel(List<SaldoContabil> list, int nivel) {
		List<SaldoContabil> retorno = new ArrayList<SaldoContabil>();
		for (SaldoContabil saldoContabil : list) {
			if (saldoContabil.getConta().getNivel().intValue() == nivel
					&& saldoContabil.getConta().getContaTipo() == ContaTipo.SINTETICA) {
				retorno.add(saldoContabil);
			}
		}
		return retorno;
	}

	private void inicializarNulos(SaldoContabil sc) {

		if (sc.getSaldoInicial() == null)
			sc.setSaldoInicial(BigDecimal.ZERO);

		if (sc.getDebito() == null)
			sc.setDebito(BigDecimal.ZERO);

		if (sc.getCredito() == null)
			sc.setCredito(BigDecimal.ZERO);

		if (sc.getSaldoFinal() == null)
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
	public List<SaldoRazao> retornarSaldosRazao(final Conta conta, Date de, Date ate) {

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
				obj.setIdLancamento((Long) tuple[0]);
				obj.setData((Date) tuple[1]);
				obj.setHistorico((String) tuple[2]);
				if (tuple[3] != null && new Long(tuple[3].toString()).equals(conta.getId()))
					obj.setVlrDebito(new BigDecimal((Float) tuple[5]));
				if (tuple[4] != null && new Long(tuple[4].toString()).equals(conta.getId()))
					obj.setVlrCredito(new BigDecimal((Float) tuple[5]));
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
				obj.setVlrDebito(new BigDecimal(tuple[0] != null ? (Double) tuple[0] : 0f));
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
				obj.setVlrCredito(new BigDecimal(tuple[0] != null ? (Double) tuple[0] : 0f));
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

	/**
	 * 
	 * @param exercicio
	 * @param quantExercicios
	 * @return
	 */
	public BalancoPatrimonial buscarBalancoPatrimonial(Exercicio exercicio, int quantExercicios) {

		PlanoContasDAO daoPlanoContas = new PlanoContasDAO();

		/* Busca todas as contas deste plano de contas */
		
		List<Conta> contas = daoPlanoContas.retornarContas(exercicio);

		int[] exercicios = new int[quantExercicios];

		for (int i = 0; i < exercicios.length; i++) {
			exercicios[i] = exercicio.getAno() - (i);
		}

		ArrayUtils.reverse(exercicios);

		/* Gera uma lista de Saldos de Balanco */
		List<SaldoBalanco> saldosBalanco = new ArrayList<SaldoBalanco>();

		for (Conta conta : contas) {
			SaldoBalanco saldoBalanco = new SaldoBalanco();
			saldoBalanco.setConta(conta);
			SaldoExercicio[] saldos = new SaldoExercicio[quantExercicios];
			for (int i = 0; i < saldos.length; i++) {
				SaldoExercicio saldoExercicio = new SaldoExercicio();
				saldoExercicio.setValor(new ValorContabil(conta, BigDecimal.ZERO));
				saldos[i] = saldoExercicio;
			}
			saldoBalanco.setSaldos(saldos);

			saldosBalanco.add(saldoBalanco);
		}

		BalancoPatrimonial bp = new BalancoPatrimonial();
		bp.setEmpresa(exercicio.getEmpresa());
		bp.setSaldos(saldosBalanco);

		int i = 0;
		for (int ano : exercicios) {

			Date de = new GregorianCalendar(ano + 1, Calendar.JANUARY, 1).getTime();

			/* Busca do banco os valores dos saldos iniciais e movimento */
			List<SaldoContabil> listSID = buscarSaldoInicialDebito(exercicio, de);
			for (SaldoContabil saldoContabil : listSID) {
				SaldoBalanco sb = (SaldoBalanco) CollectionUtils.find(saldosBalanco, new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
				sb.getSaldos()[i].getValor().setValor(saldoContabil.getSaldoInicial());
			}

			List<SaldoContabil> listSIC = buscarSaldoInicialCredito(exercicio, de);
			for (SaldoContabil saldoContabil : listSIC) {
				SaldoBalanco sb = (SaldoBalanco) CollectionUtils.find(saldosBalanco, new BeanPropertyValueEqualsPredicate("conta.id", saldoContabil.getConta().getId()));
				BigDecimal subtract = sb.getSaldos()[i].getValor().getValor().subtract(saldoContabil.getSaldoInicial());
				sb.getSaldos()[i].getValor().setValor(subtract);
			}

			i++;
		}

		for (SaldoBalanco saldoBalanco : saldosBalanco) {
			SaldoExercicio[] saldos = saldoBalanco.getSaldos();
			for (SaldoExercicio saldoExercicio : saldos) {
				if (saldoExercicio.getValor().getConta() == null) {
					saldoExercicio.getValor().setConta(saldoBalanco.getConta());
				} else {
					if (!saldoExercicio.getValor().getConta().equals(saldoBalanco.getConta())) {
						throw new RuntimeException("Erro ao calcular o balanço. A conta não está correta.");
					}
				}
			}
		}

		calculandoBalanco(saldosBalanco);
		
		for(Iterator<SaldoBalanco> it =saldosBalanco.iterator() ; it.hasNext() ;){
			SaldoBalanco next = it.next();
			String estrutura = next.getConta().getEstrutura();
			if( !(estrutura.startsWith("1.") || estrutura.startsWith("2.")) ){
				System.out.println(estrutura);
				it.remove();
			}
		}

		return bp;
	}

}
