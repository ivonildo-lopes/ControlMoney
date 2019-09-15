package com.algaworks.model;

import com.algaworks.enums.TipoLancamento;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamentos")
public @Data class Lancamento implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Favor informar uma descrição para o Lançamento")
    private String descricao;

    @Column(name = "data_vencimento")
    @NotNull(message = "Favor informe a data de vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @NotNull(message = "Informe o valor do Lançamento")
    private BigDecimal valor;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private Pessoa pessoa;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Favor informar o tipo do Lançamento")
    @Column(name = "tipo")
    private TipoLancamento tipoLancamento;
}
