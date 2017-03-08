package br.com.cnt.web.jsf.managedbeans;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.cnt.model.dao.balanco.EmpresaDAO;
import br.com.cnt.model.dao.balanco.ExercicioDAO;
import br.com.cnt.model.dto.ConfiguracaoUsuarioDTO;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.utils.JSONUtil;
import br.com.coder.arqprime.model.dao.app.ConfiguracaoDAO;
import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.model.dao.app.usuarios.UsuarioDAO;
import br.com.coder.arqprime.model.entity.app.Configuracao;
import br.com.coder.arqprime.model.entity.app.usuarios.Usuario;
import br.com.coder.arqprime.model.utils.ConfiguracaoUtil;
import br.com.coder.arqprime.model.utils.StringUtil;
import br.com.coder.arqprime.web.jsf.managedbeans.app.BaseManagedBean;
import br.com.coder.arqprime.web.jsf.managedbeans.app.LoginManagedBean;

@javax.inject.Named
@javax.enterprise.context.SessionScoped
//@javax.inject.Named
//@javax.faces.view.ViewScoped
public class Config2ManagedBean extends BaseManagedBean {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginManagedBean.class.getSimpleName());

	private String username;
	private String password;
	private Usuario usuario;
	private Empresa empresa;
	private Exercicio exercicio;
	private Date de, ate;
	private String periodo;
	private String template = "layout-1", fontSize = "12px";
	
	private List<Empresa> empresas;
	private List<Exercicio> exercicios;
	
	@Inject private EmpresaDAO empresaDAO;
	@Inject private ExercicioDAO exercicioDAO;
	@Inject private UsuarioDAO usuarioDAO;
	@Inject private ConfiguracaoDAO configuracaoDAO;
	
	@PostConstruct
	private void init() throws DaoException, IOException{
		empresas = getPopularComboEmpresa();
		buscarConfiguracoesUsuario();
	}

	public List<Empresa> getPopularComboEmpresa() {
		return empresaDAO.buscarTodos();
	}
	
	public void aoSelecionarEmpresa() throws DaoException{
		popularComboExercicio();
	}
	
	public void salvarConfiguracao(ActionEvent evt) throws DaoException{
		
		ConfiguracaoUsuarioDTO config = new ConfiguracaoUsuarioDTO();
		config.setEmpresa(empresa!=null?empresa.getId():null);
		config.setExercicio(exercicio!=null?exercicio.getId():null);
		config.setPeriodo(StringUtils.isNotBlank(periodo)?periodo:null);
		config.setTemplate(template);
		
		Gson create = new GsonBuilder().create();
		String json = create.toJson(config);
		
		String key = String.format(ConfiguracaoUtil.CONFIG_USUARIO, loginBean.getUsuario().getId());
		configuracaoDAO.salvarConfiguracao(key, json);
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("exercicio", exercicio);
		session.setAttribute("de", de);
		session.setAttribute("ate", ate);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("A configuração foi salva com sucesso."));
	}
	
	public void popularComboExercicio(){
		if(empresa != null){
			exercicios = exercicioDAO.buscarExercicio(empresa);
		}else{
			exercicio = null;
		}
		periodo = null;
		de = null;
		ate = null;
	}
	
	public void aoSelecionarExercicio(){
		periodo = null;
		de = null;
		ate = null;
	}
	
	public void selecionarPeriodo() throws IOException {
		if(exercicio!= null && !StringUtil.isBlank(periodo)){
			switch (periodo) {
			case "a":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.JANUARY, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.DECEMBER, 31).getTime();
				break;
			case "1s":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.JANUARY, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.JUNE, 30).getTime();
				break;
			case "2s":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.JULY, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.DECEMBER, 31).getTime();
				break;
			case "1t"://Trimestre
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.JANUARY, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.MARCH, 31).getTime();
				break;
			case "2t":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.APRIL, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.JUNE, 30).getTime();
				break;
			case "3t":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.JULY, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.SEPTEMBER, 30).getTime();
				break;
			case "4t":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.OCTOBER, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.DECEMBER, 31).getTime();
				break;
			case "1b"://Bimestre
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.JANUARY, 1).getTime();
				Calendar cal = new GregorianCalendar(exercicio.getAno(), Calendar.MARCH, 1);
				cal.add(Calendar.DAY_OF_MONTH, -1);
				this.ate = cal.getTime(); 
				break;
			case "2b":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.MARCH, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.APRIL, 30).getTime();
				break;
			case "3b":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.MAY, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.JUNE, 30).getTime();
				break;
			case "4b":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.JULY, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.AUGUST, 31).getTime();
				break;
			case "5b":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.SEPTEMBER, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.OCTOBER, 31).getTime();
				break;
			case "6b":
				this.de = new GregorianCalendar(exercicio.getAno(), Calendar.NOVEMBER, 1).getTime();
				this.ate = new GregorianCalendar(exercicio.getAno(), Calendar.DECEMBER, 31).getTime();
				break;
			}
