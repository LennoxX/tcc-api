package br.uema.locacao.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.uema.locacao.api.entity.Usuario;

public interface UsuarioService {
	
    Usuario create(Usuario usuario);
    
    Usuario update(Usuario usuario);
    
    Usuario updatePassword(Usuario usuario, String novaSenha);
    
    Usuario findById(Long id);
    
    Usuario findByUsername(String username);
    
    Page<Usuario> findByNome(int page, int count, String nome);
    
    Page<Usuario> findAll(int page, int size);
    
    List<Usuario> findAll();
    
    void delete(Long id);

}
