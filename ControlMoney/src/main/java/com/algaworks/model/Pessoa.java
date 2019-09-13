package com.algaworks.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Table(name = "pessoas")
@Entity
public @Data class Pessoa implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve conter entre {min} e {max} caracteres")
    private String nome;

    private Boolean ativo;

    @Embedded
    private Endereco endereco;
}