//			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//			sessao.setAttribute(ConstantesComuns.EXERCICIO_SESSAO, exercicio);
//			sessao.setAttribute(ConstantesComuns.PERIODO_SESSAO_DE, de);
//			sessao.setAttribute(ConstantesComuns.PERIODO_SESSAO_ATE, ate);
		}
	}

	public void login(ActionEvent event) throws IOException, DaoException {

		Usuario usuario = usuarioDAO.buscarComPerfis(this.username);
		
		String msg = "Login invalido";
		
		if(usuario == null){
			LOGGER.debug("Usuario {} não confere.", username);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
			redirecionarLogin(msg);
			return;
		}
		
		if (this.password.equals(usuario.getSenha())) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			
			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			HttpSession session = request.getSession();
			session.setAttribute("usuario", usuario);
			// request.login(username, password);
			
			this.usuario = usuario;
			
			buscarConfiguracoesUsuario();
			
			if(session.getAttribute("destino") != null){
				HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
				String destino = (String) session.getAttribute("destino");
				LOGGER.debug("Redirecionando para : "+ destino);
				response.sendRedirect(destino);
				session.removeAttribute("destino");
			}else{
				redirecionarPaginaInicial();
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem vindo", username));
		}else{
			LOGGER.debug("Usuario {} e senha {} não conferem.", username, password);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
			redirecionarLogin(msg);
		}

	}

	private void buscarConfiguracoesUsuario() throws DaoException, IOException {
		
		boolean ok = false;
		if(usuario!=null && usuario.getId()!=null){
			ok = true;
		}
		
		if(!ok){
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			HttpSession session = request.getSession();
			usuario = (Usuario) session.getAttribute("usuario");
			if(usuario == null)
				return;
		}
		

		
		String chave = String.format(ConfiguracaoUtil.CONFIG_USUARIO, usuario.getId());
		Configuracao config = configuracaoDAO.buscarPorChave(chave);
		if(config != null){
			ConfiguracaoUsuarioDTO configDTO = (ConfiguracaoUsuarioDTO) JSONUtil.toObject(config.getValor(), ConfiguracaoUsuarioDTO.class);
			empresa = empresaDAO.buscar(configDTO.getEmpresa());
			popularComboExercicio();
			exercicio = exercicioDAO.buscar(configDTO.getExercicio());
			periodo = configDTO.getPeriodo();
			template = configDTO.getTemplate();
			selecionarPeriodo();
		}
		
	}
	
	public void loginDesenv(ActionEvent event) throws IOException, DaoException {
		this.username = "gustavo";
		this.password = "123";
		login(null);
//		empresa = empresas.iterator().next();
//		popularComboExercicio();
//		exercicio = exercicios.iterator().next();
//		selecionarAno();
	}
	
	private void redirecionarLogin(String msg) throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		response.sendRedirect(request.getContextPath() + "/pages/login/login.jsf"+(StringUtils.isNotBlank(msg)?"?msg="+msg:""));
	}

	private void redirecionarPaginaInicial() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		response.sendRedirect(request.getContextPath() + "/pages/configuracoes/");
	}

	public void logout(ActionEvent event) throws IOException {
		LOGGER.debug("..>> Logout <<..");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		request.getSession().invalidate();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		response.sendRedirect(request.getContextPath() + "/pages/login/login.jsf");
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLogado(){
		return this.usuario != null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Exercicio> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<Exercicio> exercicios) {
		this.exercicios = exercicios;
	}

	public Exercicio getExercicio() {
		return exercicio;
	}

	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}

	public Date getDe() {
		return de;
	}

	public void setDe(Date de) {
		this.de = de;
	}

	public Date getAte() {
		return ate;
	}

	public void setAte(Date ate) {
		this.ate = ate;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

}
