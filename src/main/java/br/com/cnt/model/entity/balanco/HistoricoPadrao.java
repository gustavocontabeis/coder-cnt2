/* ::::::::::::::::::::::::::::::::::::::::::: */
package br.com.cnt.model.entity.balanco;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.coder.arqprime.model.entity.BaseEntity;

@Entity
@Table(name="HISTORICO_PADRAO", 
	indexes={
		@Index(name="INDEX_HISTORICO_PADRAO", columnList = "HISTORICO")
	})
@NamedQueries(value={
		@NamedQuery(name="HistoricoPadrao-buscar", query="select obj from HistoricoPadrao obj where obj.id = :id"),
		@NamedQuery(name="HistoricoPadrao-buscarPorHistorico", query="select obj from HistoricoPadrao obj where upper(obj.historico) like upper(:historico)"),
})
public class HistoricoPadrao extends BaseEntity {
	
 	private static final long serialVersionUID = 1L;
 	
 	@Id 
 	@GeneratedValue(generator="SEQ_HISTORICO_PADRAO", strategy=GenerationType.SEQUENCE) 
	@SequenceGenerator(name="SEQ_HISTORICO_PADRAO", sequenceName="SEQ_HISTORICO_PADRAO", initialValue=100) 
 	@Column(name="ID_HISTORICO_PADRAO", length=10,  nullable=false)
 	private Long id;
 	
 	@Column(name="HISTORICO", length=600, nullable=false)
 	private String historico;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}
	
}
 