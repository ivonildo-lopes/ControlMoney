package com.algaworks.service.impl;

import com.algaworks.dao.PessoaDao;
import com.algaworks.error.BadValueException;
import com.algaworks.model.Pessoa;
import com.algaworks.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaDao dao;
	
	@Override
	public List<Pessoa> findAll() {
		return dao.findAll();
	}

	@Override
	public Pessoa findById(Long id) {
		if(Objects.isNull(id)) {
			throw new BadValueException("Por favor informe o ID da pessoa");
		}
		return this.dao.findOne(id);
	}

	@Override
	public Pessoa save(Pessoa obj) {
		if(Objects.isNull(obj)) {
			throw new BadValueException("Por favor informe uma pessoa");
		}

		if(!this.findByName(obj.getNome()).isEmpty()){
			return null;
		}
		return this.dao.save(obj);
	}

	@Override
	public void saveAll(List<Pessoa> objs) {

		if(Objects.isNull(objs)) {
			throw new BadValueException("Por favor informe as pessoas");
		}

		objs.stream().forEach(pessoa -> {
			this.save(pessoa);
		});
	}

	@Override
	public List<Pessoa> findByName(String nome) {

		if(Objects.isNull(nome) || nome.isEmpty()) {
			throw new BadValueException("Por favor informe um nome da pessoa");
		}
		return this.dao.findByName(nome);
	}


}
