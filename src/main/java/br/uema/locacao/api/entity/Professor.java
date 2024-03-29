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

import br.uema.locacao.api.enums.CursoEnum;

@Entity
@Table(name = "professor", schema = "locacao", uniqueConstraints = @UniqueConstraint(columnNames = "matricula"))
public class Professor {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_professor")
	@SequenceGenerator(name = "seq_professor", sequenceName = "seq_professor", schema = "locacao", allocationSize = 1)
	private Long id;
	
	@NotEmpty(message = "*Campo 'Nome', obrigatório.")
	@NotNull
	@NotBlank
	private String nome;
	
	@NotEmpty(message = "*Campo 'Matrícula', obrigatório.")
	@NotNull
	@NotBlank
	private String matricula;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Campo 'Curso' obrigatório!")
	private CursoEnum curso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public CursoEnum getCurso() {
		return curso;
	}

	public void setCurso(CursoEnum curso) {
		this.curso = curso;
	}
	
	
}
