package br.uema.locacao.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.uema.locacao.api.entity.Professor;
import br.uema.locacao.api.enums.CursoEnum;
import br.uema.locacao.api.repository.ProfessorRepository;
import br.uema.locacao.api.service.ProfessorService;

@Service
public class ProfessorServiceImpl implements ProfessorService {

	@Autowired
	private ProfessorRepository repository;

	@Override
	public Professor create(Professor professor) {
		return repository.save(professor);
	}

	@Override
	public Professor update(Professor professor) {
		return repository.save(professor);
	}

	@Override
	public Professor findById(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Page<Professor> findAll(Pageable page) {
		return repository.findAll(page);
	}

	@Override
	public List<Professor> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<Professor> findByParameters(int page, int size, String matricula, String nome, String curso,
			List<String> sort, String authorization) {
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
			return repository.findByNomeContainingIgnoreCaseAndMatriculaContainingIgnoreCase(nome, matricula, pageable);
		}

		return repository.findByNomeContainingIgnoreCaseAndMatriculaContainingIgnoreCaseAndCurso(nome, matricula,
				CursoEnum.valueOf(curso), pageable);
	}

}
