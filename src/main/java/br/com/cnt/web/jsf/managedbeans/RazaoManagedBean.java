package br.com.cnt.web.jsf.managedbeans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.coder.arqprime.web.jsf.managedbeans.app.BaseManagedBean;
import br.com.cnt.model.dao.balanco.ContaDAO;
import br.com.cnt.model.dao.balanco.RazaoDAO;
import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.cnt.model.entity.balanco.dto.Razao;

@javax.inject.Named @javax.faces.view.ViewScoped
public class RazaoManagedBean extends BaseManagedBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Razao razao;
	
	@PostConstruct
	private void init(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String parameter = request.getParameter("id");
		consultarRazao(parameter);
	}
	
	private void consultarRazao(String parameter) {
		Conta conta = null;
		try {
			conta = new ContaDAO().buscar(new Long(parameter));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		Exercicio exercicio = null;//loginBean.getExercicio();
		Date de = null;// = loginBean.getDe();
		Date ate = null;// = loginBean.getAte();
		
		this.razao = new RazaoDAO().retornarRazao(conta, de, ate);
		this.razao.setDe(de);
		this.razao.setAte(ate);
		this.razao.setExercicio(exercicio);
		
		razao.getExercicio().getEmpresa().getRazaoSocial();
	}
	
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void listener(ComponentSystemEvent evt) throws AbortProcessingException{
		consultarRazao(id.toString());
	}


	public void setRazao(Razao razao) {
		this.razao = razao;
	}

	public Razao getRazao() {
		return razao;
	}

}
