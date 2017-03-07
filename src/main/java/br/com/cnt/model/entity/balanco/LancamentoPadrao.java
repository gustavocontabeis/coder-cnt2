/* ::::::::::::::::::::::::::::::::::::::::::: */
package br.com.cnt.model.entity.balanco;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.coder.arqprime.model.entity.BaseEntity;

@Entity
@Table(name="LANCAMENTO_PADRAO", 
	indexes={
			@Index(name="INDEX_LANCAMENTO_PADRAO_NOME", columnList = "NOME")
	})
@NamedQueries(value={
		@NamedQuery(name="LancamentoPadrao-buscar", query=
				"select obj from LancamentoPadrao obj "
				+ "inner join fetch obj.debito deb "
				+ "inner join fetch obj.credito cred "
				+ "inner join fetch obj.historicoPadrao hp "
				+ "where obj.id = :id"),
		@NamedQuery(name="LancamentoPadrao-buscarPorNome", query="select obj from LancamentoPadrao obj where upper(obj.nome) like upper(:nome)"),
})
public class LancamentoPadrao extends BaseEntity {
	
 	private static final long serialVersionUID = 1L;
 	
 	@Id 
 	@GeneratedValue(generator="SEQ_LANCAMENTO_PADRAO", strategy=GenerationType.SEQUENCE) 
	@SequenceGenerator(name="SEQ_LANCAMENTO_PADRAO", sequenceName="SEQ_LANCAMENTO_PADRAO", initialValue=100) 
 	@Column(name="ID_LANCAMENTO_PADRAO", length=10,  nullable=false)
 	private Long id;
 	
 	@NotEmpty 
 	@Column(name="NOME", nullable=false, length=60)
 	private String nome;
 
 	@ManyToOne(fetch=LAZY, cascade={CascadeType.DETACH}) 
 	@JoinColumn(name="DEBITO", nullable=true, foreignKey = @ForeignKey(name="FK_LANCAMENTO_PADRAO_DEBITO"))
 	private Conta debito;
 
 	@ManyToOne(fetch=LAZY, cascade={CascadeType.DETACH}) 
 	@JoinColumn(name="CREDITO", nullable=true, foreignKey = @ForeignKey(name="FK_LANCAMENTO_PADRAO_CREDITO"))
 	private Conta credito;
 
 	@ManyToOne(fetch=LAZY, cascade={CascadeType.DETACH}) 
 	@JoinColumn(name="ID_HISTORICO_PADRAO", nullable=true, foreignKey = @ForeignKey(name="FK_LANCAMENTO_PADRAO_HISTORICO_PADRAO"))
 	private HistoricoPadrao historicoPadrao;
 
	public Long getId(){
 		return this.id;
	}
	public void setId(Long id){
 		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Conta getDebito() {
		return debito;
	}
	public void setDebito(Conta debito) {
		this.debito = debito;
	}
	public Conta getCredito() {
		return credito;
	}
	public void setCredito(Conta credito) {
		this.credito = credito;
	}
	public HistoricoPadrao getHistoricoPadrao() {
		return historicoPadrao;
	}
	public void setHistoricoPadrao(HistoricoPadrao historicoPadrao) {
		this.historicoPadrao = historicoPadrao;
	}
	
}
 