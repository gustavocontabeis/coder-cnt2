package br.com.coder.cms.web.listeners;

import java.io.File;
import java.net.URL;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.model.dao.app.usuarios.UsuarioDAO;
import br.com.coder.arqprime.model.entity.app.usuarios.Usuario;
import br.com.coder.arqprime.model.utils.ArquivoUtils;
import br.com.coder.arqprime.model.utils.GenerateMD5;

@WebListener
public class AppServletContextListener implements ServletContextListener{
	
	@Override
    public void contextInitialized(ServletContextEvent event) {
        try {
			inicializarDados();
			importarArquivosCSV();
		} catch (DaoException e) {
			e.printStackTrace();
		}
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
						insert.append(linha+") values (");
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
		//new UsuarioDAO().salvar(usuario);
		
	}

}
