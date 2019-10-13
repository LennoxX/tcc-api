package br.uema.locacao.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.locacao.api.entity.Locacao;
import br.uema.locacao.api.enums.EnumStatusLocacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

	
	Page<Locacao> findAll(Pageable page);

	Page<Locacao> findByProfessorNomeContainingIgnoreCaseAndDatashowIdentificacaoContainingIgnoreCase(String professor,
			String datashow, Pageable pageable);

	Page<Locacao> findByProfessorNomeContainingIgnoreCaseAndDatashowIdentificacaoContainingIgnoreCaseAndStatus(
			String professor, String datashow, EnumStatusLocacao valueOf, Pageable pageable);

	List<Locacao> findByStatus(EnumStatusLocacao andamento);
}
