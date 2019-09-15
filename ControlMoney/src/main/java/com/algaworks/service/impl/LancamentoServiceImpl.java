package com.algaworks.service.impl;

import com.algaworks.Util.Converter;
import com.algaworks.dao.LancamentoDao;
import com.algaworks.dto.LancamentoDto;
import com.algaworks.error.BadValueException;
import com.algaworks.error.NegocioException;
import com.algaworks.error.NoContentException;
import com.algaworks.model.Categoria;
import com.algaworks.model.Lancamento;
import com.algaworks.model.Pessoa;
import com.algaworks.repository.Filter.LancamentoFilter;
import com.algaworks.repository.LancamentoRepository;
import com.algaworks.service.CategoriaService;
import com.algaworks.service.LancamentoService;
import com.algaworks.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
public class LancamentoServiceImpl implements LancamentoService {

	@Autowired
	private LancamentoDao dao;

	@Autowired
	private LancamentoRepository repository;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private PessoaService pessoaService;
	
	@Override
	public List<Lancamento> findAll() {

		List<Lancamento> lancamentos = this.dao.findAll();

		if(Objects.isNull(lancamentos) || lancamentos.isEmpty()) { throw new NoContentException("Nenhuma Lancamento Encontrada!"); }

		return lancamentos;
	}

	@Override
	public Page<LancamentoDto> findAllFilter(LancamentoFilter filter, Pageable pageable) {

		Page<LancamentoDto> lancamentos = this.repository.findAllFilter(filter,pageable);

		if(Objects.isNull(lancamentos)) { throw new NoContentException("Nenhuma Lancamento Encontrada!"); }


		return lancamentos;
	}

	@Override
	public Lancamento findById(Long id) {
		if(Objects.isNull(id)) {
			throw new BadValueException("Por favor informe o ID da lancamento");
		}
		return this.dao.findOne(id);
	}

	@Override
	public Lancamento save(Lancamento obj) {
		if(Objects.isNull(obj)) { throw new BadValueException("Por favor informe uma lancamento"); }

		obj.setCategoria(this.categoriaService.findById(obj.getCategoria().getId()));
		obj.setPessoa(this.pessoaService.findById(obj.getPessoa().getId()));

		return this.dao.save(obj);
	}


	@Override
	public LancamentoDto save(LancamentoDto obj) {
		if(Objects.isNull(obj)) { throw new BadValueException("Por favor informe uma lancamento"); }

		Lancamento lancamento = new Lancamento();

		lancamento = (Lancamento) Converter.converteDtoToModel(obj,lancamento,"id");
		lancamento.setCategoria(this.categoriaService.findById(obj.getCategoria().getId()));
		lancamento.setPessoa(pessoaService.findById(obj.getPessoa().getId()));

		if(lancamento.getPessoa().isInativo()) {
			throw new NegocioException("Não é possivel criar lançamento para pessoa Inativa ");
		}

		lancamento = this.dao.save(lancamento);

		return (LancamentoDto) Converter.converteModelToDto(lancamento,obj);
	}

	@Override
	public void delete(Long id) {
		if(Objects.isNull(id)) { throw new BadValueException("Por favor informe o id do lancamento"); }

		Lancamento lancamento = this.dao.findOne(id);

		if(Objects.isNull(lancamento)) {
			throw new BadValueException("Esse lançamento não existe");
		}


		if(Objects.nonNull(lancamento.getDataPagamento())){
			throw new BadValueException("O Lançamento não pode ser deletado pois ja está pago");
		}
		this.dao.delete(lancamento);
	}
//
//	@Override
//	public List<Lancamento> findByName(String nome) {
//
//		if(Objects.isNull(nome) || nome.isEmpty()) { throw new BadValueException("Por favor informe um nome da lancamento"); }
//		return this.dao.findByName(nome);
//	}
//
	@Override
	public LancamentoDto update(Long id, LancamentoDto dto) {
		Lancamento lancamento = this.findById(id);

		if(Objects.isNull(lancamento)) { throw new EmptyResultDataAccessException(1); }

		lancamento = (Lancamento) Converter.converteDtoToModel(dto,lancamento,"id");

		lancamento.setCategoria(Categoria.getPessoaDtoToPessoa(dto.getCategoria()));
//		lancamento.setCategoria((Categoria) Converter.converteDtotoModel(dto.getCategoria(),lancamento.getCategoria()));

		lancamento.setPessoa(Pessoa.getPessoaDtoToPessoa(dto.getPessoa()));
//		lancamento.setPessoa((Pessoa) Converter.converteDtotoModel(dto.getPessoa(),lancamento.getPessoa()));

		this.save(lancamento);

		return (LancamentoDto) Converter.converteModelToDto(lancamento,dto);
	}
//
//	@Override
//	public LancamentoDto update(Long id, Boolean ativo) {
//		EnderecoDto enderecoDto = new EnderecoDto();
//		LancamentoDto dto = new LancamentoDto();
//
//		Lancamento lancamento = this.findById(id);
//
//		if(Objects.isNull(lancamento)) { throw new EmptyResultDataAccessException(1); }
//
//		lancamento.setAtivo(ativo);
//
//		this.save(lancamento);
//
//		enderecoDto = (EnderecoDto) Converter.converteModelToDto(lancamento.getEndereco(), enderecoDto);
//
//		dto = (LancamentoDto) Converter.converteModelToDto(lancamento,dto);
//		dto.setEndereco(enderecoDto);
//
//		return dto;
//	}
}
