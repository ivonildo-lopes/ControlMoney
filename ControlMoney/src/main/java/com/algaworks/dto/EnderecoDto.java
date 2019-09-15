package com.algaworks.dto;

import lombok.Data;
import java.io.Serializable;

public @Data class EnderecoDto implements Serializable {

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
}
