package com.algaworks.model;

import com.algaworks.Util.Converter;
import com.algaworks.dto.EnderecoDto;
import com.algaworks.dto.PessoaDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "Favor informar se a pessoa ativa")
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public static Pessoa getPessoaDtoToPessoa(PessoaDto dto){
        Pessoa pessoa = new Pessoa();
        return (Pessoa) Converter.converteDtotoModel(dto,pessoa);
    }

    @JsonIgnore
    @Transient
    public Boolean isInativo(){
        return !ativo;
    }

}
