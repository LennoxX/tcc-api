package br.uema.locacao.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.uema.locacao.api.entity.Professor;

public interface ProfessorService {

	Professor create(Professor professor);

	Professor update(Professor professor);

	Professor findById(Long id);

	Page<Professor> findAll(Pageable page);

	List<Professor> findAll();
	
	List<Professor> findAllElegiveis();

	void delete(Long id);

	Page<Professor> findByParameters(int page, int size, String matricula, String nome, String curso, List<String> sort,
			String authorization);

}
