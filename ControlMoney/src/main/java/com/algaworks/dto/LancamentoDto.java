package com.algaworks.dto;

import com.algaworks.enums.TipoLancamento;
import com.algaworks.model.Pessoa;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public @Data class LancamentoDto implements Serializable {

    private Long id;

    @NotEmpty(message = "Favor informar uma descrição para o Lançamento")
    private String descricao;

    @NotNull(message = "Favor informe a data de vencimento")
    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    @NotNull(message = "Informe o valor do Lançamento")
    private BigDecimal valor;

    private String observacao;

    private CategoriaDto categoria;

    @NotNull(message = "Favor informar o tipo do Lançamento")
    private TipoLancamento tipoLancamento;

    private PessoaDto pessoa;
}
