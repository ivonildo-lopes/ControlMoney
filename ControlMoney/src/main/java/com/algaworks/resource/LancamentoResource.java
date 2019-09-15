package com.algaworks.resource;

import com.algaworks.dto.LancamentoDto;
import com.algaworks.dto.ResponseDto;
import com.algaworks.event.ResourceCriadoEvent;
import com.algaworks.model.Lancamento;
import com.algaworks.repository.Filter.LancamentoFilter;
import com.algaworks.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/lancamentos")
public class LancamentoResource implements Serializable {


	@Autowired
	private LancamentoService service;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping(value = "/")
	public ResponseDto findAll() {
		List<Lancamento> lancamentos = this.service.findAll();
		return ResponseDto.response(lancamentos,HttpStatus.OK,"Lista de Todas os lancamentos");
	}

	@GetMapping(value = "/params")
	public ResponseDto findAllFilter(LancamentoFilter filter, Pageable pageable) {
		List<LancamentoDto> lancamentos = this.service.findAllFilter(filter, pageable);
		return ResponseDto.response(lancamentos,HttpStatus.OK,"Lista de Todas os lancamentos");
	}

	@GetMapping(value = "/{id}")
	public ResponseDto findById(@PathVariable Long id, HttpServletResponse response) {

		Lancamento lancamento = this.service.findById(id);
		return ResponseDto.response(lancamento,HttpStatus.OK,"Lancamento encontrada: " + lancamento.getDescricao());
	}

	@PostMapping(value = "/")
	public ResponseDto save(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {

		Lancamento lan = this.service.save(lancamento);
		this.publisher.publishEvent(new ResourceCriadoEvent(this,response,lan.getId()));
		return ResponseDto.response(lan,HttpStatus.CREATED,"Lancamento criada com sucesso: " + lan.getDescricao());
	}

	@PostMapping(value = "/2")
	public ResponseDto save2(@Valid @RequestBody LancamentoDto lancamento, HttpServletResponse response) {

		LancamentoDto lan = this.service.save(lancamento);
		this.publisher.publishEvent(new ResourceCriadoEvent(this,response,lan.getId()));
		return ResponseDto.response(lan,HttpStatus.CREATED,"Lancamento criada com sucesso: " + lan.getDescricao());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseDto delete(@PathVariable Long id, HttpServletResponse response) {
		this.service.delete(id);
		return ResponseDto.response(null,HttpStatus.NO_CONTENT,"Lancamento deletado");
	}

	@PutMapping(value = "/{id}")
	public ResponseDto update(@Valid @PathVariable Long id, @RequestBody LancamentoDto lancamento, HttpServletResponse response) {
		LancamentoDto lancamentoDto = this.service.update(id, lancamento);
		return ResponseDto.response(lancamentoDto,HttpStatus.OK,"Lancamento atualizada");
	}

}
