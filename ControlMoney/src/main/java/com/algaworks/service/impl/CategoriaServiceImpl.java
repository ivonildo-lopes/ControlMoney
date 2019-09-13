package com.algaworks.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.algaworks.error.BadValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.dao.CategoriaDao;
import com.algaworks.model.Categoria;
import com.algaworks.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaDao dao;
	
	@Override
	public List<Categoria> findAll() {
		return dao.findAll();
	}

	@Override
	public Categoria findById(Long id) {
		if(Objects.isNull(id)) {
			throw new BadValueException("Por favor informe o ID da categoria");
		}
		return this.dao.findOne(id);
	}

	@Override
	public Categoria save(Categoria obj) {
		if(Objects.isNull(obj)) {
			throw new BadValueException("Por favor informe uma categoria");
		}

		if(!this.findByName(obj.getNome()).isEmpty()){
			return null;
		}
		return this.dao.save(obj);
	}

	@Override
	public void saveAll(List<Categoria> objs) {
		List<Categoria> categorias = new ArrayList<>();

		if(Objects.isNull(objs)) {
			throw new BadValueException("Por favor informe as categorias");
		}

		objs.stream().forEach(categoria -> {
			this.save(categoria);
		});
	}

	@Override
	public List<Categoria> findByName(String nome) {

		if(Objects.isNull(nome) || nome.isEmpty()) {
			throw new BadValueException("Por favor informe um nome da categoria");
		}
		return this.dao.findByName(nome);
	}


}
