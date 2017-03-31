/* ::::::::::::::::::::::::::::::::::::::::::: */
package br.com.cnt.model.entity.balanco;

import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.coder.arqprime.model.entity.BaseEntity;
import br.com.cnt.model.utils.ContaUtil;


/**
 * @author caixa
 *
 */
@Entity
@Table(name="CONTAS", 
	indexes={
		@Index(name="INDEX_CONTA_NOME", columnList = "NOME"),
		@Index(name="INDEX_CONTA_ESTRUTURA", columnList = "ESTRUTURA"),
	})
@NamedQueries(value={
				@NamedQuery(name="Conta-list", query="select obj from Conta obj "),
				@NamedQuery(name="Conta-porId", query="select obj from Conta obj "
														+ "left join fetch obj.empresa e "
														+ "left join fetch obj.planoContas pc "
														+ "left join fetch obj.pai pai "
														+ "where obj.id = :id ")
		}
)
public class Conta extends BaseEntity implements Comparable<Conta>{
	
	private static final long serialVersionUID = 1L;
 	
 	@Id 
 	@GeneratedValue(generator="SEQ_CONTA", strategy=GenerationType.SEQUENCE) 
	@SequenceGenerator(name="SEQ_CONTA", sequenceName="SEQ_CONTA", initialValue=1000) 
 	@Column(name="ID_CONTA", length=10,  nullable=false)
 	private Long id;
 
 	@Column(name="ESTRUTURA", length=20,  nullable=false)
 	private String estrutura;
 	
 	@Column(name="NIVEL", length=1,  nullable=false)
 	private Integer nivel;
 	
 	@Column(name="ORDEM", nullable=true)
 	private Integer ordem;

 	@Column(name="NOME", length=150,  nullable=false)
 	private String nome;
 
 	@Column(name="DESCRICAO", length=150,  nullable=true)
 	private String descricao;
 	
 	@Enumerated(EnumType.STRING)
 	@Column(name="CONTA_TIPO")
	private ContaTipo contaTipo;
 
 	@Enumerated(EnumType.STRING)
 	@Column(name="CONTA_ORIGEM")
	private ContaOrigem contaOrigem;
 	
 	@ManyToOne(targetEntity=Empresa.class, fetch=LAZY, cascade={CascadeType.DETACH}) 
 	@JoinColumn(name="ID_EMPRESA", nullable=true, foreignKey = @ForeignKey(name="FK_CONTA_EMPRESA"))
 	private Empresa empresa;
 
 	@ManyToOne(targetEntity=PlanoContas.class, fetch=FetchType.LAZY, cascade={CascadeType.DETACH}) 
 	@JoinColumn(name="ID_PLANO_CONTAS", nullable=true, foreignKey = @ForeignKey(name="FK_CONTA_PLANO_CONTAS"))
 	private PlanoContas planoContas;
 	
 	@ManyToOne(targetEntity=Conta.class, fetch=LAZY, cascade={CascadeType.DETACH}) 
 	@JoinColumn(name="ID_CONTA_PAI", nullable=true, foreignKey = @ForeignKey(name="FK_CONTA_PAI"))
 	private Conta pai;
 	
 	//@NotNull 
 	@OneToMany(targetEntity=Conta.class, fetch=LAZY, mappedBy="pai", cascade={CascadeType.ALL}) 
 	private List<Conta> subContas;
 	
	public Conta() {
		super();
	}

	public Conta(Long id) {
		super();
		this.id = id;
	}
	
	public Conta(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public Conta(Empresa empresa, Exercicio exercicio, PlanoContas planoContas) {
		super();
		this.empresa = empresa;
		this.planoContas = planoContas;
	}
	
	/**
	 * 
	 * @param id
	 * @param estrutura
	 * @param nivel
	 * @param nome
	 * @param descricao
	 * @param contaTipo
	 * @param contaOrigem
	 * @param empresa
	 * @param planoContas
	 */
 	public Conta(Long id, String estrutura, Integer nivel, String nome, String descricao, ContaTipo contaTipo,
			ContaOrigem contaOrigem, Empresa empresa, PlanoContas planoContas) {
		super();
		this.id = id;
		this.estrutura = estrutura;
		this.nivel = nivel;
		this.nome = nome;
		this.descricao = descricao;
		this.contaTipo = contaTipo;
		this.contaOrigem = contaOrigem;
		this.empresa = empresa;
		this.planoContas = planoContas;
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
	public String getDescricao(){
 		return this.descricao;
	}
	public void setDescricao(String descricao){
 		this.descricao = descricao;
	}
	public ContaTipo getContaTipo(){
 		return this.contaTipo;
	}
	public void setContaTipo(ContaTipo contaTipo){
 		this.contaTipo = contaTipo;
	}
	public Empresa getEmpresa(){
 		return this.empresa;
	}
	public void setEmpresa(Empresa empresa){
 		this.empresa = empresa;
	}
	public ContaOrigem getContaOrigem(){
 		return this.contaOrigem;
	}
	public void setContaOrigem(ContaOrigem contaOrigem){
 		this.contaOrigem = contaOrigem;
	}
	public PlanoContas getPlanoContas(){
 		return this.planoContas;
	}
	public void setPlanoContas(PlanoContas planoContas){
 		this.planoContas = planoContas;
	}
	public String getEstrutura() {
		return estrutura;
	}
	public void setEstrutura(String estrutura) {
		this.estrutura = estrutura;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	
	public Integer getNivel() {
		return nivel;
		//return ContaUtil.retornarNivel(this);
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	
	@Override
	public int compareTo(Conta conta) {
		
	    int i = estrutura.compareTo(conta.estrutura);
	    if (i != 0) return i;

	    i = conta.getContaTipo().compareTo(contaTipo);
	    if (i != 0) return i;

	    i = ordem.compareTo(conta.getOrdem()!=null?conta.getOrdem():0);
	    if (i != 0) return i;
		
		return nome.compareTo(conta.getNome());
	}
	
	public boolean isPai(Conta conta){
		return ContaUtil.isPai(this, conta);
	}
	
	public boolean isFilho(Conta conta){
		return ContaUtil.isFilho(this, conta);
	}

	@Override
	public String toString() {
		return "Conta [id=" + id 
				+ ", estrutura=" + estrutura 
				+ ", ordem=" + (ordem!=null?ordem:" ") 
				+ ", nivel=" + (nivel) 
				+ ", contaTipo=" + (contaTipo!=null?contaTipo:"") 
				+ ", nome=" + String.format("%-50s", nome) + "]";
	}

	public Conta getPai() {
		return pai;
	}

	public void setPai(Conta pai) {
		this.pai = pai;
	}

	public List<Conta> getSubContas() {
		return subContas;
	}

	public void setSubContas(List<Conta> subContas) {
		this.subContas = subContas;
	}
	
	

}