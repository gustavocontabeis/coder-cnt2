/* ::::::::::::::::::::::::::::::::::::::::::: */
package br.com.cnt.model.entity.balanco;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ForeignKey;

import br.com.coder.arqprime.model.entity.BaseEntity;

@Entity
@Table(name="LANCAMENTO", 
	indexes={
		@Index(name="INDEX_LANCAMENTO_HISTORICO", columnList = "HISTORICO")
	})
@NamedQueries(value={
	@NamedQuery(name="Lancamento-buscar", query="select obj from Lancamento obj inner join fetch obj.exercicio ex inner join fetch obj.debito d inner join fetch obj.credito c where obj.id = :id"),
	@NamedQuery(name="Lancamento-list", query="select obj from Lancamento obj "),
	@NamedQuery(name="Lancamento-porId", query="select obj from Lancamento obj where obj.id=:id")
})
public class Lancamento extends BaseEntity {
	
 	private static final long serialVersionUID = 1L;
 	
 	@Id 
 	@GeneratedValue(generator="SEQ_LANCAMENTO", strategy=GenerationType.SEQUENCE) 
	@SequenceGenerator(name="SEQ_LANCAMENTO", sequenceName="SEQ_LANCAMENTO", initialValue=100) 
 	@Column(name="ID_LANCAMENTO", length=10,  nullable=false)
 	private Long id;
 	
 	@Temporal(TemporalType.DATE)
 	@Column(name="DATA", length=10,  nullable=false)
 	private Date date;
 
 	@ManyToOne(targetEntity=Exercicio.class, fetch=LAZY) 
 	@JoinColumn(name="EXERCICIO", nullable=false, foreignKey = @ForeignKey(name="FK_LANCAMENTO_EXERCICIO"))
 	private Exercicio exercicio;
 
 	@ManyToOne(targetEntity=Conta.class, fetch=LAZY) 
 	@JoinColumn(name="DEBITO", nullable=true, foreignKey = @ForeignKey(name="FK_LANCAMENTO_DEBITO"))
 	private Conta debito;
 
 	@ManyToOne(targetEntity=Conta.class, fetch=LAZY) 
 	@JoinColumn(name="CREDITO", nullable=true, foreignKey = @ForeignKey(name="FK_LANCAMENTO_CREDITO"))
 	private Conta credito;
 
 	@Column(name="VALOR", length=10, precision=2,  nullable=false)
 	private Float valor;
 
 	@Column(name="HISTORICO", length=600, nullable=false)
 	private String historico;
 
 	@ManyToOne(targetEntity=Lancamento.class, fetch=LAZY) 
 	@JoinColumn(name="LANCAMENTO_PRINCIPAL", nullable=true, foreignKey =@ForeignKey(name="FK_LANCAMENTO_PRINCIPAL"))
 	private Lancamento lancamentoPrincipal;
 
 	@Enumerated(EnumType.STRING)
	private LancamentoTipo lancamentoTipo;
 	
 	/**
 	 * 
 	 */
 	public Lancamento() {
		super();
	}
 	
 	/**
 	 * Lancamento(obj.id, obj.date, obj.exercicio.ano, obj.exercicio.empresa.razaoSocial, obj.debito.nome, obj.credito.nome, obj.valor)
 	 */
 	public Lancamento(Long id, Date date, Integer ano, String razaoSocial, String historico, Long debitoId, String debitoNome, Long creditoId, String creditoNome, Float valor) {
		super();
		this.id = id;
		this.date = date;
		Exercicio exercicio = new Exercicio();
		exercicio.setAno(ano);
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial(razaoSocial);
		exercicio.setEmpresa(empresa);
		this.historico = historico;
		this.exercicio = exercicio;
		this.debito = new Conta(debitoId, debitoNome);
		this.credito = new Conta(creditoId, creditoNome);
		this.valor = valor;
	}

	public Long getId(){
 		return this.id;
	}
	public Date getDate(){
 		return this.date;
	}
	public void setId(Long id){
 		this.id = id;
	}
	public Exercicio getExercicio(){
 		return this.exercicio;
	}
	public void setExercicio(Exercicio exercicio){
 		this.exercicio = exercicio;
	}
	public void setDate(Date date){
 		this.date = date;
	}
	public Conta getDebito(){
 		return this.debito;
	}
	public void setDebito(Conta debito){
 		this.debito = debito;
	}
	public Conta getCredito(){
 		return this.credito;
	}
	public void setCredito(Conta credito){
 		this.credito = credito;
	}
	public Float getValor(){
 		return this.valor;
	}
	public void setValor(Float valor){
 		this.valor = valor;
	}
	public Lancamento getLancamentoPrincipal(){
 		return this.lancamentoPrincipal;
	}
	public void setLancamentoPrincipal(Lancamento lancamentoPrincipal){
 		this.lancamentoPrincipal = lancamentoPrincipal;
	}
	public LancamentoTipo getLancamentoTipo(){
 		return this.lancamentoTipo;
	}
	public void setLancamentoTipo(LancamentoTipo lancamentoTipo){
 		this.lancamentoTipo = lancamentoTipo;
	}
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
}
 