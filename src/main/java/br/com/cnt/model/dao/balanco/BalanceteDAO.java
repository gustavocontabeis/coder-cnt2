package br.com.cnt.model.dao.balanco;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.cnt.model.entity.balancete.dto.Balancete;
import br.com.cnt.model.entity.balancete.dto.SaldoContabil;
import br.com.cnt.model.entity.balanco.Empresa;
import br.com.cnt.model.entity.balanco.Exercicio;
import br.com.coder.arqprime.model.dao.app.DaoException;

@javax.inject.Named @javax.faces.view.ViewScoped
public class BalanceteDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject private LancamentoDAO daoLancamentos;
	@Inject private ExercicioDAO daoExercicio;
	
	public BalanceteDAO() {
	}
	
	public BalanceteDAO(LancamentoDAO daoLancamentos, ExercicioDAO daoExercicio) {
		super();
		this.daoLancamentos = daoLancamentos;
		this.daoExercicio = daoExercicio;
	}

	public Balancete buscarBalancete(Exercicio exercicio, Date de , Date ate) throws DaoException{
		
		exercicio = daoExercicio.buscar(exercicio.getId());
		Empresa empresa = exercicio.getEmpresa();
		exercicio.setEmpresa(empresa);
		
		Balancete balancete = new Balancete();
		balancete.setExercicio(exercicio);
		balancete.setDe(de);
		balancete.setAte(ate);
		
		List<SaldoContabil> saldosContabeis = daoLancamentos.buscarSaldosBalancete(exercicio, de, ate);
		balancete.setSaldos(saldosContabeis);
		
		return balancete;
	}

}
