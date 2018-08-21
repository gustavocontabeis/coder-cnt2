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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.coder.arqprime.model.entity.BaseEntity;

/**
 * @author caixa
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.NONE, region="ContaModelo")
@Table(name = "CONTA_MODELO", indexes = { 
		@Index(name = "INDEX_CONTA_MODELO_NOME", columnList = "NOME"),
		@Index(name = "INDEX_CONTA_MODELO_ESTRUTURA", columnList = "ESTRUTURA"), }
)
@NamedQueries(value = { 
		@NamedQuery(name = "ContaModelo-list", query = "select obj from ContaModelo obj "),
		@NamedQuery(name = "ContaModelo-porId", query = 
		"select obj from Conta obj " 
		+ "left join fetch obj.pai pai " 
		+ "where obj.id = :id ") })
public class ContaModelo extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SEQ_CONTA_MODELO", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SEQ_CONTA_MODELO", sequenceName = "SEQ_CONTA_MODELO", initialValue = 1000)
	@Column(name = "ID_CONTA_MODELO", length = 10, nullable = false)
	private Long id;

	@Column(name = "ESTRUTURA", length = 20, nullable = false)
	private String estrutura;

	@Column(name = "NIVEL", length = 1, nullable = false)
	private Integer nivel;

	@Column(name = "ORDEM", nullable = true)
	private Integer ordem;

	@Column(name = "NOME", length = 150, nullable = false)
	private String nome;

	@Column(name = "DESCRICAO", length = 150, nullable = true)
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "CONTA_TIPO")
	private ContaTipo contaTipo;

	@Enumerated(EnumType.STRING)
	@Column(name = "CONTA_ORIGEM")
	private ContaOrigem contaOrigem;

	@ManyToOne(targetEntity = PlanoContas.class, fetch = FetchType.EAGER, cascade = { CascadeType.DETACH })
	@JoinColumn(name = "ID_PLANO_CONTAS", nullable = true, foreignKey = @ForeignKey(name = "FK_CONTA_PLANO_CONTAS"))
	private PlanoContas planoContas;
	
	@ManyToOne(targetEntity = ContaModelo.class, fetch = LAZY, cascade = { CascadeType.DETACH })
	@JoinColumn(name = "ID_CONTA_MODELO_PAI", nullable = true, foreignKey = @ForeignKey(name = "FK_CONTA_MODELO_PAI"))
	private ContaModelo pai;

	@OneToMany(targetEntity = Conta.class, fetch = FetchType.LAZY, mappedBy = "pai", cascade = { CascadeType.ALL })
	private List<ContaModelo> subContas;

	public ContaModelo() {
		super();
	}

	public ContaModelo(Long id) {
		super();
		this.id = id;
	}

	public ContaModelo(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public ContaModelo(PlanoContas planoContas) {
		super();
		this.planoContas = planoContas;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ContaTipo getContaTipo() {
		return this.contaTipo;
	}

	public void setContaTipo(ContaTipo contaTipo) {
		this.contaTipo = contaTipo;
	}

	public ContaOrigem getContaOrigem() {
		return this.contaOrigem;
	}

	public void setContaOrigem(ContaOrigem contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public PlanoContas getPlanoContas() {
		return this.planoContas;
	}

	public void setPlanoContas(PlanoContas planoContas) {
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
		// return ContaUtil.retornarNivel(this);
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public ContaModelo getPai() {
		return pai;
	}

	public void setPai(ContaModelo pai) {
		this.pai = pai;
	}

	public List<ContaModelo> getSubContas() {
		return subContas;
	}

	public void setSubContas(List<ContaModelo> subContas) {
		this.subContas = subContas;
	}

}