package com.algaworks.service.impl;

import com.algaworks.Util.Converter;
import com.algaworks.dao.UsuarioDao;
import com.algaworks.error.BadValueException;
import com.algaworks.error.NoContentException;
import com.algaworks.model.Usuario;
import com.algaworks.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao dao;
	
	@Override
	public List<Usuario> findAll() {

		List<Usuario> usuarios = this.dao.findAll();

		if(Objects.isNull(usuarios) || usuarios.isEmpty()) { throw new NoContentException("Nenhuma Usuario Encontrado!"); }

		return usuarios;
	}

	@Override
	public Usuario findById(Long id) {
		if(Objects.isNull(id)) {
			throw new BadValueException("Por favor informe o ID da usuario");
		}

		Usuario usuario = this.dao.findOne(id);

		if(Objects.isNull(usuario)) {
			throw new BadValueException("Essa usuario não existe");
		}
		return usuario;
	}

	@Override
	public Usuario save(Usuario obj) {
		if(Objects.isNull(obj)) { throw new BadValueException("Por favor informe uma usuario"); }

		return this.dao.save(obj);
	}

	@Override
	public void delete(Long id) {
		if(Objects.isNull(id)) { throw new BadValueException("Por favor informe o id da usuario"); }

		Usuario usuario = this.dao.findOne(id);
		this.dao.delete(usuario);
	}

	@Override
	public Usuario update(Long id, Usuario dto) {
		Usuario usuario = this.findById(id);

		if(Objects.isNull(usuario)) { throw new EmptyResultDataAccessException(1); }

		Converter.converteDtoToModel(dto,usuario,"id");
		this.save(usuario);

		return (Usuario) Converter.converteModelToDto(usuario,dto);
	}

	@Override
	public List<Usuario> findByName(String nome) {

		if(Objects.isNull(nome) || nome.isEmpty()) { throw new BadValueException("Por favor informe um nome da usuario"); }
		return this.dao.findByName(nome);
	}

	@Override
	public Usuario findByEmail(String email) {

		if(Objects.isNull(email) || email.isEmpty()) { throw new BadValueException("Por favor informe um nome ou email do usuario"); }

		Usuario usuario = this.dao.findByEmail(email).orElse(null);

		if(Objects.isNull(usuario)) {
			throw new BadValueException("Usuario ou senha invalidos");
		}

		return usuario;
	}

}
