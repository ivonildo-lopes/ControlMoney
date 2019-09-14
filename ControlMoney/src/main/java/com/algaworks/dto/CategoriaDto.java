package com.algaworks.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CategoriaDto implements Serializable {

    private Long id;

    @NotEmpty(message = "O nome da categoria deve ser informado")
    @Size(min = 3, max = 100, message = "O nome da Categoria  deve conter entre {min} e {max} caracteres")
    private String nome;
}
