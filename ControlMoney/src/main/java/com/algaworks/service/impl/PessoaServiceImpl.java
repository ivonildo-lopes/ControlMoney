package com.algaworks.service.impl;

import com.algaworks.Util.Converter;
import com.algaworks.dao.PessoaDao;
import com.algaworks.dto.EnderecoDto;
import com.algaworks.dto.PessoaDto;
import com.algaworks.error.BadValueException;
import com.algaworks.error.NoContentException;
import com.algaworks.model.Pessoa;
import com.algaworks.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaDao dao;
	
	@Override
	public List<Pessoa> findAll() {

		List<Pessoa> pessoas = this.dao.findAll();

		if(Objects.isNull(pessoas) || pessoas.isEmpty()) { throw new NoContentException("Nenhuma Pessoa Encontrada!"); }

		return pessoas;
	}

	@Override
	public Pessoa findById(Long id) {
		if(Objects.isNull(id)) {
			throw new BadValueException("Por favor informe o ID da pessoa");
		}

		Pessoa pessoa = this.dao.findOne(id);

		if(Objects.isNull(pessoa)) {
			throw new BadValueException("Essa pessoa não existe");
		}
		return pessoa;
	}

	@Override
	public Pessoa save(Pessoa obj) {
		if(Objects.isNull(obj)) { throw new BadValueException("Por favor informe uma pessoa"); }

		return this.dao.save(obj);
	}

	@Override
	public void delete(Long id) {
		if(Objects.isNull(id)) { throw new BadValueException("Por favor informe o id da pessoa"); }

		Pessoa pessoa = this.dao.findOne(id);
		this.dao.delete(pessoa);
	}

	@Override
	public List<Pessoa> findByName(String nome) {

		if(Objects.isNull(nome) || nome.isEmpty()) { throw new BadValueException("Por favor informe um nome da pessoa"); }
		return this.dao.findByName(nome);
	}

	@Override
	public PessoaDto update(Long id, PessoaDto dto) {
		Pessoa pessoa = this.findById(id);

		if(Objects.isNull(pessoa)) { throw new EmptyResultDataAccessException(1); }

		Converter.converteDtoToModel(dto,pessoa,"id");
		this.save(pessoa);

		return (PessoaDto) Converter.converteModelToDto(pessoa,dto);
	}

	@Override
	public PessoaDto update(Long id, Boolean ativo) {
		EnderecoDto enderecoDto = new EnderecoDto();
		PessoaDto dto = new PessoaDto();

		Pessoa pessoa = this.findById(id);

		if(Objects.isNull(pessoa)) { throw new EmptyResultDataAccessException(1); }

		pessoa.setAtivo(ativo);

		this.save(pessoa);

		enderecoDto = (EnderecoDto) Converter.converteModelToDto(pessoa.getEndereco(), enderecoDto);

		dto = (PessoaDto) Converter.converteModelToDto(pessoa,dto);
		dto.setEndereco(enderecoDto);

		return dto;
	}
}
