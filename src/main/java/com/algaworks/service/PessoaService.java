package com.algaworks.service;

import com.algaworks.dto.PessoaDto;
import com.algaworks.model.Pessoa;

import java.util.List;

public interface PessoaService {
	
	 List<Pessoa> findAll();

	 Pessoa findById(Long id);

	 Pessoa save(Pessoa obj);

	 void delete(Long id);

	 List<Pessoa> findByName(String nome);

	 PessoaDto update(Long id, PessoaDto dto);

	 PessoaDto update(Long id, Boolean ativo);
}
