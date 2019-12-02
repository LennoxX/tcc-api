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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.uema.locacao.api.enums.CursoEnum;
import br.uema.locacao.api.enums.NivelEnum;

@Entity
@Table(name = "usuario", schema = "seguranca", uniqueConstraints = @UniqueConstraint(columnNames = { "username",
		"email" }))
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", schema = "seguranca", allocationSize = 1)
	private Long id;

	@NotEmpty(message = "*Campo 'Nome', obrigatório.")
	@NotBlank
	@NotNull
	private String nome;

	@NotEmpty(message = "*Campo 'Username', obrigatório.")
	@NotBlank
	@NotNull
	private String username;

	@NotEmpty(message = "*Campo 'Password', obrigatório.")
	@NotBlank
	@NotNull
	private String password;

	private boolean ativo;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Campo 'Nível' obrigatório!")
	private NivelEnum nivel;

	@Email
	@NotEmpty(message = "*Campo 'Email', obrigatório.")
	@NotNull
	@NotBlank
	private String email;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Campo 'Curso' obrigatório!")
	private CursoEnum curso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public NivelEnum getNivel() {
		return nivel;
	}

	public void setNivel(NivelEnum nivel) {
		this.nivel = nivel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CursoEnum getCurso() {
		return curso;
	}

	public void setCurso(CursoEnum curso) {
		this.curso = curso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", username=" + username + ", password=" + password + ", ativo="
				+ ativo + ", nivel=" + nivel + ", email=" + email + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
