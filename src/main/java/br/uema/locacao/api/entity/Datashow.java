package br.uema.locacao.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.uema.locacao.api.enums.EnumStatusDatashow;

@Entity
@Table(name = "datashow", schema = "locacao")
public class Datashow {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_datashow")
	@SequenceGenerator(name = "seq_datashow", sequenceName = "seq_datashow", schema = "locacao", allocationSize = 1)
	private Long id;
	
	@Column(unique = true)
	private String identificacao;
	
	@Column(unique = true)
	private String numTombamento;
	
	private boolean possuiHdmi;
	
	private boolean possuiVga;
	
	@Enumerated(EnumType.STRING)
	private EnumStatusDatashow status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getNumTombamento() {
		return numTombamento;
	}

	public void setNumTombamento(String numTombamento) {
		this.numTombamento = numTombamento;
	}

	public boolean isPossuiHdmi() {
		return possuiHdmi;
	}

	public void setPossuiHdmi(boolean possuiHdmi) {
		this.possuiHdmi = possuiHdmi;
	}

	public boolean isPossuiVga() {
		return possuiVga;
	}

	public void setPossuiVga(boolean possuiVga) {
		this.possuiVga = possuiVga;
	}

	public EnumStatusDatashow getStatus() {
		return status;
	}

	public void setStatus(EnumStatusDatashow status) {
		this.status = status;
	}
	
	
	
	
}
