package br.com.cnt.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity @Table(name="imagem")
public class Imagem extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(generator="seq_arquivo", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="seq_arquivo", sequenceName="seq_arquivo", allocationSize=1000, initialValue=1000)
	@Column(name="id_arquivo")
	private Long id;
	
	//@Lob
	//@Column(name="dados", nullable=false, columnDefinition="LONGBLOB")//MySQL
	@Column(name="dados", nullable=false,columnDefinition="BLOB")//HSQLDB
	private byte[] data;
	
	@Basic
	private Long tamanho;
	
	@Basic
	private String nome;
	
	@Basic
	private String extencao;
	
	private Integer width;
	
	private Integer height;
	
	@Basic
	private String contentType;

	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getExtencao() {
		return extencao;
	}

	public void setExtencao(String extencao) {
		this.extencao = extencao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
