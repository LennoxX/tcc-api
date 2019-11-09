package br.uema.locacao.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.uema.locacao.api.entity.Usuario;
import br.uema.locacao.api.enums.NivelEnum;
import br.uema.locacao.api.repository.UsuarioRepository;

@SpringBootApplication
public class LocacaoApiApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LocacaoApiApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LocacaoApiApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			iniciarUsuario(usuarioRepository, passwordEncoder);
		};
	}

	public void iniciarUsuario(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
		List<Usuario> usuarios = repository.findAll();

		if (usuarios.isEmpty()) {
			List<NivelEnum> niveis = new ArrayList<>();
			niveis.add(NivelEnum.ADMIN);

			Usuario usuario = new Usuario();
			usuario.setNome("Administrador");
			usuario.setUsername("admin"); 
			usuario.setPassword(passwordEncoder.encode("@dm1n1str@dor"));
			usuario.setEmail("email@mail.com");
			usuario.setAtivo(true);
			usuario.setNivel(NivelEnum.ADMIN);

			repository.save(usuario);
		}
	}
}
