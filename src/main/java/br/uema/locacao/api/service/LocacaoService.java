package br.uema.locacao.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.uema.locacao.api.entity.Locacao;

public interface LocacaoService {

	Locacao create(Locacao locacao);

	Locacao update(Locacao locacao);

	Locacao findById(Long id);

	Page<Locacao> findAll(Pageable page);

	List<Locacao> findAll();
	
	List<Locacao> findAllEmAndamento();

	void delete(Long id);

	Page<Locacao> findByParameters(int page, int count, String professor, String datashow, String status,
			List<String> sort);

	List<Locacao> relatorioByPeriodo(String dataInicio, String dataFim);

	/*
	 * Page<Locacao> findByParameters(int page, int count, String identificacao,
	 * String numTombamento, String status, List<String> sort);
	 */
}
