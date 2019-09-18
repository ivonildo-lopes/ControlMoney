package com.algaworks.service;

import com.algaworks.dto.PessoaDto;
import com.algaworks.model.Pessoa;
import com.algaworks.model.Usuario;

import java.util.List;

public interface UsuarioService {
	
	 List<Usuario> findAll();

	 Usuario findById(Long id);

	 Usuario save(Usuario obj);

	 void delete(Long id);

	 List<Usuario> findByName(String nome);

	 Usuario findByEmail(String email);

	 Usuario update(Long id, Usuario usuario);

}
