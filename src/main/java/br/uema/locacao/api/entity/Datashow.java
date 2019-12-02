package br.uema.locacao.api.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.uema.locacao.api.enums.EnumStatusDatashow;

@Entity
@Table(name = "datashow", schema = "locacao", uniqueConstraints = @UniqueConstraint(columnNames = {"identificacao", "numTombamento"}))
public class Datashow {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_datashow")
	@SequenceGenerator(name = "seq_datashow", sequenceName = "seq_datashow", schema = "locacao", allocationSize = 1)
	private Long id;
	
	@NotEmpty(message = "*Campo 'Identificação' obrigatório")
	@NotNull
	@NotBlank
	private String identificacao;
	
	@NotEmpty(message = "*Campo 'Número de Tombamento' obrigatório")
	@NotNull
	@NotBlank
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
