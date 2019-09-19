package br.uema.locacao.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.locacao.api.entity.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

	
	Page<Locacao> findAll(Pageable page);
    


}
