package br.uema.locacao.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.locacao.api.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
    Optional<Usuario> findByUsername(String cpfCnpj);
    
    Page<Usuario> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome, Pageable pageable);

}
