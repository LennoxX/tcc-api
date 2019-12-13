package br.uema.locacao.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.uema.locacao.api.entity.Locacao;
import br.uema.locacao.api.entity.Professor;
import br.uema.locacao.api.enums.CursoEnum;
import br.uema.locacao.api.enums.EnumStatusLocacao;
import br.uema.locacao.api.exception.CustomException;
import br.uema.locacao.api.repository.LocacaoRepository;
import br.uema.locacao.api.repository.ProfessorRepository;
import br.uema.locacao.api.service.ProfessorService;

@Service
public class ProfessorServiceImpl implements ProfessorService {

	@Autowired
	private ProfessorRepository repository;

	@Autowired
	private LocacaoRepository locacaoRepository;

	@Override
	public Professor create(Professor professor) {

		if (repository.findByMatricula(professor.getMatricula()) != null) {
			throw new CustomException("Matrícula já cadastrada.", HttpStatus.BAD_REQUEST);
		}
		return repository.save(professor);

	}

	@Override
	public Professor update(Professor professor) {
		Professor profTemp = repository.findByMatricula(professor.getMatricula());
		if (profTemp != null && profTemp.getId() != professor.getId()) {
			throw new CustomException("Matrícula já cadastrada.", HttpStatus.BAD_REQUEST);
		}
		return repository.save(professor);

	}

	@Override
	public Professor findById(Long id) {
		try {
			return repository.getOne(id);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public Page<Professor> findAll(Pageable page) {
		try {
			return repository.findAll(page);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<Professor> findAllElegiveis() {
		try {
			List<Professor> professores = repository.findAll();
			List<Locacao> locacoes = locacaoRepository.findAll();
			for (Locacao l : locacoes) {
				if (l.getStatus() != EnumStatusLocacao.CONCLUIDA) {
					professores.remove(l.getProfessor());
				}
			}
			return professores;
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<Professor> findAll() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	public Page<Professor> findByParameters(int page, int size, String matricula, String nome, String curso,
			List<String> sort, String authorization) {

		try {
			List<Sort.Order> orders = new ArrayList<>();
			for (String order : sort) {
				String[] orderSplit = order.split("!");
				String property = orderSplit[0];
				if (orderSplit.length == 1) {
					orders.add(new Sort.Order(Direction.ASC, property));
				} else {
					Sort.Direction direction = Sort.Direction.fromString(orderSplit[1]);
					orders.add(new Sort.Order(direction, property));
				}
			}

			Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
			if (curso.isEmpty()) {
				return repository.findByNomeContainingIgnoreCaseAndMatriculaContainingIgnoreCase(nome, matricula,
						pageable);
			}

			return repository.findByNomeContainingIgnoreCaseAndMatriculaContainingIgnoreCaseAndCurso(nome, matricula,
					CursoEnum.valueOf(curso), pageable);

		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
