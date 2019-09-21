package com.algaworks.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class PessoaDto implements Serializable {

    private Long id;

    @NotEmpty(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve conter entre {min} e {max} caracteres")
    private String nome;

    @NotNull(message = "Favor informar se a pessoa ativa")
    private Boolean ativo;

    private EnderecoDto endereco;
}
