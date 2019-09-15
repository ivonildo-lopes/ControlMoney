package com.algaworks.enums;

public enum TipoLancamento {

    RECEITA(1), DESPESA(2);

    Integer codigo;

    TipoLancamento(Integer codigo){
        this.codigo = codigo;
    }
}
