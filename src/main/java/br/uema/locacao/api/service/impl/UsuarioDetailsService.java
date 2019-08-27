package br.uema.locacao.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.uema.locacao.api.entity.PostgresUserDetails;
import br.uema.locacao.api.entity.Usuario;
import br.uema.locacao.api.enums.NivelEnum;
import br.uema.locacao.api.exception.CustomException;
import br.uema.locacao.api.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUsername(username).get();
		if (usuario == null || usuario.getNiveis() == null || usuario.getNiveis().isEmpty()) {
			throw new CustomException("Username ou password inválido.", HttpStatus.UNAUTHORIZED);
		}
		String[] authorities = new String[usuario.getNiveis().size()];
		int count = 0;
		for (NivelEnum nivel : usuario.getNiveis()) {
			// NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically
			// for us.
			// Since we are using custom token using JWT we should add ROLE_ prefix
			authorities[count] = "ROLE_" + nivel.name();
			count++;
		}
		PostgresUserDetails userDetails = new PostgresUserDetails(usuario.getUsername(), usuario.getPassword(),
				usuario.getAtivo(), usuario.isBloqueado(), usuario.isExpirado(), usuario.isHabilitado(),
				authorities);
		return userDetails;
	}

}
