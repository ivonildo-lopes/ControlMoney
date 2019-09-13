package com.algaworks.service;

import java.util.List;

import com.algaworks.model.Categoria;

public interface CategoriaService {
	
	 List<Categoria> findAll();

	 Categoria findById(Long id);

	 Categoria save(Categoria obj);

	 void saveAll(List<Categoria> objs);

	 List<Categoria> findByName(String nome);

}
