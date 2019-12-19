package br.uema.locacao.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import br.uema.locacao.api.enums.EnumStatusLocacao;
import br.uema.locacao.api.exception.CustomException;
import br.uema.locacao.api.repository.LocacaoRepository;
import br.uema.locacao.api.service.LocacaoService;

@Service
public class LocacaoServiceImpl implements LocacaoService {

	@Autowired
	private LocacaoRepository repository;

	@Override
	public Locacao create(Locacao datashow) {
		try {
			return repository.save(datashow);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public Locacao update(Locacao datashow) {
		try {
			return repository.save(datashow);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public Locacao findById(Long id) {
		try {
			return repository.getOne(id);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public Page<Locacao> findAll(Pageable page) {

		try {
			return repository.findAll(page);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<Locacao> findAll() {

		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public Page<Locacao> findByParameters(int page, int count, String professor, String datashow, String status,
			List<String> sort) {
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
				return repository.findByProfessorNomeContainingIgnoreCaseAndDatashowIdentificacaoContainingIgnoreCase(
						professor, datashow, pageable);
			}

			return repository
					.findByProfessorNomeContainingIgnoreCaseAndDatashowIdentificacaoContainingIgnoreCaseAndStatus(
							professor, datashow, EnumStatusLocacao.valueOf(status), pageable);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<Locacao> findAllEmAndamento() {
		try {
			return repository.findAllByStatus(EnumStatusLocacao.ANDAMENTO);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<Locacao> relatorioByPeriodo(String dataInicio, String dataFim) {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			return repository.findAllByDataInicioGreaterThanEqualAndDataInicioLessThanEqualAndStatus(sdf.parse(dataInicio), sdf2.parse(dataFim), EnumStatusLocacao.CONCLUIDA);
		} catch (ParseException e) {
			
		}
		return null;
	}

}
