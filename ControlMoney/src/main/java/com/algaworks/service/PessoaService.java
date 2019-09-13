package com.algaworks.service;

import com.algaworks.model.Pessoa;

import java.util.List;

public interface PessoaService {
	
	 List<Pessoa> findAll();

	 Pessoa findById(Long id);

	Pessoa save(Pessoa obj);

	 void saveAll(List<Pessoa> objs);

	 List<Pessoa> findByName(String nome);

}
