package com.algaworks.enums;

public enum TipoLancamento {

    RECEITA("Receita"), DESPESA("Despesa");

    String descricao;

    TipoLancamento(String descricao){
        this.descricao = descricao;
    }
}
