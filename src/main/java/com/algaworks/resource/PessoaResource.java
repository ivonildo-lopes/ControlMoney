package com.algaworks.resource;

import com.algaworks.dto.PessoaDto;
import com.algaworks.dto.ResponseDto;
import com.algaworks.event.ResourceCriadoEvent;
import com.algaworks.model.Pessoa;
import com.algaworks.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource implements Serializable {


	@Autowired
	private PessoaService service;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping(value = "/")
	public ResponseDto findAll(HttpServletResponse response) {
		List<Pessoa> pessoas = this.service.findAll();
		return ResponseDto.response(pessoas,HttpStatus.OK,"Lista de Todas as pessoas");
	}

	@GetMapping(value = "/{id}")
	public ResponseDto findById(@PathVariable Long id, HttpServletResponse response) {

		Pessoa pessoa = this.service.findById(id);
		return ResponseDto.response(pessoa,HttpStatus.OK,"Pessoa encontrada: " + pessoa.getNome());
	}

	@PostMapping(value = "/")
	public ResponseDto save(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

		Pessoa pes = this.service.save(pessoa);
		this.publisher.publishEvent(new ResourceCriadoEvent(this,response,pes.getId()));
		return ResponseDto.response(pes,HttpStatus.CREATED,"Pessoa criada com sucesso: " + pes.getNome());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseDto delete(@PathVariable Long id, HttpServletResponse response) {
		this.service.delete(id);
		return ResponseDto.response(null,HttpStatus.NO_CONTENT,"Pessoa deletada");
	}

	@PutMapping(value = "/{id}")
	public ResponseDto update(@Valid @PathVariable Long id, @RequestBody PessoaDto pessoa, HttpServletResponse response) {
		PessoaDto pessoaDto = this.service.update(id, pessoa);
		return ResponseDto.response(pessoaDto,HttpStatus.OK,"Pessoa atualizada");
	}

	@PutMapping(value = "/{id}/ativo")
	public ResponseDto updateParcial(@Valid @PathVariable Long id, @RequestBody Boolean ativo, HttpServletResponse response) {
		PessoaDto pessoaDto = this.service.update(id, ativo);
		return ResponseDto.response(pessoaDto,HttpStatus.OK,"Pessoa atualizada");
	}

}
