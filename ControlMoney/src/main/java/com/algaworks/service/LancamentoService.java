package com.algaworks.service;

import com.algaworks.dto.LancamentoDto;
import com.algaworks.model.Lancamento;
import com.algaworks.repository.Filter.LancamentoFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LancamentoService {
	
	 List<Lancamento> findAll();
	 List<LancamentoDto> findAllFilter(LancamentoFilter filter, Pageable pageable);

	 Lancamento findById(Long id);

	 Lancamento save(Lancamento obj);
	 LancamentoDto save(LancamentoDto obj);

	 void delete(Long id);
//
//	 List<Lancamento> findByName(String nome);
//
	 LancamentoDto update(Long id, LancamentoDto dto);
//
//	 LancamentoDto update(Long id, Boolean ativo);
}
