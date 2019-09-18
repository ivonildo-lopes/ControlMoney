package com.algaworks.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "usuarios")
public @Data class Usuario implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Informe o nome do Usuario")
    private String nome;

    @NotEmpty(message = "Informe o email do Usuario")
    private String email;

    @NotEmpty(message = "Informe a senha do Usuario")
    private String senha;

    private Boolean ativo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permissao",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_permissao"))
    private List<Permissao> permissoes;
}
