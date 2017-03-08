package br.com.cnt.web.jsf.managedbeans;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.coder.arqprime.model.dao.app.DaoException;
import br.com.cnt.model.dao.balanco.PlanoContasDAO;
import br.com.cnt.model.entity.balanco.PlanoContas;
import br.com.coder.arqprime.model.utils.Filtro;
import br.com.coder.arqprime.web.jsf.managedbeans.app.BaseManagedBean;

//@ManagedBean @ViewScoped
@javax.inject.Named 
@javax.faces.view.ViewScoped
public class PlanoContasManagedBean extends BaseManagedBean {

	private static final long serialVersionUID = 1L;

	private PlanoContas planoContas;
	@Inject private PlanoContasDAO dao;
	protected LazyDataModel<PlanoContas> model;
	protected Filtro filtro;

	@PostConstruct
	private void init() {
		novo(null);
		loadLazyModel();
	}

	private void loadLazyModel() {
		model = new LazyDataModel<PlanoContas>() {
			private static final long serialVersionUID = 1L;
			@Override
			public List<PlanoContas> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				filtro = new Filtro(PlanoContas.class, first, pageSize, sortField, sortOrder, filters);
				setRowCount(dao.getQuantidade2(filtro));
				return dao.buscar2(filtro);
			}
		    public PlanoContas getRowData(String rowKey) {
		    	PlanoContas obj = new PlanoContas();
		    	obj.setId(new Long(rowKey));
		    	 try {
					obj = dao.buscar(new Long(rowKey));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (DaoException e) {
					e.printStackTrace();
				}
		    	return obj;
		    }
		    public Object getRowKey(PlanoContas object) {
		    	return String.valueOf(object.getId());
		    }
		};
	}
	
	public void novo(ActionEvent evt) {
		planoContas = new PlanoContas();
	}

	public void salvar(ActionEvent evt) throws DaoException {
		try {
			dao.salvar(planoContas);
			message(null, "Registro salvo com sucesso.");
		} catch (Exception e) {
			message(e);
		}
	}


	public void excluir(ActionEvent evt) throws DaoException {
		try {
			dao.excluir(planoContas);
			novo(null);
			message(null, "Registro excluído com sucesso.");
		} catch (Exception e) {
			message(e);
		}
	}

	public PlanoContas getPlanoContas() {
		return planoContas;
	}

	public void setPlanoContas(PlanoContas planoContas) {
		this.planoContas = planoContas;
	}

	public LazyDataModel<PlanoContas> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<PlanoContas> model) {
		this.model = model;
	}
	
}

