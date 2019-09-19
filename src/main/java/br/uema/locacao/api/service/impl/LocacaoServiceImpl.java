package br.uema.locacao.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.uema.locacao.api.entity.Locacao;
import br.uema.locacao.api.repository.LocacaoRepository;
import br.uema.locacao.api.service.LocacaoService;

@Service
public class LocacaoServiceImpl implements LocacaoService {

	@Autowired
	private LocacaoRepository repository;

	@Override
	public Locacao create(Locacao datashow) {
		return repository.save(datashow);
	}

	@Override
	public Locacao update(Locacao datashow) {
		return repository.save(datashow);
	}

	@Override
	public Locacao findById(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Page<Locacao> findAll(Pageable page) {
		return repository.findAll(page);
	}

	@Override
	public List<Locacao> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	/*
	 * public Page<Locacao> findByParameters(int page, int count, String
	 * identificacao, String numTombamento, String status, List<String> sort) {
	 * List<Sort.Order> orders = new ArrayList<>(); for (String order : sort) {
	 * String[] orderSplit = order.split("!"); String property = orderSplit[0];
	 * 
	 * if (orderSplit.length == 1) { orders.add(new Sort.Order(Direction.ASC,
	 * property)); } else { Sort.Direction direction =
	 * Sort.Direction.fromString(orderSplit[1]); orders.add(new
	 * Sort.Order(direction, property)); } }
	 * 
	 * Pageable pageable = PageRequest.of(page, count, Sort.by(orders));
	 * 
	 * if (status.isEmpty()) { return repository.
	 * findByIdentificacaoContainingIgnoreCaseAndNumTombamentoContainingIgnoreCase(
	 * identificacao, numTombamento, pageable); }
	 * 
	 * return repository.
	 * findByIdentificacaoContainingIgnoreCaseAndNumTombamentoContainingIgnoreCaseAndStatus
	 * (identificacao, numTombamento, EnumStatusLocacao.valueOf(status), pageable);
	 * }
	 */

}
