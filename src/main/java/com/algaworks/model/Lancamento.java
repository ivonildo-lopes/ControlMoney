package com.algaworks.model;

import com.algaworks.Util.Converter;
import com.algaworks.dto.CategoriaDto;
import com.algaworks.dto.LancamentoDto;
import com.algaworks.enums.TipoLancamento;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern =  "dd/MM/yyyy")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    @JsonFormat(pattern =  "dd/MM/yyyy")
    private LocalDate dataPagamento;

    @NotNull(message = "Informe o valor do Lançamento")
    private BigDecimal valor;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    @NotNull(message = "Favor informar a categoria do lançamento")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    @NotNull(message = "Favor informar a pessoa")
    private Pessoa pessoa;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Favor informar o tipo do Lançamento")
    @Column(name = "tipo")
    private TipoLancamento tipoLancamento;

}
