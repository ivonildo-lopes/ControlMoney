package com.algaworks.model;

import com.algaworks.Util.Converter;
import com.algaworks.dto.CategoriaDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categorias")
public @Data class Categoria implements Serializable {

	private static final long serialVersionUID = -4904844702954092693L;

	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty(message = "O nome da categoria deve ser informado")
	@Size(min = 3, max = 100, message = "O nome da Categoria  deve conter entre {min} e {max} caracteres")
	private String nome;

	public static Categoria getPessoaDtoToPessoa(CategoriaDto dto){
		Categoria categoria = new Categoria();
		return (Categoria) Converter.converteDtotoModel(dto,categoria);
	}

}
