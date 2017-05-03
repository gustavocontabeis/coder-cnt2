package br.com.coder.cms.web.listeners;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import br.com.cnt.model.dao.balanco.HibernateUtility;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.PlanoContas;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.model.dao.app.usuarios.UsuarioDAO;
import br.com.coder.arqprime.model.entity.app.usuarios.Usuario;
import br.com.coder.arqprime.model.utils.ArquivoUtils;
import br.com.coder.arqprime.model.utils.GenerateMD5;
import br.com.coder.arqprime.model.utils.HibernateUtil;

@WebListener
public class AppServletContextListener implements ServletContextListener{
	
	@Override
    public void contextInitialized(ServletContextEvent event) {
        try {
        	inicializarCache();
        	//inicializarCache();
			//inicializarDados(); 
			//importarArquivosCSV();
        	System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	private void inicializarCache() {
		
		Session session = HibernateUtil.getSession();
		Statistics statistics = HibernateUtil.getStatistics();
		
		System.out.println("Stats enabled="+statistics.isStatisticsEnabled());
		statistics.setStatisticsEnabled(true);
		System.out.println("Stats enabled="+statistics.isStatisticsEnabled());
		
    	List<Empresa> empresas = session.createCriteria(Empresa.class).list();
    	for (Empresa object : empresas) {
			session.load(Empresa.class, object.getId());
		}
    	printStats(statistics, 1);
    	
		List<PlanoContas> PlanoContasList = session.createCriteria(PlanoContas.class).list();
		for (PlanoContas planoContas : PlanoContasList) {
			session.load(PlanoContas.class, planoContas.getId());
		}
		printStats(statistics, 2);
		
		List<Exercicio> exercicioList = session.createCriteria(Exercicio.class).list();
		for (Exercicio exercicio : exercicioList) {
			session.load(Exercicio.class, exercicio.getId());
		}
		printStats(statistics, 3);
		
		List<Conta> contaList = session.createCriteria(Conta.class).list();
		for (Conta conta : contaList) {
			session.load(Conta.class, conta.getId());
		}
		printStats(statistics, 4);
		
		session.close();
	}
	
	private static void printStats(Statistics stats, int i) {
		System.out.println("***** " + i + " *****");
		System.out.println("Fetch Count=" + stats.getEntityFetchCount());
		System.out.println("Cache Hit Count=" + stats.getSecondLevelCacheHitCount());
		System.out.println("Cache Miss Count=" + stats.getSecondLevelCacheMissCount());
		System.out.println("Cache Put Count=" + stats.getSecondLevelCachePutCount());
		System.out.println();
	}

	private void importarArquivosCSV() {
		URL resource = this.getClass().getResource("/csv");
		System.out.println(resource.getFile());
		File[] listFiles = new File(resource.getFile()).listFiles();
		for (File file : listFiles) {
			String path = file.getPath();
			System.out.println(path);
			if(path.toLowerCase().endsWith(".csv")){
				String name = file.getName();
				StringBuilder insert = new StringBuilder("insert into "+name.replace(".csv", "")+" (");
				String[] linhas = ArquivoUtils.ler(file);
				for (int i = 0; i < linhas.length; i++) {
					String linha = linhas[i];
					if(i == 0){
						insert.append(linha+" values (");
					}else{
						String x = insert.toString()+linha+");";
						System.out.println(x);
					}
				}
			}
		}
	}

	@Override
    public void contextDestroyed(ServletContextEvent event) {
        
    }
	
    private void inicializarDados() throws DaoException {
		Usuario usuario = new Usuario();
		usuario.setDtSenha(new Date());
		usuario.setDtUltimoAcesso(new Date());
		usuario.setId(null);
		usuario.setDtInativo(null);
		usuario.setEmail("gustavos.cadastros@gmail.com");
		usuario.setLogin("gustavo");
		usuario.setPerfis("USU-ADM,WEB,USU");
		String generate = GenerateMD5.generateMD5("123");
		usuario.setSenha(generate);
		new UsuarioDAO().salvar(usuario);
		
	}

}
