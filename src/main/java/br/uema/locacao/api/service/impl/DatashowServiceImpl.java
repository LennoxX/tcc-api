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

import br.uema.locacao.api.entity.Datashow;
import br.uema.locacao.api.enums.EnumStatusDatashow;
import br.uema.locacao.api.exception.CustomException;
import br.uema.locacao.api.repository.DatashowRepository;
import br.uema.locacao.api.service.DatashowService;

@Service
public class DatashowServiceImpl implements DatashowService {

	@Autowired
	private DatashowRepository repository;

	@Override
	public Datashow create(Datashow datashow) {
		if (repository.findByNumTombamento(datashow.getNumTombamento()) != null) {
			throw new CustomException("Número de Tombamento já cadastrado.", HttpStatus.BAD_REQUEST);
		}
		if (repository.findByIdentificacao(datashow.getIdentificacao()) != null) {
			throw new CustomException("Identificação já cadastrada.", HttpStatus.BAD_REQUEST);
		}
		return repository.save(datashow);

	}

	@Override
	public Datashow update(Datashow datashow) {
		try {
			return repository.save(datashow);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public Datashow findById(Long id) {
		try {
			return repository.getOne(id);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public Page<Datashow> findAll(Pageable page) {
		try {
			return repository.findAllByOrderByIdentificacao(page);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<Datashow> findAll() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<Datashow> findAllDisponiveis() {
		try {
			System.out.println(repository.findAllByStatus(EnumStatusDatashow.DISPONIVEL).size());
			return repository.findAllByStatus(EnumStatusDatashow.DISPONIVEL);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	public Page<Datashow> findByParameters(int page, int count, String identificacao, String numTombamento,
			String status, List<String> sort) {
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

			Pageable pageable = PageRequest.of(page, count, Sort.by(orders));

			if (status.isEmpty()) {
				return repository.findByIdentificacaoContainingIgnoreCaseAndNumTombamentoContainingIgnoreCase(
						identificacao, numTombamento, pageable);
			}

			return repository.findByIdentificacaoContainingIgnoreCaseAndNumTombamentoContainingIgnoreCaseAndStatus(
					identificacao, numTombamento, EnumStatusDatashow.valueOf(status), pageable);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
