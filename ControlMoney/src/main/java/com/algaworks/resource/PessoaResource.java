package com.algaworks.resource;

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
import java.util.Objects;

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

		if(Objects.isNull(pessoas) || pessoas.isEmpty()) {
			response.setStatus(HttpStatus.NO_CONTENT.value());
			return ResponseDto.response(pessoas,HttpStatus.NO_CONTENT,"Nenhuma pessoa encontrada!");
		}
		return ResponseDto.response(pessoas,HttpStatus.OK,"Lista de Todas as pessoas");
	}

	@GetMapping(value = "/{id}")
	public ResponseDto findById(@PathVariable Long id, HttpServletResponse response) {

		Pessoa pessoa = this.service.findById(id);

		if(Objects.isNull(pessoa)) {
			return ResponseDto.response(pessoa,HttpStatus.NO_CONTENT,"Nenhuma Pessoa encontrada!");
		}

		return ResponseDto.response(pessoa,HttpStatus.OK,"Pessoa encontrada: " + pessoa.getNome());
	}

	@PostMapping(value = "/")
	public ResponseDto save(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

		if(Objects.isNull(pessoa)) {
			return ResponseDto.response(pessoa,HttpStatus.NO_CONTENT,"Informe uma pessoa");
		}

		Pessoa pes = this.service.save(pessoa);

		if(Objects.isNull(pes)) {
			return ResponseDto.response(pessoa,HttpStatus.NO_CONTENT,"Essa pessoa já existe");
		}

		this.publisher.publishEvent(new ResourceCriadoEvent(this,response,pes.getId()));
		return ResponseDto.response(pes,HttpStatus.CREATED,"Pessoa criada com sucesso: " + pes.getNome());
	}

	@PostMapping(value = "/all")
	public ResponseDto saveAll(@RequestBody List<Pessoa> pessoas) {

		if(Objects.isNull(pessoas)) {
			return ResponseDto.response(null,HttpStatus.NO_CONTENT,"Informe as pessoas");
		}

		try {
			this.service.saveAll(pessoas);
			return ResponseDto.response(null,HttpStatus.CREATED,"Pessoas criadas com sucesso: ");
		}catch (Exception e) {
			return ResponseDto.response(null,HttpStatus.NO_CONTENT,"Erro ao tentar salvar pessoas");
		}

	}

	@DeleteMapping(value = "/{id}")
	public ResponseDto delete(@PathVariable Long id, HttpServletResponse response) {
		this.service.delete(id);
		return ResponseDto.response(null,HttpStatus.NO_CONTENT,"Pessoa deletada");
	}

}
