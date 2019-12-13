package br.uema.locacao.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.uema.locacao.api.entity.Datashow;

public interface DatashowService {

	Datashow create(Datashow datashow);

	Datashow update(Datashow datashow);

	Datashow findById(Long id);

	Page<Datashow> findAll(Pageable page);

	List<Datashow> findAll();
	
	List<Datashow> findAllDisponiveis();

	Page<Datashow> findByParameters(int page, int count, String identificacao, String numTombamento, String status, List<String> sort);

	/*
	 * Page<Datashow> findByParameters(int page, int size, String matricula, String
	 * nome, String curso, List<String> sort, String authorization);
	 */

}
