package com.algaworks.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permissoes")
public @Data class Permissao implements Serializable {

    @Id
    private Long id;

    private String descricao;


}
