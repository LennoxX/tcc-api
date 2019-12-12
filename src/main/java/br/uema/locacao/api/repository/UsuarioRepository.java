package br.uema.locacao.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.locacao.api.entity.Usuario;
import br.uema.locacao.api.enums.NivelEnum;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
    Optional<Usuario> findByUsername(String cpfCnpj);
    
    Page<Usuario> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome, Pageable pageable);

	Page<Usuario> findByNomeContainingIgnoreCaseAndEmailContainingIgnoreCaseAndAtivo(String nome, String email,
			boolean parseBoolean, Pageable pageable);

	Page<Usuario> findByNomeContainingIgnoreCaseAndEmailContainingIgnoreCaseAndNivel(String nome, String email,
			NivelEnum valueOf, Pageable pageable);

	Page<Usuario> findByNomeContainingIgnoreCaseAndEmailContainingIgnoreCase(String nome, String email,
			Pageable pageable);

	Page<Usuario> findByNomeContainingIgnoreCaseAndEmailContainingIgnoreCaseAndNivelAndAtivo(String nome,
			String email, NivelEnum valueOf, boolean parseBoolean, Pageable pageable);

}
