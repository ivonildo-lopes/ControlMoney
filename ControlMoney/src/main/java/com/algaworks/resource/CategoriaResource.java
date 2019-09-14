package com.algaworks.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.algaworks.dto.ResponseDto;
import com.algaworks.event.ResourceCriadoEvent;
import com.algaworks.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.algaworks.service.CategoriaService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource implements Serializable {

	private static final long serialVersionUID = -3832793981135304634L;
	
	@Autowired
	private CategoriaService service;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping(value = "/")
	public ResponseDto findAll(HttpServletResponse response) {

		List<Categoria> categorias = this.service.findAll();

		if(Objects.isNull(categorias) || categorias.isEmpty()) {
			response.setStatus(HttpStatus.NO_CONTENT.value());
			return ResponseDto.response(categorias,HttpStatus.NO_CONTENT,"Nenhuma Categoria encontrada!");
		}
		return ResponseDto.response(categorias,HttpStatus.OK,"Lista de Todas as categorias");
	}

	@GetMapping(value = "/{id}")
	public ResponseDto findById(@PathVariable Long id, HttpServletResponse response) {

		Categoria categoria = this.service.findById(id);

		if(Objects.isNull(categoria)) {
			return ResponseDto.response(categoria,HttpStatus.NO_CONTENT,"Nenhuma Categoria encontrada!");
		}

		return ResponseDto.response(categoria,HttpStatus.OK,"Categoria encontrada: " + categoria.getNome());
	}

	@PostMapping(value = "/")
	public ResponseDto save(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {

		if(Objects.isNull(categoria)) {
			return ResponseDto.response(categoria,HttpStatus.NO_CONTENT,"Informe uma categoria");
		}

		Categoria cat = this.service.save(categoria);

		if(Objects.isNull(cat)) {
			return ResponseDto.response(categoria,HttpStatus.NO_CONTENT,"Essa categoria já existe");
		}

//		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cat.getId()).toUri();
//		response.setHeader("Location", uri.toString());
//		response.setStatus(HttpStatus.CREATED.value());
		this.publisher.publishEvent(new ResourceCriadoEvent(this,response,cat.getId()));

		return ResponseDto.response(cat,HttpStatus.CREATED,"Categoria criada com sucesso: " + categoria.getNome());
	}

	@PostMapping(value = "/all")
	public ResponseDto saveAll(@RequestBody List<Categoria> categorias) {

		if(Objects.isNull(categorias)) {
			return ResponseDto.response(null,HttpStatus.NO_CONTENT,"Informe as categorias");
		}

		try {
			this.service.saveAll(categorias);
			return ResponseDto.response(null,HttpStatus.CREATED,"Categorias criadas com sucesso: ");
		}catch (Exception e) {
			return ResponseDto.response(null,HttpStatus.NO_CONTENT,"Erro ao tentar salvar categorias");
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseDto delete(@PathVariable Long id, HttpServletResponse response) {
		try {
			this.service.delete(id);
			return ResponseDto.response(null,HttpStatus.OK,"Categoria deletada: ");
		}catch (Exception e) {
			return ResponseDto.response(null,HttpStatus.NO_CONTENT,"Erro ao tentar excluir categoria!");
		}
	}

	@DeleteMapping(value = "/")
	public ResponseDto deleteAll(@RequestBody List<Long> ids, HttpServletResponse response) {
		try {
			this.service.deleteAll(ids);
			return ResponseDto.response(null,HttpStatus.OK,"Categorias deletadas: ");
		}catch (Exception e) {
			return ResponseDto.response(null,HttpStatus.NO_CONTENT,"Erro ao tentar excluir categoria!");
		}
	}


}
