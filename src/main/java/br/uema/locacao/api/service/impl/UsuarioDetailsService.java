package br.uema.locacao.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.uema.locacao.api.entity.PostgresUserDetails;
import br.uema.locacao.api.entity.Usuario;
import br.uema.locacao.api.exception.CustomException;
import br.uema.locacao.api.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUsername(username).get();
		if (usuario == null || usuario.getNivel() == null) {
			throw new CustomException("Usuário ou senha inválidos.", HttpStatus.UNAUTHORIZED);
		}

		PostgresUserDetails userDetails = new PostgresUserDetails(usuario.getUsername(), usuario.getPassword(),
				usuario.getAtivo(), false, false, true, usuario.getNivel().name());
		return userDetails;
	}

}
