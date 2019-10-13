package br.uema.locacao.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uema.locacao.api.entity.Usuario;
import br.uema.locacao.api.exception.CustomException;
import br.uema.locacao.api.repository.UsuarioRepository;
import br.uema.locacao.api.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public Usuario create(Usuario usuario) {
		try {
			if (usuario.getId() != null && usuarioRepository.existsById(usuario.getId()))
				throw new CustomException("ID de Usuário (" + usuario.getId() + ") já utilizado.",
						HttpStatus.BAD_REQUEST);
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			return usuarioRepository.save(usuario);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	@Transactional
	public Usuario update(Usuario usuario) {
		try {
			if (usuario.getId() == null)
				throw new CustomException("ID da Usuário não informado.", HttpStatus.BAD_REQUEST);
			if (!usuarioRepository.existsById(usuario.getId()))
				throw new CustomException("Usuario não encontrado.", HttpStatus.BAD_REQUEST);
			usuario.setPassword(usuarioRepository.findById(usuario.getId()).get().getPassword());
			return usuarioRepository.save(usuario);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Transactional
	public Usuario updatePassword(Usuario usuario, String novaSenha) {
		try {
			if (usuario.getId() == null)
				throw new CustomException("ID da Usuário não informado.", HttpStatus.BAD_REQUEST);
			if (!usuarioRepository.existsById(usuario.getId()))
				throw new CustomException("Usuario não encontrado.", HttpStatus.BAD_REQUEST);
			if(!passwordEncoder.matches(usuario.getPassword(), usuarioRepository.getOne(usuario.getId()).getPassword())){
				throw new CustomException("Senha atual informada é inválida", HttpStatus.BAD_REQUEST);
			}
			Usuario usuariotmp = usuarioRepository.getOne(usuario.getId());
			usuariotmp.setPassword(passwordEncoder.encode(novaSenha));
			return usuarioRepository.save(usuariotmp);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		try {
			Optional<Usuario> opt = usuarioRepository.findByUsername(username);
			if (opt.isPresent())
				return opt.get();
			else
				throw new CustomException("Usuario não encontrado", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		try {
			Optional<Usuario> opt = usuarioRepository.findById(id);
			if (opt.isPresent())
				return opt.get();
			else
				throw new CustomException("Usuario não encontrado", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findByNome(int page, int size, String nome) {
		try {
			Pageable pageable = PageRequest.of(page, size);
			return usuarioRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome, pageable);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<Usuario> findAll() {
		try {
			return usuarioRepository.findAll();
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public Page<Usuario> findAll(int page, int size) {
		try {
			Pageable pageable = PageRequest.of(page, size);
			return usuarioRepository.findAll(pageable);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public void delete(Long id) {
		try {
			usuarioRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
