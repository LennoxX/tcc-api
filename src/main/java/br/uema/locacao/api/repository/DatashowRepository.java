package br.uema.locacao.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.locacao.api.entity.Datashow;
import br.uema.locacao.api.enums.EnumStatusDatashow;

public interface DatashowRepository extends JpaRepository<Datashow, Long> {

	Page<Datashow> findByIdentificacaoContainingIgnoreCaseAndNumTombamentoContainingIgnoreCase(String identificacao,
			String numTombamento, Pageable pageable);

	Page<Datashow> findByIdentificacaoContainingIgnoreCaseAndNumTombamentoContainingIgnoreCaseAndStatus(
			String identificacao, String numTombamento, EnumStatusDatashow valueOf, Pageable pageable);

	Page<Datashow> findAllByOrderByIdentificacao(Pageable page);
    
	List<Datashow> findAllByStatus(EnumStatusDatashow status);

	Datashow findByNumTombamento(String numTombamento);

	Datashow findByIdentificacao(String identificacao);

	/*
	 * Page<Professor>
	 * findByNomeContainingIgnoreCaseAndMatriculaContainingIgnoreCaseAndCurso(String
	 * nome, String matricula, CursoEnum curso, Pageable pageable);
	 * 
	 * Page<Professor>
	 * findByNomeContainingIgnoreCaseAndMatriculaContainingIgnoreCase(String nome,
	 * String matricula, Pageable pageable);
	 */
}
