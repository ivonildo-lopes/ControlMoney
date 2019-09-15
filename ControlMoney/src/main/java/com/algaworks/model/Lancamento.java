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

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    @NotNull(message = "Informe o valor do Lançamento")
    private BigDecimal valor;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;

    @Enumerated(value = EnumType.ORDINAL)
    private TipoLancamento tipoLancamento;
}
