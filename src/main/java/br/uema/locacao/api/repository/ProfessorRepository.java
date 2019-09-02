package br.uema.locacao.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.locacao.api.entity.Professor;
import br.uema.locacao.api.enums.CursoEnum;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    

	Page<Professor> findByNomeContainingIgnoreCaseAndMatriculaContainingIgnoreCaseAndCurso(String nome, String matricula, CursoEnum curso,
			Pageable pageable);
	
	Page<Professor> findByNomeContainingIgnoreCaseAndMatriculaContainingIgnoreCase(String nome, String matricula,
			Pageable pageable);

}
