package br.uema.locacao.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import br.uema.locacao.api.entity.Datashow;
import br.uema.locacao.api.enums.EnumStatusDatashow;
import br.uema.locacao.api.repository.DatashowRepository;
import br.uema.locacao.api.service.DatashowService;

@Service
public class DatashowServiceImpl implements DatashowService {

	@Autowired
	private DatashowRepository repository;

	@Override
	public Datashow create(Datashow datashow) {
		return repository.save(datashow);
	}

	@Override
	public Datashow update(Datashow datashow) {
		return repository.save(datashow);
	}

	@Override
	public Datashow findById(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Page<Datashow> findAll(Pageable page) {
		return repository.findAllByOrderByIdentificacao(page);
	}

	@Override
	public List<Datashow> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<Datashow> findByParameters(int page, int count, String identificacao, String numTombamento,
			String status, List<String> sort) {
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
			return repository.findByIdentificacaoContainingIgnoreCaseAndNumTombamentoContainingIgnoreCase(identificacao,
					numTombamento, pageable);
		}

		return repository.findByIdentificacaoContainingIgnoreCaseAndNumTombamentoContainingIgnoreCaseAndStatus(identificacao,
				numTombamento, EnumStatusDatashow.valueOf(status), pageable);
	}

}
