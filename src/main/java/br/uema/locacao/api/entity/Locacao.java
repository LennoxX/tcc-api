package br.uema.locacao.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.uema.locacao.api.enums.EnumStatusLocacao;

@Entity
@Table(name = "locacao", schema = "locacao")
public class Locacao {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_locacao")
	@SequenceGenerator(name = "seq_locacao", sequenceName = "seq_locacao", schema = "locacao", allocationSize = 1)
	private Long id;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	@JoinColumn(name = "datashow_id")
	@ManyToOne
	private Datashow datashow;
	
	@JoinColumn(name = "professor_id")
	@ManyToOne
	private Professor professor;
	
	@Enumerated(EnumType.STRING)
	private EnumStatusLocacao status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Datashow getDatashow() {
		return datashow;
	}

	public void setDatashow(Datashow datashow) {
		this.datashow = datashow;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public EnumStatusLocacao getStatus() {
		return status;
	}

	public void setStatus(EnumStatusLocacao status) {
		this.status = status;
	}
	
	
	
}
