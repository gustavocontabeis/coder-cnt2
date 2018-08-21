/* ::::::::::::::::::::::::::::::::::::::::::: */
package br.com.cnt.model.entity.balanco;

import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.coder.arqprime.model.entity.BaseEntity;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="PlanoContas")
@Table(name="PLANO_CONTAS")
@NamedQueries(value={
		@NamedQuery(name="PlanoContas-list", query="select obj from PlanoContas obj "),
		@NamedQuery(name="PlanoContas-porId", query="select obj from PlanoContas obj where obj.id=:id")
	})
public class PlanoContas extends BaseEntity {
	
 	private static final long serialVersionUID = 1L;
 	
 	@Id 
 	@GeneratedValue(generator="SEQ_PLANO_CONTAS", strategy=GenerationType.SEQUENCE) 
	@SequenceGenerator(name="SEQ_PLANO_CONTAS", sequenceName="SEQ_PLANO_CONTAS", initialValue=100)  
 	@Column(name="ID_PLANO_CONTAS", length=10,  nullable=false)
 	private Long id;
 
 	@NotEmpty
 	@Column(name="NOME", length=150, nullable=false)
 	private String nome;
 	
	@NotNull 
 	@OneToMany(targetEntity=ContaModelo.class, fetch=LAZY, mappedBy="planoContas", cascade={CascadeType.ALL}) 
 	private List<ContaModelo> contas;
 	
 	public PlanoContas() {
		super();
	}
 	
 	public PlanoContas(Long id) {
		super();
		this.id = id;
	}

	public Long getId(){
 		return this.id;
	}
	public void setId(Long id){
 		this.id = id;
	}
	public String getNome(){
 		return this.nome;
	}
	public void setNome(String nome){
 		this.nome = nome;
	}
	
	public List<ContaModelo> getContas() {
		return contas;
	}

	public void setContas(List<ContaModelo> contas) {
		this.contas = contas;
	}

	@Override
	public String toString() {
		return "PlanoContas [id=" + id + ", nome=" + nome + "]";
	}
	
}
 