package br.com.cnt.model.entity.balanco.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.cnt.model.entity.balanco.Exercicio;

public class Balancete implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Exercicio exercicio;
	private Date de, ate;
	private List<SaldoContabil>saldos;
	
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
	public List<SaldoContabil> getSaldos() {
		return saldos;
	}
	public void setSaldos(List<SaldoContabil> saldos) {
		this.saldos = saldos;
	}

}
